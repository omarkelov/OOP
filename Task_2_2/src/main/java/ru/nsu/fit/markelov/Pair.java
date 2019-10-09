package ru.nsu.fit.markelov;

public class Pair<K extends Comparable, V> implements Comparable<Pair> {

    private K key;
    private V value;

    /**
     * Creates a Pair that represents a <(key, value)> object.
     * <p>
     * The key must be of Comparable type as it is used to implement
     * the Comparable interface.
     *
     * @param key   the key
     * @param value the value
     */
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public int compareTo(Pair anotherPair) {
        return key.compareTo(anotherPair.getKey());
    }

    /**
     * @return the pair as <(key, value)> string
     */
    @Override
    public String toString() {
        return "(" + key + "; " + value + ")";
    }

    /**
     * return the key of this pair
     */
    public K getKey() {
        return key;
    }

    /**
     * return the value of this pair
     */
    public V getValue() {
        return value;
    }
}
