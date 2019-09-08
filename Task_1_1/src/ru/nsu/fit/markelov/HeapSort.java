package ru.nsu.fit.markelov;

public abstract class HeapSort<T> {
    public abstract boolean compare(T a, T b); // comparator

    public T[] sort(T[] arr) {
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
}
