package com.dosilovic.hermanzvonimir.ecfjava.models.solutions.factories;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.RealVector;

public class RealVectorFactory extends AbstractFactory<RealVector> {

    private RealVector realVector;

    public RealVectorFactory(RealVector realVector) {
        this.realVector = new RealVector(realVector);
    }

    @Override
    public RealVector createInstance() {
        return realVector.copy();
    }
}
