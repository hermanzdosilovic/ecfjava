package com.dosilovic.hermanzvonimir.ecfjava.models.solutions.factories;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.IntVector;

public class IntVectorFactory extends AbstractFactory<IntVector> {

    private IntVector template;

    public IntVectorFactory(IntVector template) {
        this.template = template;
    }

    @Override
    public IntVector createInstance() {
        return template.copy();
    }
}
