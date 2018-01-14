package com.dosilovic.hermanzvonimir.ecfjava.models.solutions.factories;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.SimpleSolution;

public class SimpleSolutionFactory<T> extends AbstractSolutionFactory<T> {

    private IFactory<T> representativeFactory;

    public SimpleSolutionFactory(IFactory<T> representativeFactory) {
        this.representativeFactory = representativeFactory;
    }

    @Override
    public SimpleSolution<T> createInstance() {
        return new SimpleSolution<>(representativeFactory.createInstance());
    }
}
