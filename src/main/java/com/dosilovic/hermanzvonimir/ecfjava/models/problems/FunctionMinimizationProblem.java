package com.dosilovic.hermanzvonimir.ecfjava.models.problems;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;
import com.dosilovic.hermanzvonimir.ecfjava.numeric.IFunction;

public class FunctionMinimizationProblem<T> extends SingleObjectiveProblem<T> {

    private IFunction<T> function;

    public FunctionMinimizationProblem(IFunction<T> function) {
        this.function = function;
    }

    @Override
    public double fitness(ISolution<T> individual) {
        return -1.0 * penalty(individual);
    }

    @Override
    public double penalty(ISolution<T> individual) {
        return function.getValue(individual.getRepresentative());
    }
}
