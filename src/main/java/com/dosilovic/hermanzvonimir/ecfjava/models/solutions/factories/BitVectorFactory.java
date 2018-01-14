package com.dosilovic.hermanzvonimir.ecfjava.models.solutions.factories;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.BitVector;

public class BitVectorFactory extends AbstractFactory<BitVector> {

    private BitVector bitVector;

    public BitVectorFactory(BitVector bitVector) {
        this.bitVector = new BitVector(bitVector);
    }

    @Override
    public BitVector createInstance() {
        return bitVector.copy();
    }
}
