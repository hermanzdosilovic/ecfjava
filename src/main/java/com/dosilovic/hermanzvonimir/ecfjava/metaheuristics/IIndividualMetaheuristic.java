package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

public interface IIndividualMetaheuristic<T extends ISolution> extends IMetaheuristic<T> {

    T getInitialSolution();

    void setInitialSolution(T initialSolution);

    T getSolution();

    void setSolution(T solution);

    T run(T initialSolution);
}
