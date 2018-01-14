package com.dosilovic.hermanzvonimir.ecfjava.models.solutions;

import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;

public abstract class AbstractSolution<T> implements ISolution<T> {

    @Override
    public double updateFitness(IProblem<T> problem) {
        setFitness(problem.fitness(this));
        return getFitness();
    }

    @Override
    public double updatePenalty(IProblem<T> problem) {
        setPenalty(problem.penalty(this));
        return getPenalty();
    }

    @Override
    public int compareTo(ISolution<T> o) {
        if (this.getFitness() == o.getFitness()) {
            return 0;
        } else if (Solutions.betterByFitness(this, o) == this) {
            return 1;
        }
        return -1;
    }
}
