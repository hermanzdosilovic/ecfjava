package com.dosilovic.hermanzvonimir.ecfjava.models.crossovers;

import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;

import java.util.Collection;

public interface ICrossover<T> {

    public Collection<Solution<T>> cross(Solution<T> firstParent, Solution<T> secondParent);
}
