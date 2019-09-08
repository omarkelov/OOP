package ru.nsu.fit.markelov;

public class Task_1_1 {
    public static void main(String[] args) {
        Integer[][] arrays = {
                {5, 4, 3, 2, 1},
                {100, -9, 0, 5, 0, 0, 27, 0, -7, 5},
        };

        HeapSort heapSort = new HeapSort<Integer>() {
            @Override
            public boolean compare(Integer a, Integer b) {
                return a > b;
            }
        };

        for (int i = 0; i < arrays.length; i++) {
            System.out.println("Test " + (i + 1) + ":");
            printArray(arrays[i]);
            printArray((Integer[]) heapSort.sort(arrays[i])); // ??? почему возвращает Object[] ???
        }
    }

    private static void printArray(Integer[] arr) {
        System.out.print("{");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i != arr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.print("}\n");
    }
}
