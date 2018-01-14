package com.dosilovic.hermanzvonimir.ecfjava.models.solutions.factories;

import java.util.Collection;

public interface IFactory<T> {

    T createInstance();

    Collection<T> createMultipleInstances(int numberOfInstances);
}
