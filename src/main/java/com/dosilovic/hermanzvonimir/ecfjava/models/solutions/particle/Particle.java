package com.dosilovic.hermanzvonimir.ecfjava.models.solutions.particle;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.AbstractSolution;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.Solutions;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.RealVector;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Particle<T> extends AbstractSolution<T> {

    private ISolution<T> solution;
    private ISolution<T> bestSolution;
    private RealVector   speed;

    private int id;

    private static final AtomicInteger particlesCounter = new AtomicInteger(0);

    public Particle(ISolution<T> initialSolution, RealVector initialSpeed) {
        this.solution = initialSolution;
        this.speed = initialSpeed;
        this.id = particlesCounter.getAndIncrement();
    }

    public Particle(Particle<T> particle) {
        this.solution = particle.solution.copy();
        this.bestSolution = particle.bestSolution.copy();
        this.speed = particle.speed.copy();
        this.id = particlesCounter.getAndIncrement();
    }

    @Override
    public T getRepresentative() {
        return solution.getRepresentative();
    }

    @Override
    public void setRepresentative(T representative) {
        solution.setRepresentative(representative);
    }

    @Override
    public double getFitness() {
        return solution.getFitness();
    }

    @Override
    public void setFitness(double fitness) {
        solution.setFitness(fitness);
    }

    @Override
    public double getPenalty() {
        return solution.getPenalty();
    }

    @Override
    public void setPenalty(double penalty) {
        solution.setPenalty(penalty);
    }

    public ISolution<T> getSolution() {
        return solution;
    }

    public void setSolution(ISolution<T> currentSolution) {
        this.solution = currentSolution;
    }

    public ISolution<T> getBestSolution() {
        return bestSolution;
    }

    public void setBestSolution(ISolution<T> bestSolution) {
        this.bestSolution = bestSolution;
    }

    public RealVector getSpeed() {
        return speed;
    }

    public void setSpeed(RealVector speed) {
        this.speed = speed;
    }

    public void updateBestSolutionByFitness() {
        setBestSolution(Solutions.betterByFitness(bestSolution, solution));
    }

    public void updateBestSolutionByPenalty() {
        setBestSolution(Solutions.betterByPenalty(bestSolution, solution));
    }

    @Override
    public Particle copy() {
        return new Particle(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Particle<?> particle = (Particle<?>) o;
        return id == particle.id;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Speed: ").append(speed).append("\n");
        builder.append("Current Solution:\n").append(solution).append("\n");
        builder.append("Best Solution:\n").append(bestSolution);
        return builder.toString();
    }
}
