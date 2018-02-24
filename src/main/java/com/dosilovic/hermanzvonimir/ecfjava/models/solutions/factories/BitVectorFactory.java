package com.dosilovic.hermanzvonimir.ecfjava.models.solutions.factories;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.BitVector;

public class BitVectorFactory extends AbstractFactory<BitVector> {

    private BitVector template;

    public BitVectorFactory(BitVector template) {
        this.template = template;
    }

    @Override
    public BitVector createInstance() {
        return template.copy();
    }
}
