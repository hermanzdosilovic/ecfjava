package com.dosilovic.hermanzvonimir.ecfjava.models.solutions.factories;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.IntVector;

public class IntVectorFactory extends AbstractFactory<IntVector> {

    private IntVector intVector;

    public IntVectorFactory(IntVector intVector) {
        this.intVector = new IntVector(intVector);
    }

    @Override
    public IntVector createInstance() {
        return intVector.copy();
    }
}
