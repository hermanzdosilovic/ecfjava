package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.ga;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.IPopulationMetaheuristic;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

public interface IGeneticAlgorithm<T extends ISolution> extends IPopulationMetaheuristic<T> {

    int getGeneration();

    void setGeneration(int generation);

    int getMaxGenerations();

    void setMaxGenerations(int maxGenerations);
}
