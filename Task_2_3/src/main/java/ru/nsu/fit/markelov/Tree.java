package ru.nsu.fit.markelov;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class Tree<T> implements Iterable<Tree<T>> {

    private enum ITERATOR_TYPE {BFS, DFS}
    private ITERATOR_TYPE iteratorType;

    private int modCount;

    private T value;
    private Tree<T> parent;
    private ArrayList<Tree<T>> children;

    public Tree(T value) {
        this(value, null);
    }

    public Tree(T value, Tree<T> parent) {
        iteratorType = ITERATOR_TYPE.BFS;
        modCount = 0;

        this.value = value;
        this.parent = parent;
        children = new ArrayList<>();
    }

    public Tree<T> add(T value) {
        Tree<T> tree = new Tree<>(value, this);
        children.add(tree);
        incModCount();

        return tree;
    }

    public Tree<T> add(Tree<T> tree) {
        tree.parent = this;
        children.add(tree);
        incModCount();

        return tree;
    }

    public boolean remove(T value) {
        for (int i = 0; i < children.size(); i++) {
            if (value.equals(children.get(i).getValue())) {
                children.remove(i);
                incModCount();

                return true;
            }
        }

        return false;
    }

    public boolean remove() {
        return parent.remove(value); // increments modCount!
    }

    public void useDfsIterator() {
        iteratorType = ITERATOR_TYPE.DFS;
    }

    public void useBfsIterator() {
        iteratorType = ITERATOR_TYPE.BFS;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public ArrayList<Tree<T>> getChildren() {
        return children;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterator<Tree<T>> iterator() {
        PathSearch<T> pathSearch;
        switch (iteratorType) {
            case DFS:
                pathSearch = new DepthFirstSearch(this);
                break;
            case BFS:
            default:
                pathSearch = new BreadthFirstSearch(this);
                break;
        }

        return new Iterator<Tree<T>>() {
            private int i = 0;
            int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                return i < pathSearch.getPath().size();
            }

            @Override
            public Tree<T> next() {
                checkForComodification();
                return pathSearch.getPath().get(i++);
            }

            private void checkForComodification() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
            }
        };
    }

    private void incModCount() {
        incModCount(this);
    }

    private void incModCount(Tree<T> tree) {
        tree.modCount++;

        if (!tree.hasParent()) {
            return;
        }

        incModCount(tree.parent);
    }

    private boolean hasParent() {
        return parent != null;
    }

    // ----- debug -----

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("direct children: ");
        for (Tree<T> child : children) {
            stringBuilder.append(child.getValue()).append(", ");
        }

        return stringBuilder.toString();
    }
}
