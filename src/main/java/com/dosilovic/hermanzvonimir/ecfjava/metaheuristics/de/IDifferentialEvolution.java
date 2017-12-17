package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.de;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.IMetaheuristic;
import com.dosilovic.hermanzvonimir.ecfjava.util.RealVector;
import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;

import java.util.Collection;

public interface IDifferentialEvolution<T extends RealVector> extends IMetaheuristic<T> {

    public T run(Collection<Solution<T>> initialPopulation);

    public void setInitialPopulation(Collection<Solution<T>> initialPopulation);
}
