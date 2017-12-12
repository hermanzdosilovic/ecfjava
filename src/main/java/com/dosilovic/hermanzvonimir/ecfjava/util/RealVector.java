package com.dosilovic.hermanzvonimir.ecfjava.util;

import org.apache.commons.math3.linear.ArrayRealVector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class RealVector extends ArrayRealVector implements IVector<Double> {

    private static final Random RAND = new Random();

    public RealVector(int size) {
        super(size);
    }

    public RealVector(double... d) {
        super(d);
    }

    public RealVector(RealVector vector) {
        super(vector.toArray());
    }

    public RealVector(int size, double minComponentValue, double maxComponentValue) {
        this(size);
        for (int i = 0; i < size; i++) {
            setEntry(i, minComponentValue + RAND.nextDouble() * (maxComponentValue - minComponentValue));
        }
    }

    public RealVector(int size, double initialValue) {
        this(size, initialValue, initialValue);
    }

    @Override
    public Double getValue(int index) {
        return getEntry(index);
    }

    @Override
    public void setValue(int index, Double value) {
        setEntry(index, value);
    }

    @Override
    public int getSize() {
        return getDimension();
    }

    @Override public Object clone() {
        return new RealVector(this);
    }

    public static Collection<RealVector> createCollection(int collectionSize, int vectorSize) {
        Collection<RealVector> collection = new ArrayList<>(collectionSize);
        for (int i = 0; i < collectionSize; i++) {
            collection.add(new RealVector(vectorSize));
        }
        return collection;
    }

    public static Collection<RealVector> createCollection(
        int collectionSize,
        int vectorSize,
        double minComponentValue,
        double maxComponentValue
    ) {
        Collection<RealVector> collection = new ArrayList<>(collectionSize);
        for (int i = 0; i < collectionSize; i++) {
            collection.add(new RealVector(vectorSize, minComponentValue, maxComponentValue));
        }
        return collection;
    }
}
