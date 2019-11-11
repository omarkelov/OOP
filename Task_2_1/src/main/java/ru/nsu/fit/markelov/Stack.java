package ru.nsu.fit.markelov;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Iterator;

/**
 * <code>Stack</code> represents a last-in-first-out (LIFO) model
 * of objects. The usual push and pop operations are provided, as
 * well as a method to get the amount of objects kept.
 * <p>
 * When a stack is first created, it contains no items and no
 * memory is allocated. However, the size of a stack can grow
 * or shrink depending on a default resize factor (1.5) to
 * accommodate adding and removing items.
 * <p>
 * A Stack implements <code>Iterable</code> interface.
 *
 * @author Oleg Markelov
 * @see    Iterable
 */
public class Stack<T> implements Iterable<T> {

    private static double RESIZE_FACTOR = 1.5;

    private Object[] mObjects;
    private int mCount;
    private int mCapacity;
    private double mResizeFactor;

    /**
     * Creates a <code>Stack</code> with default resize factor.
     */
    public Stack() {
        this(RESIZE_FACTOR);
    }

    /**
     * Creates a <code>Stack</code> with specified resize factor.
     */
    public Stack(double resizeFactor) {
        mCount = 0;
        mCapacity = 0;
        this.mResizeFactor = resizeFactor;
    }

    /**
     * Pushes an object onto the top of this stack.
     * <p>
     * If there is no enough space for object storing, the capacity
     * of the stack is increased automatically.
     *
     * @param elem the element to add.
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
     * <p>
     * If there is too much space after object removing, the
     * capacity of the stack is decreased automatically.
     *
     * @return                     the object at the top of this
     *                             stack or null, if it is empty.
     * @throws EmptyStackException if the stack is empty.
     */
    @SuppressWarnings("unchecked")
    public T pop() {
        if (mCount == 0) {
            throw new EmptyStackException();
        }

        T elem = (T) mObjects[--mCount];

        if (mCount <= (int) Math.ceil(mCapacity / (mResizeFactor * mResizeFactor))) {
            mCapacity = (int) Math.ceil(mCapacity / mResizeFactor);
            mObjects = Arrays.copyOf(mObjects, mCapacity);
        }

        return elem;
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
    @SuppressWarnings("unchecked")
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < mCount;
            }

            @Override
            public T next() {
                return (T) mObjects[i++];
            }
        };
    }

    /**
     * Returns the capacity of this stack.
     * <p>
     * <i>This method should be used for testing only</i>.
     *
     * @return the capacity of this stack.
     */
    public int getCapacity() {
        return mCapacity;
    }
}
