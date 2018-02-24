package com.dosilovic.hermanzvonimir.ecfjava.models.problems;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;
import org.apache.commons.math3.analysis.UnivariateFunction;

public class SimpleMultiObjectiveProblem<T extends ISolution> extends SingleObjectiveProblem<T> {

    private IProblem<T>[]        problems;
    private UnivariateFunction[] functions;

    public SimpleMultiObjectiveProblem(UnivariateFunction[] functions, IProblem<T>... problems) {
        if (functions.length != problems.length) {
            throw new IllegalArgumentException("Number of must be equal to number of problems");
        }

        this.problems = problems;
        this.functions = functions;
    }

    public SimpleMultiObjectiveProblem(IProblem<T>... problems) {
        UnivariateFunction[] functions = new UnivariateFunction[problems.length];
        UnivariateFunction   identity  = (x) -> x;
        for (int i = 0; i < functions.length; i++) {
            functions[i] = identity;
        }

        this.problems = problems;
        this.functions = functions;
    }

    @Override
    public double fitness(T individual) {
        double fitness = 0;

        int offset = 0;
        for (IProblem<T> problem : problems) {
            fitness += functions[offset++].value(problem.fitness(individual));
        }

        return fitness;
    }

    @Override
    public double penalty(T individual) {
        double penalty = 0;

        int offset = 0;
        for (IProblem<T> problem : problems) {
            penalty += functions[offset++].value(problem.penalty(individual));
        }

        return penalty;
    }
}
