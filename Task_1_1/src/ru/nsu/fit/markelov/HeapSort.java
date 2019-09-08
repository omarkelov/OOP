package ru.nsu.fit.markelov;

public abstract class HeapSort {
    public abstract boolean compare(int a, int b); // comparator

    public void sort(int[] arr) {
        for (int i = arr.length / 2 - 1; i >= 0; i--) { // heap building
            siftDown(arr, arr.length, i);
        }

        for (int i = arr.length - 1; i > 0; i--) { // swap every element (going backwards) with the root and sift it down
            swap(arr, 0, i);
            siftDown(arr, i, 0);
        }
    }

    private void siftDown(int[] arr, int len, int parent) {
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

    private void swap(int[] arr, int a, int b) {
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }
}
