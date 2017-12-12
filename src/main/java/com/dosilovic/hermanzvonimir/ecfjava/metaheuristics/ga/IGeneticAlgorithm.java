package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.ga;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.IMetaheuristic;

import java.util.Collection;

public interface IGeneticAlgorithm<T> extends IMetaheuristic<T> {

    public T run(Collection<T> initialPopulation);

    public void setInitialPopulation(Collection<T> initialPopulation);
}
