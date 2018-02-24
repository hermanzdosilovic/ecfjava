package com.dosilovic.hermanzvonimir.ecfjava.models.problems;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

import java.util.Collection;

public interface IProblem<T extends ISolution> {

    double fitness(T individual);

    double penalty(T individual);

    void updateFitness(T population);

    void updatePenalty(T population);

    void updateFitness(Collection<T> population);

    void updatePenalty(Collection<T> population);
}
