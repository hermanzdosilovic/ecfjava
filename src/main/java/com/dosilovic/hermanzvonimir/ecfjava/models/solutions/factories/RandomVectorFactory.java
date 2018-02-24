package com.dosilovic.hermanzvonimir.ecfjava.models.solutions.factories;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.IVector;

public class RandomVectorFactory<T extends IVector> extends AbstractFactory<T> {

    IFactory<T> vectorFactory;

    public RandomVectorFactory(IFactory<T> vectorFactory) {
        this.vectorFactory = vectorFactory;
    }

    @Override
    public T createInstance() {
        T vector = vectorFactory.createInstance();
        vector.randomizeValues();
        return vector;
    }
}
