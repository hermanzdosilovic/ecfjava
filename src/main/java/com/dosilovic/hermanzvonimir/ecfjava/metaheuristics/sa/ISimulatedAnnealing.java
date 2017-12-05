package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.IMetaheuristic;

public interface ISimulatedAnnealing<T> extends IMetaheuristic<T> {

    public T run(T initialSolution);
}
