package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.ga;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.IPopulationMetaheuristic;

public interface IGeneticAlgorithm<T> extends IPopulationMetaheuristic<T> {

    int getGeneration();

    void setGeneration(int generation);

    int getMaxGenerations();

    void setMaxGenerations(int maxGenerations);
}
