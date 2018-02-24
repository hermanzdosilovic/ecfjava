package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

import java.util.Collection;

public interface IPopulationMetaheuristic<T extends ISolution> extends IMetaheuristic<T> {

    Collection<T> getInitialPopulation();

    void setInitialPopulation(Collection<T> initialPopulation);

    Collection<T> getPopulation();

    void setPopulation(Collection<T> population);

    T run(Collection<T> initialPopulation);
}
