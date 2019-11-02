package ru.nsu.fit.markelov;

import org.junit.Test;

public class TreeTest {

    @Test
    public void test() {
        Tree<String> root = new Tree<>("NSU");
        root.add("MMF");
        root.add("FF");
        Tree<String> fit = root.add("FIT");
        root.add("GGF");
        fit.add("C");
        fit.add("KOI");

        root.useDfsIterator();
        for (Tree<String> child : root) {
            System.out.println(child.getValue());
        }
        System.out.println();

        root.useDfsIterator();
        for (Tree<String> child : root) {
            System.out.println(child.getValue());
        }
        System.out.println();

        root.useBfsIterator();
        for (Tree<String> child : root) {
            System.out.println(child.getValue());
        }
        System.out.println();

        root.useBfsIterator();
        for (Tree<String> child : root) {
            System.out.println(child.getValue());
        }
        System.out.println();
    }
}
