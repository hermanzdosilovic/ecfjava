package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

public abstract class AbstractIndividualMetaheuristic<T extends ISolution> extends AbstractMetaheuristic<T> implements IIndividualMetaheuristic<T> {

    protected T initialSolution;
    protected T solution;

    @Override
    public T getInitialSolution() {
        return initialSolution;
    }

    @Override
    public void setInitialSolution(T initialSolution) {
        if (initialSolution == null) {
            throw new IllegalArgumentException("Initial solution cannot be null");
        }
        this.initialSolution = initialSolution;
    }

    @Override
    public T getSolution() {
        return solution;
    }

    @Override
    public void setSolution(T solution) {
        if (solution == null) {
            throw new IllegalArgumentException("Solution cannot be null");
        }
        this.solution = solution;
    }

    @Override
    public T run(T initialSolution) {
        setInitialSolution(initialSolution);
        return run();
    }
}
