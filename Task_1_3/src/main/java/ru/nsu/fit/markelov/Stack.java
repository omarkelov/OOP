package ru.nsu.fit.markelov;

import java.util.Arrays;
import java.util.Iterator;

public class Stack<T> implements Iterable<T> {

    private static double STACK_RESIZE_FACTOR = 1.5;

    private Object[] mObjects;
    private int mCount;
    private int mCapacity;
    private double mResizeFactor;

    public Stack() {
        this(STACK_RESIZE_FACTOR);
    }

    public Stack(double resizeFactor) {
        mResizeFactor = resizeFactor;
    }

    public void push(T elem) {
        if (mObjects == null) {
            mCapacity = 1;
            mObjects = new Object[1];
        } else if (mCapacity == mCount) {
            mCapacity = (int) Math.ceil(mCapacity * mResizeFactor);
            mObjects = Arrays.copyOf(mObjects, mCapacity);
        }

        mObjects[mCount++] = elem;
    }

    public T pop() {
        return (mCount != 0) ? (T) mObjects[mCount--] : null;
    }

    public int count() {
        return mCount;
    }

    public Object[] getData() {
        return Arrays.copyOf(mObjects, mCount);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < mCount && mObjects[i] != null;
            }

            @Override
            public T next() {
                return (T) mObjects[i++];
            }
        };
    }

    public void print() {
        System.out.print(mCapacity + ": ");
        for (int i = 0; i < mCapacity; i++) {
            System.out.print(mObjects[i] + " ");
        }
        System.out.println();
    }
}
