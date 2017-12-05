package com.dosilovic.hermanzvonimir.ecfjava.util;

public class BitVector implements IVector<Boolean> {

    private boolean[] bits;
    private int cardinality;

    public BitVector(int size) {
        bits = new boolean[size];
    }

    public BitVector(BitVector bitVector) {
        this(bitVector.bits);
    }

    public BitVector(boolean... bits) {
        this.bits = bits.clone();
        countNumberOfSetBits();
    }

    @Override public Boolean getValue(int index) {
        return bits[index];
    }

    @Override public void setValue(int index, Boolean value) {
        if (bits[index] && !value) {
            cardinality--;
        } else if (!bits[index] && value) {
            cardinality++;
        }

        bits[index] = value;
    }

    @Override public int getSize() {
        return bits.length;
    }

    public void flip(int index) {
        setValue(index, !bits[index]);
    }

    public int getCardinality() {
        return cardinality;
    }

    private void countNumberOfSetBits() {
        for (int i = 0; i < bits.length; i++) {
            if (bits[i]) {
                cardinality++;
            }
        }
    }

    @Override public Object clone() {
        return new BitVector(this);
    }

    @Override public String toString() {
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
}
