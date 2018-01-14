package com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector;

import java.util.Random;

public class BitVector implements IVector<Boolean> {

    private boolean[] bits;
    private int       cardinality;

    private static final Random RAND = new Random();

    public BitVector(int size) {
        bits = new boolean[size];
    }

    public BitVector(boolean... bits) {
        this.bits = bits.clone();
        countNumberOfSetBits();
    }

    public BitVector(BitVector bitVector) {
        this(bitVector.bits);
    }

    public BitVector(int size, boolean initialValue) {
        this(size);
        for (int i = 0; i < size; i++) {
            setValue(i, initialValue);
        }
    }

    public BitVector(int size, Boolean setBitsAtRandom) {
        this(size);
        if (setBitsAtRandom) {
            for (int i = 0; i < size; i++) {
                setValue(i, RAND.nextBoolean());
            }
        }
    }

    @Override
    public Boolean getValue(int index) {
        return bits[index];
    }

    @Override
    public void setValue(int index, Boolean value) {
        if (bits[index] && !value) {
            cardinality--;
        } else if (!bits[index] && value) {
            cardinality++;
        }

        bits[index] = value;
    }

    @Override
    public int getSize() {
        return bits.length;
    }

    @Override
    public BitVector copy() {
        return new BitVector(this);
    }

    public void flip(int index) {
        setValue(index, !bits[index]);
    }

    public int getCardinality() {
        return cardinality;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");
        for (int i = 0; i < bits.length; i++) {
            stringBuilder.append(bits[i]);
            if (i + 1 != bits.length) {
                stringBuilder.append("; ");
            }
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private void countNumberOfSetBits() {
        for (boolean bit : bits) {
            if (bit) {
                cardinality++;
            }
        }
    }
}
