package com.dosilovic.hermanzvonimir.ecfjava.models.solutions.factories;

import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstractFactory<T> implements IFactory<T> {

    @Override
    public Collection<T> createMultipleInstances(int numberOfInstances) {
        Collection<T> instances = new ArrayList<>();
        for (int i = 0; i < numberOfInstances; i++) {
            instances.add(createInstance());
        }
        return instances;
    }
}
