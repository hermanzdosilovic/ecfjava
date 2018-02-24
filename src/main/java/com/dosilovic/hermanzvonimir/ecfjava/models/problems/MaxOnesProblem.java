package com.dosilovic.hermanzvonimir.ecfjava.models.problems;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.BitVector;

public class MaxOnesProblem<T extends BitVector> extends SingleObjectiveProblem<T> {

    @Override
    public double fitness(T individual) {
        int size = individual.getSize();
        int k    = individual.getCardinality();

        if (k <= 0.8 * size) {
            return (double) k / size;
        } else if (k <= 0.9 * size) {
            return 0.8;
        }

        return (2.0 * k / size) - 1;
    }

    @Override
    public double penalty(T individual) {
        return -1.0 * fitness(individual);
    }
}
