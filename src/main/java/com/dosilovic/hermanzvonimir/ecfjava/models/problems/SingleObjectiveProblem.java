package com.dosilovic.hermanzvonimir.ecfjava.models.problems;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

import java.util.Collection;

public abstract class SingleObjectiveProblem<T extends ISolution> implements IProblem<T> {

    @Override
    public void updateFitness(T individual) {
        individual.setFitness(fitness(individual));
    }

    @Override
    public void updatePenalty(T individual) {
        individual.setPenalty(penalty(individual));
    }

    @Override
    public void updateFitness(Collection<T> population) {
        for (T individual : population) {
            individual.setFitness(fitness(individual));
        }
    }

    @Override
    public void updatePenalty(Collection<T> population) {
        for (T individual : population) {
            individual.setPenalty(penalty(individual));
        }
    }
}
