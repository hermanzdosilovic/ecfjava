package com.dosilovic.hermanzvonimir.ecfjava.models.problems;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

import java.util.Collection;

public abstract class SingleObjectiveProblem<T> implements IProblem<T> {

    @Override
    public void updateFitness(Collection<? extends ISolution<T>> population) {
        for (ISolution<T> individual : population) {
            individual.setFitness(fitness(individual));
        }
    }

    @Override
    public void updatePenalty(Collection<? extends ISolution<T>> population) {
        for (ISolution<T> individual : population) {
            individual.setPenalty(penalty(individual));
        }
    }
}
