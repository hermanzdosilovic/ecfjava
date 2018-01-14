package com.dosilovic.hermanzvonimir.ecfjava.models.problems;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

import java.util.Collection;

public abstract class SingleObjectiveProblem<T> implements IProblem<T> {

    private double[] fitnessValues;
    private double[] penaltyValues;

    @Override
    public double[] fitness(Collection<? extends ISolution<T>> population) {
        if (fitnessValues == null || fitnessValues.length != population.size()) {
            fitnessValues = new double[population.size()];
        }

        int i = 0;
        for (ISolution<T> individual : population) {
            fitnessValues[i++] = fitness(individual);
        }

        return fitnessValues;
    }

    @Override
    public double[] penalty(Collection<? extends ISolution<T>> population) {
        if (penaltyValues == null || penaltyValues.length != population.size()) {
            penaltyValues = new double[population.size()];
        }

        int i = 0;
        for (ISolution<T> individual : population) {
            penaltyValues[i++] = penalty(individual);
        }

        return penaltyValues;
    }
}
