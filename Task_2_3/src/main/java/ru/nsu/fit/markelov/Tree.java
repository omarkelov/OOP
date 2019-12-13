package ru.nsu.fit.markelov;

import java.util.*;

/**
 * The <code>Tree</code> class simulates a hierarchical tree structure, with a root value and
 * subtrees of children with a parent node, represented as a set of linked nodes. A tree data
 * structure is defined recursively as a collection of nodes (starting at a root node). Each node
 * is a data structure consisting of a value, together with a list of references to direct nodes --
 * the "children". Among the children all the nodes have different values.
 * <p>
 * The <code>Tree</code> class implements <code>Iterable</code> interface and can be traversed with
 * help of breadth or depth first search. A breadth first search is used by default.
 * <p>
 * The <code>Tree</code> class implements <code>Comparable</code> interface, that's why objects of
 * only comparable type can be used as a value of each node. Also a value cannot be <tt>null</tt>.
 *
 * @author Oleg Markelov
 * @see    Iterable
 * @see    Comparable
 */
public class Tree<T extends Comparable<? super T>> implements Iterable<Tree<T>>, Comparable<Tree<T>> {

    public static final String NULL_PARAMETER_EXCEPTION_MESSAGE =
            "Null input parameter is not allowed.";
    public static final String ROOT_REMOVING_EXCEPTION_MESSAGE = "Unable to remove the root.";
    public static final String NO_MORE_ELEMENTS_EXCEPTION_MESSAGE = "All the elements have" +
            " already been iterated.";
    public static final String MODIFICATION_EXCEPTION_MESSAGE = "Iterator is no more valid, as" +
            "the tree was modified.";

    /**
     * Iterator types that can be used for iterating the tree.
     * <p>
     * BFS is for breadth first search. DFS is for depth first search.
     */
    public enum IteratorType { BFS, DFS }

    private IteratorType iteratorType;
    private int modificationCount;

    private T value;
    private Tree<T> parent;
    private List<Tree<T>> children;
    private Set<T> childrenValueSet;

    /**
     * Creates the root node with specified value. The value cannot be <tt>null</tt>.
     *
     * @param value the value to be attached to the node.
     */
    public Tree(T value) {
        this(value, null);
    }

    private Tree(T value, Tree<T> parent) {
        checkForNull(value);

        iteratorType = IteratorType.BFS;
        modificationCount = 0;

        this.value = value;
        this.parent = parent;
        children = new ArrayList<>();
        childrenValueSet = new TreeSet<>();

        if (parent != null) {
            parent.add(this);
        }
    }

    /**
     * Creates a new node with specified value and adds it as a child to current node. The value
     * cannot be <tt>null</tt>.
     *
     * @param  value the value to be attached to the node.
     * @return       the created tree.
     */
    public Tree<T> add(T value) {
        checkForNull(value);

        if (childrenValueSet.contains(value)) {
            return null;
        }

        Tree<T> tree = new Tree<>(value, this);
        incModificationCount();

        return tree;
    }

    /**
     * Adds the node as a child to current node. The node cannot be <tt>null</tt>.
     *
     * @param  tree the node to be added.
     * @return      added tree itself.
     */
    public Tree<T> add(Tree<T> tree) {
        checkForNull(tree);

        if (childrenValueSet.contains(tree.value)) {
            return null;
        }

        if (tree.parent != null) {
            tree.remove();
        }

        tree.parent = this;
        children.add(tree);
        childrenValueSet.add(tree.value);
        incModificationCount();

        return tree;
    }

    /**
     * Removes the direct child node with specified value in case it exists. The value cannot be
     * <tt>null</tt>.
     *
     * @param  value the value of the child.
     * @return       whether the node was removed. Returns <tt>false</tt>
     *               if the node with specified value was not found.
     */
    public boolean remove(T value) {
        checkForNull(value);

        if (!childrenValueSet.contains(value)) {
            return false;
        }

        childrenValueSet.remove(value);

        Iterator<Tree<T>> iterator = children.iterator();
        while (iterator.hasNext()) {
            if (value.equals(iterator.next().getValue())) {
                iterator.remove();
                incModificationCount();

                return true;
            }
        }

        return false;
    }

    /**
     * Removes the node itself from its root children list.
     *
     * @return                  whether the node was removed.
     * @throws RuntimeException if the current node is a root.
     */
    public boolean remove() {
        if (parent == null) {
            throw new RuntimeException(ROOT_REMOVING_EXCEPTION_MESSAGE);
        }

        return parent.remove(value); // increments modificationCount!
    }

    /**
     * Returns the value of the current node.
     *
     * @return the value of the current node.
     */
    public T getValue() {
        return value;
    }

    /**
     * Sets the type of iterating the tree.
     *
     * @param iteratorType the type of iterating the tree.
     */
    public void setIteratorType(IteratorType iteratorType) {
        this.iteratorType = iteratorType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<Tree<T>> iterator() {

        LinkedList<Tree<T>> traverseList = new LinkedList<>();
        traverseList.addLast(this);

        return new Iterator<Tree<T>>() {
            private IteratorType localIteratorType = iteratorType;
            private int expectedModificationCount = modificationCount;

            @Override
            public boolean hasNext() {
                return !traverseList.isEmpty();
            }

            @Override
            public Tree<T> next() {
                checkForPresence();
                checkForComodification();

                switch (localIteratorType) {
                    case DFS:
                        traverseList.addAll(1, traverseList.peekFirst().children);
                        break;
                    case BFS:
                        traverseList.addAll(traverseList.peekFirst().children);
                        break;
                }

                return traverseList.removeFirst();
            }

            private void checkForPresence() {
                if (!hasNext()) {
                    throw new NoSuchElementException(NO_MORE_ELEMENTS_EXCEPTION_MESSAGE);
                }
            }

            private void checkForComodification() {
                if (modificationCount != expectedModificationCount) {
                    throw new ConcurrentModificationException(MODIFICATION_EXCEPTION_MESSAGE);
                }
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Tree<T> anotherTree) {
        return value.compareTo(anotherTree.getValue());
    }

    private void incModificationCount() {
        for (Tree<T> tree = this; tree != null; tree = tree.parent) {
            tree.modificationCount++;
        }
    }

    private void checkForNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException(NULL_PARAMETER_EXCEPTION_MESSAGE);
        }
    }
}
