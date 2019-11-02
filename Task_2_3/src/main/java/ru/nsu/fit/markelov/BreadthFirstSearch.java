package ru.nsu.fit.markelov;

import java.util.ArrayList;

public class BreadthFirstSearch<T> implements PathSearch<T> {

    private Tree<T> tree;
    private ArrayList<Tree<T>> path;

    public BreadthFirstSearch(Tree<T> tree) {
        this.tree = tree;
        path = new ArrayList<>();

        calculatePath();
    }

    @Override
    public ArrayList<Tree<T>> getPath() {
        return path;
    }

    private void calculatePath() {
        path.add(tree);

        int i = 0;
        while (path.size() > i) {
            Tree<T> nextTree = path.get(i++);
            path.addAll(nextTree.getChildren());
        }
    }
}
