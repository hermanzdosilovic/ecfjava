package com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector;

import com.dosilovic.hermanzvonimir.ecfjava.util.random.IRandom;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.Random;

import java.util.Objects;

public class BoundedIntVector extends IntVector {

    private IntVector lowerBound;
    private IntVector upperBound;

    public BoundedIntVector(int size, int lowerBound, int upperBound) {
        super(size);
        this.lowerBound = new IntVector(size, lowerBound);
        this.upperBound = new IntVector(size, upperBound);
    }

    public BoundedIntVector(int lowerBound, int upperBound, int[] data) {
        super(data);
        this.lowerBound = new IntVector(data.length, lowerBound);
        this.upperBound = new IntVector(data.length, upperBound);
    }

    public BoundedIntVector(int size, IntVector lowerBound, IntVector upperBound) {
        super(size);
        this.lowerBound = lowerBound.copy();
        this.upperBound = upperBound.copy();
    }

    public BoundedIntVector(IntVector lowerBound, IntVector upperBound, int[] data) {
        super(data);
        this.lowerBound = lowerBound.copy();
        this.upperBound = upperBound.copy();
    }

    public BoundedIntVector(BoundedIntVector boundedVector) {
        super(boundedVector);
        this.lowerBound = boundedVector.lowerBound.copy();
        this.upperBound = boundedVector.upperBound.copy();
    }

    @Override
    public void setValue(int index, Integer value) {
        int boundedValue = value;
        boundedValue = Math.max(boundedValue, lowerBound.getValue(Math.min(lowerBound.getSize() - 1, index)));
        boundedValue = Math.min(boundedValue, upperBound.getValue(Math.min(upperBound.getSize() - 1, index)));
        super.setValue(index, boundedValue);
    }

    @Override
    public void randomizeValues() {
        IRandom random = Random.getRandom();
        for (int i = 0; i < getSize(); i++) {
            int min = lowerBound.getValue(Math.min(lowerBound.getSize() - 1, i));
            int max = upperBound.getValue(Math.max(upperBound.getSize() - 1, i));
            setValue(i, random.nextInt(min, max + 1));
        }
    }

    @Override
    public BoundedIntVector copy() {
        return new BoundedIntVector(this);
    }
}
