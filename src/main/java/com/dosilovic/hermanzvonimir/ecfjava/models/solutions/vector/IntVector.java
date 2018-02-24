package com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.AbstractSolution;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.IRandom;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.Random;

import java.util.Arrays;

public class IntVector extends AbstractSolution implements IVector<Integer> {

    private int[] data;

    public IntVector(int size) {
        data = new int[size];
    }

    public IntVector(int size, int value) {
        this(size);
        for (int i = 0; i < size; i++) {
            data[i] = value;
        }
    }

    public IntVector(int[] data) {
        this.data = data.clone();
    }

    public IntVector(IntVector vector) {
        this(vector.data);
        setFitness(vector.getFitness());
        setPenalty(vector.getPenalty());
    }

    @Override
    public Integer getValue(int index) {
        return data[index];
    }

    @Override
    public void setValue(int index, Integer value) {
        data[index] = value;
    }

    @Override
    public int getSize() {
        return data.length;
    }

    @Override
    public void randomizeValues() {
        IRandom random = Random.getRandom();
        for (int i = 0; i < data.length; i++) {
            data[i] = random.nextInt();
        }
    }

    @Override
    public IntVector copy() {
        return new IntVector(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IntVector that = (IntVector) o;
        return Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }
}
