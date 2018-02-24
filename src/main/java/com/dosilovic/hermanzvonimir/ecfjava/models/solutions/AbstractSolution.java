package com.dosilovic.hermanzvonimir.ecfjava.models.solutions;

public abstract class AbstractSolution implements ISolution {

    private double fitness;
    private double penalty;

    public AbstractSolution(double fitness, double penalty) {
        this.fitness = fitness;
        this.penalty = penalty;
    }

    public AbstractSolution() {
        this(-Double.MAX_VALUE, Double.MAX_VALUE);
    }

    @Override
    public double getFitness() {
        return fitness;
    }

    @Override
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    @Override
    public double getPenalty() {
        return penalty;
    }

    @Override
    public void setPenalty(double penalty) {
        this.penalty = penalty;
    }
}
