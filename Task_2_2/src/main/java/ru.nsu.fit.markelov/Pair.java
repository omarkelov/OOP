package ru.nsu.fit.markelov;

public class Pair<K extends Comparable, V> implements Comparable<Pair> {

    private K mKey;
    private V mValue;

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
        mKey = key;
        mValue = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public int compareTo(Pair anotherPair) {
        return mKey.compareTo(anotherPair.getKey());
    }

    /**
     * @return the pair as <(key, value)> string
     */
    @Override
    public String toString() {
        return "(" + mKey + "; " + mValue + ")";
    }

    /**
     * return the key of this pair
     */
    public K getKey() {
        return mKey;
    }

    /**
     * return the value of this pair
     */
    public V getValue() {
        return mValue;
    }
}
