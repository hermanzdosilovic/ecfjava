package com.dosilovic.hermanzvonimir.ecfjava.models.solutions.particle;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.RealVector;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Particle implements ISolution {

    private RealVector solution;
    private RealVector bestSolution;
    private RealVector speed;

    private int id;

    private static final AtomicInteger particlesCounter = new AtomicInteger(0);

    public Particle(RealVector solution, RealVector speed) {
        this.solution = solution;
        this.bestSolution = null;
        this.speed = speed;
        this.id = particlesCounter.getAndIncrement();
    }

    public Particle(Particle particle) {
        this.solution = particle.solution.copy();
        if (particle.bestSolution != null) {
            this.bestSolution = particle.bestSolution.copy();
        }
        this.speed = particle.speed.copy();
        this.id = particlesCounter.getAndIncrement();
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

    @Override
    public Particle copy() {
        return new Particle(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Particle particle = (Particle) o;
        return id == particle.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public RealVector getSolution() {
        return solution;
    }

    public void setSolution(RealVector solution) {
        this.solution = solution;
    }

    public RealVector getBestSolution() {
        return bestSolution;
    }

    public void setBestSolution(RealVector bestSolution) {
        this.bestSolution = bestSolution;
    }

    public RealVector getSpeed() {
        return speed;
    }

    public void setSpeed(RealVector speed) {
        this.speed = speed;
    }
}
