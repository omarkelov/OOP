package ru.nsu.fit.markelov;

import java.util.ArrayList;

public class DepthFirstSearch<T> implements PathSearch<T> {

    private Tree<T> tree;
    private ArrayList<Tree<T>> path;

    public DepthFirstSearch(Tree<T> tree) {
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
        dfs(tree);
    }

    private void dfs(Tree<T> parent) {
        for (Tree<T> child : parent.getChildren()) {
            path.add(child);
            dfs(child);
        }
    }
}
