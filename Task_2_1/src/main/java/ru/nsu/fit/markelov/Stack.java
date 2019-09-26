package ru.nsu.fit.markelov;

import java.util.Arrays;
import java.util.Iterator;

public class Stack<T> implements Iterable<T> {

    private static double STACK_RESIZE_FACTOR = 1.5;

    private Object[] mObjects;
    private int mCount;
    private int mCapacity;
    private double mResizeFactor;

    /**
     * Creates a Stack that represents a last-in-first-out (LIFO) model
     * of objects. The usual push and pop operations are provided, as
     * well as a method to get the amount of objects kept.
     * <p>
     * When a stack is first created, it contains no items and no
     * memory is allocated. Then the size of a Stack can grow depending
     * on a default resize factor (1.5) to accommodate adding items.
     * <p>
     * A Stack implements Iterable interface.
     *
     * @see Iterable
     */
    public Stack() {
        this(STACK_RESIZE_FACTOR);
    }

    /**
     * Creates a Stack that represents a last-in-first-out (LIFO) model
     * of objects. The usual push and pop operations are provided, as
     * well as a method to get the amount of objects kept.
     * <p>
     * When a stack is first created, it contains no items and no
     * memory is allocated. Then the size of a Stack can grow depending
     * on a specified resize factor to accommodate adding items.
     * <p>
     * A Stack implements Iterable interface.
     *
     * @param resizeFactor the resize factor that is used for Stack growing
     * @see   Iterable
     */
    public Stack(double resizeFactor) {
        mResizeFactor = resizeFactor;
    }

    /**
     * Pushes an object onto the top of this stack.
     * <p>
     * If there is no enough space for object storing, the capacity of the
     * stack is increased automatically.
     *
     * @param elem the element to add
     */
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

    /**
     * Removes the object at the top of this stack and returns it.
     *
     * @return he object at the top of this stack
     */
    public T pop() {
        return (mCount != 0) ? (T) mObjects[mCount--] : null;
    }

    /**
     * Returns the amount of objects in this stack.
     *
     * @return the amount of objects in this stack.
     */
    public int count() {
        return mCount;
    }

    /**
     * {@inheritDoc}
     */
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
