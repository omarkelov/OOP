package ru.nsu.fit.markelov;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class PriorityQueue<T extends Comparable> implements Iterable<T> {

    private static double RESIZE_FACTOR = 8;

    private Object[] objects;
    private int count;
    private int capacity;

    private Comparator<T> comparator;

    /**
     * Creates new PriorityQueue. The elements of the priority queue
     * are ordered by a specified Comparator. It does not permit
     * insertion of non-comparable objects.
     * <p>
     * The head of this queue is the least element with respect to
     * the natural ordering. If multiple elements are tied for least
     * value, the head is one of those elements -- ties are broken
     * arbitrarily.
     * <p>
     * When a queue is first created, it contains no items and no
     * memory is allocated. However, the size of a queue can grow
     * or shrink as needed to accommodate adding and removing items
     * after the queue has been created.
     * <p>
     * A PriorityQueue implements Iterable interface.
     */
    @SuppressWarnings("unchecked") // for Comparator.naturalOrder()
    public PriorityQueue() {
        this(Comparator.naturalOrder());
    }

    /**
     * Creates new PriorityQueue. The elements of the priority queue
     * are ordered by a specified Comparator. It does not permit
     * insertion of non-comparable objects.
     * <p>
     * The head of this queue is the least or largest element with
     * respect to the ordering determined by specified comparator.
     * If multiple elements are tied for least value, the head is
     * one of those elements -- ties are broken arbitrarily.
     * <p>
     * When a queue is first created, it contains no items and no
     * memory is allocated. However, the size of a queue can grow
     * or shrink as needed to accommodate adding and removing items
     * after the queue has been created.
     * <p>
     * A PriorityQueue implements Iterable interface.
     *
     * @param comparator the comparator to determine the order
     * @see   Comparator
     */
    public PriorityQueue(Comparator<T> comparator) {
        count = 0;
        capacity = 0;
        this.comparator = comparator;
    }

    /**
     * Inserts the specified element into this queue.
     * <p>
     * Takes O (log(n)) time for n sized PriorityQueue.
     *
     * @param  elem                 the element to insert.
     * @throws NullPointerException if the specified element is null.
     */
    public void insert(T elem) {
        if (elem == null) {
            throw new NullPointerException("null cannot be inserted in PriorityQueue");
        }

        append(elem);
        siftUp(count - 1);
    }

    /**
     * Retrieves and removes the head of this queue.
     * <p>
     * Takes O (log(n)) time for n sized PriorityQueue.
     *
     * @return                     the head of this queue.
     * @throws EmptyQueueException if the queue is empty.
     */
    @SuppressWarnings("unchecked")
    public T extract() {
        if (count == 0) {
            throw new EmptyQueueException();
        }

        T elem = (T) objects[0];

        swap(0, count - 1);
        remove();
        siftDown(0);

        return elem;
    }

    /**
     * Returns the amount of objects in this queue.
     *
     * @return the amount of objects in this queue.
     */
    public int getCount() {
        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < count;
            }

            @Override
            public T next() {
                return (T) objects[i++];
            }
        };
    }

    private void append(T elem) {
        if (objects == null) {
            capacity = 1;
            objects = new Object[1];
        } else if (capacity == count) {
            capacity = (int) Math.ceil(capacity * RESIZE_FACTOR);
            objects = Arrays.copyOf(objects, capacity);
        }

        objects[count++] = elem;
    }

    private void remove() {
        count--;
        if (count <= (int) Math.ceil(capacity / (RESIZE_FACTOR * RESIZE_FACTOR))) {
            capacity = (int) Math.ceil(capacity / RESIZE_FACTOR);
            objects = Arrays.copyOf(objects, capacity);
        }
    }

    @SuppressWarnings("unchecked")
    private void siftDown(int node) {
        int child = 2 * node + 1; // left child

        if (child >= count) { // no children
            return;
        }

        if (child + 1 < count && compare((T) objects[child + 1], (T) objects[child])) {
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
        return comparator.compare(a, b) < 0;
    }
}
