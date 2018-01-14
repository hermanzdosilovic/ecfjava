package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

public interface IIndividualMetaheuristic<T> extends IMetaheuristic<T> {

    ISolution<T> getInitialSolution();

    void setInitialSolution(ISolution<T> initialSolution);

    ISolution<T> getSolution();

    void setSolution(ISolution<T> solution);

    ISolution<T> run(ISolution<T> initialSolution);
}
