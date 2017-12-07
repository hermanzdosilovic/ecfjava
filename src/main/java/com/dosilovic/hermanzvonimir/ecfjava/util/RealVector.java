package com.dosilovic.hermanzvonimir.ecfjava.util;

import org.apache.commons.math3.linear.ArrayRealVector;

public class RealVector extends ArrayRealVector implements IVector<Double> {

    public RealVector(int size) {
        super(size);
    }

    public RealVector(double... d) {
        super(d);
    }

    public RealVector(RealVector vector) {
        super(vector.toArray());
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

    @Override
    public RealVector clone() {
        return new RealVector(this);
    }
}
