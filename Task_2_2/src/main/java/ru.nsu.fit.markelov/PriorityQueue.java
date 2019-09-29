package ru.nsu.fit.markelov;

import java.util.Comparator;

public class PriorityQueue<T extends Comparable> extends Stack<T> {

    private Comparator<T> mComparator;

    /**
     * Creates new PriorityQueue based on a Stack. The elements of
     * the priority queue are ordered with natural ordering.
     * It does not permit insertion of non-comparable objects.
     * <p>
     * The head of this queue is the least element with respect to
     * the natural ordering. If multiple elements are tied for least
     * value, the head is one of those elements -- ties are broken
     * arbitrarily.
     * <p>
     * A PriorityQueue implements Iterable interface.
     */
    @SuppressWarnings("unchecked") // for Comparator.naturalOrder()
    public PriorityQueue() {
        this(Comparator.naturalOrder());
    }

    /**
     * Creates new PriorityQueue based on a Stack. The elements of
     * the priority queue are ordered by a specified Comparator.
     * It does not permit insertion of non-comparable objects.
     * <p>
     * The head of this queue is the least element with respect to
     * the natural ordering. If multiple elements are tied for least
     * value, the head is one of those elements -- ties are broken
     * arbitrarily.
     * <p>
     * A PriorityQueue implements Iterable interface.
     *
     * @param comparator the comparator to determine the order
     * @see   Comparator
     */
    public PriorityQueue(Comparator<T> comparator) {
        mComparator = comparator;
    }

    /**
     * Inserts the specified element into this priority queue.
     * <p>
     * Takes O (log(n)) time for n sized PriorityQueue.
     *
     * @param  elem                 the element to insert
     * @throws NullPointerException if the specified element is null
     */
    public void insert(T elem) {
        if (elem == null) {
            throw new NullPointerException("null cannot be passed as a parameter");
        }

        push(elem);
        siftUp(count() - 1);
    }

    /**
     * Retrieves and removes the head of this queue, or returns null
     * if this queue is empty.
     * <p>
     * Takes O (log(n)) time for n sized PriorityQueue.
     *
     * @return the head of this queue, or null if this queue is empty
     */
    @SuppressWarnings("unchecked")
    public T extract() {
        if (count() == 0) {
            return null;
        }

        T elem = (T) objects[0];

        objects[0] = pop();
        siftDown(0);

        return elem;
    }

    @SuppressWarnings("unchecked")
    private void siftDown(int node) {
        int child = 2 * node + 1; // left child

        if (child >= count()) { // no children
            return;
        }

        if (child + 1 < count() && compare((T) objects[child + 1], (T) objects[child])) {
            child++; // right child
        }

        if (compare((T) objects[child], (T) objects[node])) {
            swap(child, node);
            siftDown(child);
        }
    }

    @SuppressWarnings("unchecked")
    private void siftUp(int node) {
        if (node == 0) { // no parent
            return;
        }

        int parent = (node - 1) / 2;

        if (compare((T) objects[node], (T) objects[parent])) {
            swap(node, parent);
            siftUp(parent);
        }
    }

    private void swap(int a, int b) {
        Object tmp = objects[a];
        objects[a] = objects[b];
        objects[b] = tmp;
    }

    private boolean compare(T a, T b) {
        return mComparator.compare(a, b) < 0;
    }
}
