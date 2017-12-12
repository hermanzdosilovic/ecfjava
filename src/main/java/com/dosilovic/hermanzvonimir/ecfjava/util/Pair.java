package com.dosilovic.hermanzvonimir.ecfjava.util;

import java.util.Objects;

/**
 * Represents a pair of two generic types.
 *
 * @author Herman Zvonimir Dosilovic
 */
public class Pair<F, S> {

    /**
     * First value in pair.
     */
    private F first;

    /**
     * Second value in pair.
     */
    private S second;

    /**
     * Empty default constructor.
     */
    public Pair() {}

    /**
     * Creates <code>Pair</code> with two members.
     *
     * @param first  - first member of pair
     * @param second - second member of pair
     */
    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Returns first member of pair.
     *
     * @return first member of pair
     */
    public F getFirst() {
        return first;
    }

    /**
     * Sets first member of pair.
     *
     * @param first - new value of first member
     */
    public void setFirst(F first) {
        this.first = first;
    }

    /**
     * Returns second member of pair.
     *
     * @return second member of pair
     */
    public S getSecond() {
        return second;
    }

    /**
     * Sets second member of pair.
     *
     * @param second - new value of second member
     */
    public void setSecond(S second) {
        this.second = second;
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) &&
               Objects.equals(second, pair.second);
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

}

