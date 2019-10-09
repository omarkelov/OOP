package ru.nsu.fit.markelov;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Iterator;

public class Stack<T> implements Iterable<T> {

    private static double RESIZE_FACTOR = 1.5;

    private Object[] objects;
    private int count;
    private int capacity;
    private double resizeFactor;

    /**
     * Creates a Stack that represents a last-in-first-out (LIFO) model
     * of objects. The usual push and pop operations are provided, as
     * well as a method to get the amount of objects kept.
     * <p>
     * When a stack is first created, it contains no items and no
     * memory is allocated. However, the size of a stack can grow
     * or shrink depending on a default resize factor (1.5) to
     * accommodate adding and removing items.
     * <p>
     * A Stack implements Iterable interface.
     *
     * @see Iterable
     */
    public Stack() {
        this(RESIZE_FACTOR);
    }

    /**
     * Creates a Stack that represents a last-in-first-out (LIFO) model
     * of objects. The usual push and pop operations are provided, as
     * well as a method to get the amount of objects kept.
     * <p>
     * When a stack is first created, it contains no items and no
     * memory is allocated. However, the size of a stack can grow
     * or shrink depending on a specified resize factor to
     * accommodate adding and removing items.
     * <p>
     * A Stack implements Iterable interface.
     *
     * @param resizeFactor the resize factor that is used for Stack growing.
     * @see   Iterable
     */
    public Stack(double resizeFactor) {
        count = 0;
        capacity = 0;
        this.resizeFactor = resizeFactor;
    }

    /**
     * Pushes an object onto the top of this stack.
     * <p>
     * If there is no enough space for object storing, the capacity of the
     * stack is increased automatically.
     *
     * @param elem the element to add.
     */
    public void push(T elem) {
        if (objects == null) {
            capacity = 1;
            objects = new Object[1];
        } else if (capacity == count) {
            capacity = (int) Math.ceil(capacity * resizeFactor);
            objects = Arrays.copyOf(objects, capacity);
        }

        objects[count++] = elem;
    }

    /**
     * Removes the object at the top of this stack and returns it.
     * <p>
     * If there is too much space after object removing, the capacity of the
     * stack is decreased automatically.
     *
     * @return                     the object at the top of this stack
                                   or null, if it is empty.
     * @throws EmptyStackException if the stack is empty.
     */
    @SuppressWarnings("unchecked")
    public T pop() {
        if (count == 0) {
            throw new EmptyStackException();
        }

        T elem = (T) objects[--count];

        if (count <= (int) Math.ceil(capacity / (resizeFactor * resizeFactor))) {
            capacity = (int) Math.ceil(capacity / resizeFactor);
            objects = Arrays.copyOf(objects, capacity);
        }

        return elem;
    }

    /**
     * Returns the amount of objects in this stack.
     *
     * @return the amount of objects in this stack.
     */
    public int count() {
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

    // for testing only
    public int getCapacity() {
        return capacity;
    }
}
