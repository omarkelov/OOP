package ru.nsu.fit.markelov;

import java.util.Comparator;

public class HeapSort<T extends Comparable> {

    private Comparator<T> mComparator;

    /**
     * Sorts the specified array of objects using elements' natural
     * ordering. All elements in the array must be mutually comparable
     * by the specified comparator.
     * <p>
     * Heapsort takes O(n log n) comparisons to sort n items in the
     * worst case. It is an in-place algorithm, but not stable.
     *
     * @param arr the array to be sorted
     * @return    the initial array after sorting or null if initial
     *            array was null
     */
    public T[] sort(T[] arr) {
        return sort(arr, null);
    }

    /**
     * Sorts the specified array of objects according to the order
     * induced by the specified comparator. All elements in the
     * array must be mutually comparable by the specified comparator.
     * <p>
     * Heapsort takes O(n log n) comparisons to sort n items in the
     * worst case. It is an in-place algorithm, but not stable.
     *
     * @param arr        the array to be sorted
     * @param comparator the comparator to determine the order of the array.
     *                   A null value indicates that the elements' natural
     *                   ordering should be used.
     * @return           the initial array after sorting or null if initial
     *                   array was null
     * @see              Comparator
     */
    @SuppressWarnings("unchecked") // for Comparator.naturalOrder()
    public T[] sort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            return null;
        }

        mComparator = (comparator != null) ? comparator : Comparator.naturalOrder();

        for (int i = arr.length / 2 - 1; i >= 0; i--) { // heap building
            siftDown(arr, arr.length, i);
        }

        for (int i = arr.length - 1; i > 0; i--) { // swap every element (going backwards) with the root and sift it down
            swap(arr, 0, i);
            siftDown(arr, i, 0);
        }

        return arr;
    }

    private void siftDown(T[] arr, int len, int parent) {
        int child = 2 * parent + 1; // left child

        if (child >= len) { // no children
            return;
        }

        if (child + 1 < len && compare(arr[child + 1], arr[child])) {
            child++; // right child
        }

        if (compare(arr[child], arr[parent])) {
            swap(arr, child, parent);
            siftDown(arr, len, child);
        }
    }

    private void swap(T[] arr, int a, int b) {
        T tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }

    private boolean compare(T a, T b) {
        return mComparator.compare(a, b) > 0;
    }
}
