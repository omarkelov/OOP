package ru.nsu.fit.markelov;

import java.util.Comparator;

public class HeapSort {
    private Comparator mComparator;

    public HeapSort(Comparator comparator) {
        mComparator = comparator;
    }

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

        if (child + 1 < len && cmp(arr[child + 1], arr[child])) {
            child++; // right child
        }

        if (cmp(arr[child], arr[parent])) {
            swap(arr, child, parent);
            siftDown(arr, len, child);
        }
    }

    private void swap(int[] arr, int a, int b) {
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }

    private boolean cmp(int a, int b) {
        return mComparator.compare(a, b) > 0;
    }
}
