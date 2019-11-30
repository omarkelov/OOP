package ru.nsu.fit.markelov;

import org.junit.Assert;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class TreeTest {

    private int i;
    private String[] traverseList;

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
    }

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
    }

    @Test
    public void testRemove() {
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

        Assert.assertTrue(nsu.remove("FF"));
        traverseList = new String[] {"NSU", "MMF", "FIT", "GGF", "C", "KOI"};
        i = 0;
        for (Tree<String> child : nsu) {
            Assert.assertEquals(traverseList[i++], child.getValue());
        }

        Assert.assertTrue(fit.remove());
        traverseList = new String[] {"NSU", "MMF", "GGF"};
        i = 0;
        for (Tree<String> child : nsu) {
            Assert.assertEquals(traverseList[i++], child.getValue());
        }

        Assert.assertFalse(nsu.remove("qwerty"));
        Assert.assertFalse(fit.remove());
    }

    @Test
    public void testNoSuchElementException() {
        Tree<String> nsu = new Tree<>("NSU");

        Iterator<Tree<String>> iterator = nsu.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().getValue());
        }

        try {
            iterator.next();
            Assert.fail();
        } catch (NoSuchElementException e) {
            System.out.println(e.getClass().getSimpleName() + " got caught.");
        }
    }

    @Test
    public void testConcurrentModificationException() {
        Tree<String> nsu = new Tree<>("NSU");
        nsu.add("MMF");
        nsu.add("FF");

        try {
            for (Tree<String> child : nsu) {
                System.out.println(child.getValue());
                nsu.add("FIT");
            }
            Assert.fail();
        } catch (ConcurrentModificationException e) {
            System.out.println(e.getClass().getSimpleName() + " got caught.");
        }
    }

    @Test
    public void testRootRemovingException() {
        Tree<String> nsu = new Tree<>("NSU");

        try {
            nsu.remove();
            Assert.fail();
        } catch (RuntimeException e) {
            System.out.println(e.getClass().getSimpleName() + " got caught.");
            Assert.assertEquals("Unable to remove the root.", e.getMessage());
        }
    }
}
