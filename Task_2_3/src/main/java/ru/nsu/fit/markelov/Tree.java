package ru.nsu.fit.markelov;

public class Tree<T> {

    private int mDimension;
    private T mData;
    private Tree[] children;

    public Tree(T data) {
        this(2, data);
    }

    public Tree(int dimension, T data) {
        mDimension = dimension;
        mData = data;
        children = new Tree[mDimension];
    }
}
