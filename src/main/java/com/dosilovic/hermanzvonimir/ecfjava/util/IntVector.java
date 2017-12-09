package com.dosilovic.hermanzvonimir.ecfjava.util;

import java.util.Random;

public class IntVector implements IVector<Integer> {

    private int[] components;

    private static final Random RAND = new Random();

    public IntVector(int size) {
        components = new int[size];
    }

    public IntVector(int... i) {
        components = i.clone();
    }

    public IntVector(IntVector vector) {
        this(vector.components);
    }

    public IntVector(int size, int minComponentSize, int maxComponentSize) {
        this(size);
        for (int i = 0; i < size; i++) {
            components[i] = minComponentSize + RAND.nextInt(maxComponentSize - minComponentSize + 1);
        }
    }

    @Override
    public Integer getValue(int index) {
        return components[index];
    }

    @Override
    public void setValue(int index, Integer value) {
        components[index] = value;
    }

    @Override
    public int getSize() {
        return components.length;
    }

    @Override
    public Object clone() {
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
