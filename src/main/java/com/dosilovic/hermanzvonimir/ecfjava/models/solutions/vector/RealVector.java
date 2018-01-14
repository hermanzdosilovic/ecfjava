package com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector;

import org.apache.commons.math3.linear.ArrayRealVector;

import java.util.Random;

public class RealVector extends ArrayRealVector implements IVector<Double> {

    private boolean isBounded;
    private double  minComponentValue;
    private double  maxComponentValue;

    private static final Random RAND = new Random();

    public RealVector(int size) {
        super(size);
    }

    public RealVector(double... data) {
        super(data);
    }

    public RealVector(RealVector realVector) {
        super(realVector.toArray());
        this.minComponentValue = realVector.minComponentValue;
        this.maxComponentValue = realVector.maxComponentValue;
        this.isBounded = realVector.isBounded;
    }

    public RealVector(int size, double minComponentValue, double maxComponentValue, boolean isBounded) {
        this(size);

        if (minComponentValue > maxComponentValue) {
            throw new IllegalArgumentException("Minimum component value cannot be greater than maximal component value");
        }

        for (int i = 0; i < size; i++) {
            setEntry(i, minComponentValue + RAND.nextDouble() * (maxComponentValue - minComponentValue));
        }

        this.isBounded = isBounded;
        this.minComponentValue = minComponentValue;
        this.maxComponentValue = maxComponentValue;
    }

    public RealVector(int size, double minComponentValue, double maxComponentValue) {
        this(size, minComponentValue, maxComponentValue, false);
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
        double boundedValue = value;
        if (isBounded) {
            boundedValue = Math.max(boundedValue, minComponentValue);
            boundedValue = Math.min(boundedValue, maxComponentValue);
        }
        setEntry(index, boundedValue);
    }

    @Override
    public int getSize() {
        return getDimension();
    }

    @Override
    public RealVector copy() {
        return new RealVector(this);
    }
}
