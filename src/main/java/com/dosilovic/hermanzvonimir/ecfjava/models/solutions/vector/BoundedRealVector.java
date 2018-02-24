package com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector;

import com.dosilovic.hermanzvonimir.ecfjava.util.random.IRandom;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.Random;

public class BoundedRealVector extends RealVector {

    private RealVector lowerBound;
    private RealVector upperBound;

    public BoundedRealVector(int size, double lowerBound, double upperBound) {
        super(size);
        this.lowerBound = new RealVector(size, lowerBound);
        this.upperBound = new RealVector(size, upperBound);
    }

    public BoundedRealVector(double lowerBound, double upperBound, double[] data) {
        super(data);
        this.lowerBound = new RealVector(data.length, lowerBound);
        this.upperBound = new RealVector(data.length, upperBound);
    }

    public BoundedRealVector(int size, RealVector lowerBound, RealVector upperBound) {
        super(size);
        this.lowerBound = lowerBound.copy();
        this.upperBound = upperBound.copy();
    }

    public BoundedRealVector(RealVector lowerBound, RealVector upperBound, double[] data) {
        super(data);
        this.lowerBound = lowerBound.copy();
        this.upperBound = upperBound.copy();
    }

    public BoundedRealVector(BoundedRealVector boundedVector) {
        super(boundedVector);
        this.lowerBound = boundedVector.lowerBound.copy();
        this.upperBound = boundedVector.upperBound.copy();
    }

    @Override
    public void setValue(int index, Double value) {
        double boundedValue = value;
        boundedValue = Math.max(boundedValue, lowerBound.getValue(Math.min(lowerBound.getSize() - 1, index)));
        boundedValue = Math.min(boundedValue, upperBound.getValue(Math.min(upperBound.getSize() - 1, index)));
        super.setValue(index, boundedValue);
    }

    @Override
    public void randomizeValues() {
        IRandom random = Random.getRandom();
        for (int i = 0, size = getSize(); i < size; i++) {
            double min = lowerBound.getValue(Math.min(lowerBound.getSize() - 1, i));
            double max = upperBound.getValue(Math.min(upperBound.getSize() - 1, i));
            setValue(i, random.nextDouble(min, max));
        }
    }

    @Override
    public BoundedRealVector copy() {
        return new BoundedRealVector(this);
    }
}
