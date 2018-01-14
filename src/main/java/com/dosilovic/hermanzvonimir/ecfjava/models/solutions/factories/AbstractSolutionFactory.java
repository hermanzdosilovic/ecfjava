package com.dosilovic.hermanzvonimir.ecfjava.models.solutions.factories;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstractSolutionFactory<T> implements ISolutionFactory<T> {

    @Override
    public Collection<ISolution<T>> createMultipleInstances(int numberOfInstances) {
        Collection<ISolution<T>> instances = new ArrayList<>();
        for (int i = 0; i < numberOfInstances; i++) {
            instances.add(createInstance());
        }
        return instances;
    }
}
