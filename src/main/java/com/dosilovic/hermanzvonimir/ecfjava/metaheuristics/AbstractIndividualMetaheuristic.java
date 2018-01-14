package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

public abstract class AbstractIndividualMetaheuristic<T> extends AbstractMetaheuristic<T> implements IIndividualMetaheuristic<T> {

    protected ISolution<T> initialSolution;
    protected ISolution<T> solution;

    @Override
    public ISolution<T> getInitialSolution() {
        return initialSolution;
    }

    @Override
    public void setInitialSolution(ISolution<T> initialSolution) {
        if (initialSolution == null) {
            throw new IllegalArgumentException("Initial solution cannot be null");
        }
        this.initialSolution = initialSolution;
    }

    @Override
    public ISolution<T> getSolution() {
        return solution;
    }

    @Override
    public void setSolution(ISolution<T> solution) {
        if (solution == null) {
            throw new IllegalArgumentException("Solution cannot be null");
        }
        this.solution = solution;
    }

    @Override
    public ISolution<T> run(ISolution<T> initialSolution) {
        setInitialSolution(initialSolution);
        return run();
    }
}
