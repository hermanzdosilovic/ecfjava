package com.dosilovic.hermanzvonimir.ecfjava.models.solutions;

public class SimpleSolution<T> extends AbstractSolution<T> {

    private T      representative;
    private double fitness;
    private double penalty;

    public SimpleSolution(T representative) {
        this.representative = representative;
        fitness = -Double.MAX_VALUE;
        penalty = Double.MAX_VALUE;
    }

    public SimpleSolution(SimpleSolution<T> solution) {
        this.representative = solution.representative;
        this.fitness = solution.fitness;
        this.penalty = solution.penalty;
    }

    @Override
    public T getRepresentative() {
        return representative;
    }

    @Override
    public void setRepresentative(T representative) {
        this.representative = representative;
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

    @Override
    public SimpleSolution<T> copy() {
        return new SimpleSolution<>(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SimpleSolution<?> solution = (SimpleSolution<?>) o;

        return representative != null ?
               representative.equals(solution.representative) :
               solution.representative == null;
    }

    @Override
    public int hashCode() {
        return representative != null ? representative.hashCode() : 0;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Fitness: ").append(fitness).append("\n");
        builder.append("Penalty: ").append(penalty).append("\n");
        builder.append("Solution:\n").append(representative).append("\n");
        return builder.toString();
    }
}
