package com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.AbstractSolution;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.IRandom;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.Random;

import java.util.Arrays;

public class BitVector extends AbstractSolution implements IVector<Boolean> {

    private boolean[] data;
    private int       cardinality;

    public BitVector(int size) {
        data = new boolean[size];
    }

    public BitVector(boolean[] data) {
        this.data = data.clone();
        countNumberOfSetBits();
    }

    public BitVector(BitVector vector) {
        this(vector.data);
        setFitness(vector.getFitness());
        setPenalty(vector.getPenalty());
    }

    @Override
    public Boolean getValue(int index) {
        return data[index];
    }

    @Override
    public void setValue(int index, Boolean value) {
        if (data[index] && !value) {
            cardinality--;
        } else if (!data[index] && value) {
            cardinality++;
        }

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
            data[i] = random.nextBoolean();
        }
    }

    @Override
    public BitVector copy() {
        return new BitVector(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BitVector bitVector = (BitVector) o;
        return Arrays.equals(data, bitVector.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");
        int i = 0;
        for (boolean bool : data) {
            stringBuilder.append(bool ? 1 : 0);
            if (++i < data.length) {
                stringBuilder.append("; ");
            }
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public void flip(int index) {
        setValue(index, !data[index]);
    }

    public int getCardinality() {
        return cardinality;
    }

    private void countNumberOfSetBits() {
        for (boolean bit : data) {
            if (bit) {
                cardinality++;
            }
        }
    }
}
