package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

import java.util.Collection;

public interface IPopulationMetaheuristic<T> extends IMetaheuristic<T> {

    Collection<ISolution<T>> getInitialPopulation();

    void setInitialPopulation(Collection<ISolution<T>> initialPopulation);

    Collection<ISolution<T>> getPopulation();

    void setPopulation(Collection<ISolution<T>> population);

    ISolution<T> run(Collection<ISolution<T>> initialPopulation);
}
