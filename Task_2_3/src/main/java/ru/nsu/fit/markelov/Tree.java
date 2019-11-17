package ru.nsu.fit.markelov;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * The <code>Tree</code> class simulates a hierarchical tree structure,
 * with a root value and subtrees of children with a parent node,
 * represented as a set of linked nodes. A tree data structure is
 * defined recursively as a collection of nodes (starting at a root node).
 * Each node is a data structure consisting of a value, together with a
 * list of references to nodes (the "children")
 * <p>
 * The <code>Tree</code> class implements <code>Iterable</code> interface
 * and can be traversed with help of breadth or depth first search.
 * A breadth first search is used by default.
 *
 * @auther Oleg Markelov
 * @see    Iterable
 */
public class Tree<T> implements Iterable<Tree<T>> {

    /**
     * Iterator types that can be used for iterating the tree.
     * <p>
     * BFS is for breadth first search. DFS is for depth first search.
     */
    public enum ITERATOR_TYPE {BFS, DFS}
    private ITERATOR_TYPE iteratorType;

    private int modificationCount;

    private T value;
    private Tree<T> parent;
    private ArrayList<Tree<T>> children;

    /**
     * Creates the root node with specified value.
     *
     * @param value the value to be attached to the node.
     */
    public Tree(T value) {
        this(value, null);
    }

    /**
     * Creates a node with specified value and adds it to parent
     * node as a child in case the parent node is not <tt>null</tt>.
     *
     * @param value  the value to be attached to the node.
     * @param parent the parent node.
     */
    public Tree(T value, Tree<T> parent) {
        iteratorType = ITERATOR_TYPE.BFS;
        modificationCount = 0;

        this.value = value;
        this.parent = parent;
        children = new ArrayList<>();

        if (!isRoot()) {
            parent.add(this);
        }
    }

    /**
     * Creates a new node with specified value and adds it
     * as a child to current node.
     *
     * @param value the value to be attached to the node.
     * @return      the created tree.
     */
    public Tree<T> add(T value) {
        Tree<T> tree = new Tree<>(value, this);
        incModificationCount();

        return tree;
    }

    /**
     * Adds the node as a child to current node.
     *
     * @param tree the node to be added.
     * @return     added tree itself.
     */
    public Tree<T> add(Tree<T> tree) {
        tree.parent = this;
        children.add(tree);
        incModificationCount();

        return tree;
    }

    /**
     * Removes the direct child node with specified value in case it exists.
     *
     * @param value the value of the child.
     * @return      whether the node was removed. Returns <tt>false</tt>
     *              if the node with specified value was not found.
     */
    public boolean remove(T value) {
        Iterator<Tree<T>> i = children.iterator();
        while (i.hasNext()) {
            if (value.equals(i.next().getValue())) {
                i.remove();
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
    public boolean remove() throws RuntimeException {
        if (isRoot()) {
            throw new RuntimeException("Unable to remove the root");
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
    public void setIteratorType(ITERATOR_TYPE iteratorType) {
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
            int expectedModificationCount = modificationCount;

            @Override
            public boolean hasNext() {
                return !traverseList.isEmpty();
            }

            @Override
            public Tree<T> next() {
                checkForComodification();

                switch (iteratorType) {
                    case DFS:
                        traverseList.addAll(1, traverseList.peekFirst().children);
                        break;
                    case BFS:
                        traverseList.addAll(traverseList.peekFirst().children);
                        break;
                }

                return traverseList.removeFirst();
            }

            private void checkForComodification() {
                if (modificationCount != expectedModificationCount) {
                    throw new ConcurrentModificationException();
                }
            }
        };
    }

    private void incModificationCount() {
        incModificationCount(this);
    }

    private void incModificationCount(Tree<T> tree) {
        tree.modificationCount++;

        if (tree.isRoot()) {
            return;
        }

        incModificationCount(tree.parent);
    }

    private boolean isRoot() {
        return parent == null;
    }
}
