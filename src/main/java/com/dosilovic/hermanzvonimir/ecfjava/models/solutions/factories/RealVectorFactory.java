package com.dosilovic.hermanzvonimir.ecfjava.models.solutions.factories;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.RealVector;

public class RealVectorFactory extends AbstractFactory<RealVector> {

    private RealVector template;

    public RealVectorFactory(RealVector template) {
        this.template = template;
    }

    @Override
    public RealVector createInstance() {
        return template.copy();
    }
}
