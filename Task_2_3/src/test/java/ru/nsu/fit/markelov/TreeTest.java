package ru.nsu.fit.markelov;

import org.junit.Assert;
import org.junit.Test;

public class TreeTest {

    private int i;
    private String[] traverseList;

    @Test
    public void testDfs() {
        Tree<String> nsu = new Tree<>("NSU");
        nsu.add("MMF");
        nsu.add("FF");
        Tree<String> fit = nsu.add("FIT");
        nsu.add("GGF");
        fit.add("C");
        fit.add("KOI");

        nsu.setIteratorType(Tree.ITERATOR_TYPE.DFS);

        traverseList = new String[] {"NSU", "MMF", "FF", "FIT", "C", "KOI", "GGF"};
        i = 0;
        for (Tree<String> child : nsu) {
            Assert.assertEquals(traverseList[i++], child.getValue());
        }

        nsu.remove("FF");
        traverseList = new String[] {"NSU", "MMF", "FIT", "C", "KOI", "GGF"};
        i = 0;
        for (Tree<String> child : nsu) {
            Assert.assertEquals(traverseList[i++], child.getValue());
        }

        fit.remove();
        traverseList = new String[] {"NSU", "MMF", "GGF"};
        i = 0;
        for (Tree<String> child : nsu) {
            Assert.assertEquals(traverseList[i++], child.getValue());
        }
    }

    @Test
    public void testBfs() {
        Tree<String> nsu = new Tree<>("NSU");
        nsu.add("MMF");
        nsu.add("FF");
        Tree<String> fit = nsu.add("FIT");
        nsu.add("GGF");
        fit.add("C");
        fit.add("KOI");

        traverseList = new String[] {"NSU", "MMF", "FF", "FIT", "GGF", "C", "KOI"};
        i = 0;
        for (Tree<String> child : nsu) {
            Assert.assertEquals(traverseList[i++], child.getValue());
        }

        nsu.remove("FF");
        traverseList = new String[] {"NSU", "MMF", "FIT", "GGF", "C", "KOI"};
        i = 0;
        for (Tree<String> child : nsu) {
            Assert.assertEquals(traverseList[i++], child.getValue());
        }

        fit.remove();
        traverseList = new String[] {"NSU", "MMF", "GGF"};
        i = 0;
        for (Tree<String> child : nsu) {
            Assert.assertEquals(traverseList[i++], child.getValue());
        }
    }
}
