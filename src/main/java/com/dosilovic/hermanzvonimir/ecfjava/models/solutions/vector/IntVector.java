package com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector;

import java.util.Random;

public class IntVector implements IVector<Integer> {

    private int[]   components;
    private boolean isBounded;
    private int     minComponentValue;
    private int     maxComponentValue;

    private static final Random RAND = new Random();

    public IntVector(int size) {
        components = new int[size];
    }

    public IntVector(int... ints) {
        components = ints.clone();
    }

    public IntVector(IntVector intVector) {
        this(intVector.components);
        this.minComponentValue = intVector.minComponentValue;
        this.maxComponentValue = intVector.maxComponentValue;
        this.isBounded = intVector.isBounded;
    }

    public IntVector(int size, int minComponentValue, int maxComponentValue, boolean isBounded) {
        this(size);

        if (minComponentValue > maxComponentValue) {
            throw new IllegalArgumentException("Minimum component value cannot be greater than maximal component value");
        }

        for (int i = 0; i < size; i++) {
            components[i] = minComponentValue + RAND.nextInt(maxComponentValue - minComponentValue + 1);
        }

        this.isBounded = isBounded;
        this.minComponentValue = minComponentValue;
        this.maxComponentValue = maxComponentValue;
    }

    public IntVector(int size, int minComponentValue, int maxComponentValue) {
        this(size, minComponentValue, maxComponentValue, false);
    }

    public IntVector(int size, int initialValue) {
        this(size, initialValue, initialValue);
    }

    @Override
    public Integer getValue(int index) {
        return components[index];
    }

    @Override
    public void setValue(int index, Integer value) {
        int boundedValue = value;
        if (isBounded) {
            boundedValue = Math.max(boundedValue, minComponentValue);
            boundedValue = Math.min(boundedValue, maxComponentValue);
        }
        components[index] = boundedValue;
    }

    @Override
    public int getSize() {
        return components.length;
    }

    @Override
    public IntVector copy() {
        return new IntVector(this);
    }

    public int[] toArray() {
        return components.clone();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("{ ");
        for (int i = 0; i < components.length; i++) {
            builder.append(components[i]);
            if (i + 1 < components.length) {
                builder.append("; ");
            }
        }
        builder.append("}");
        return builder.toString();
    }
}
