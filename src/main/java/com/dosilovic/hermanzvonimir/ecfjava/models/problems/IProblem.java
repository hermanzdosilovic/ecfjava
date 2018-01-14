package com.dosilovic.hermanzvonimir.ecfjava.models.problems;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

import java.util.Collection;

public interface IProblem<T> {

    double fitness(ISolution<T> individual);

    double penalty(ISolution<T> individual);

    double[] fitness(Collection<? extends ISolution<T>> population);

    double[] penalty(Collection<? extends ISolution<T>> population);
}
