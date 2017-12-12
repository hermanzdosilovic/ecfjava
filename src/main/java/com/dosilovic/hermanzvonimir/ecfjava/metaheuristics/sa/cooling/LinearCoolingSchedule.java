package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling;

import java.util.Iterator;

public class LinearCoolingSchedule extends AbstractCoolingSchedule {

    private double beta;
    private int    numberOfIterations;
    private double finalTemperature;

    public LinearCoolingSchedule(int numberOfIterations, double initialTemperature, double finalTemperature) {
        super(initialTemperature);
        this.numberOfIterations = numberOfIterations;
        this.finalTemperature = finalTemperature;
        calculateBeta();
    }

    @Override
    public void setInitialTemperature(double initialTemperature) {
        super.setInitialTemperature(initialTemperature);
        calculateBeta();
    }

    public double getFinalTemperature() {
        return finalTemperature;
    }

    public void setFinalTemperature(double finalTemperature) {
        this.finalTemperature = finalTemperature;
        calculateBeta();
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public int getNumberOfIterations() {
        return numberOfIterations;
    }

    public void setNumberOfIterations(int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
        calculateBeta();
    }

    @Override
    public double getTemperature(int iteration) {
        return initialTemperature - iteration * beta;
    }

    @Override
    public Iterator<Double> iterator() {
        return new Iterator<>() {
            private int currentIteration;

            @Override
            public boolean hasNext() {
                return currentIteration < numberOfIterations;
            }

            @Override
            public Double next() {
                return getTemperature(currentIteration++);
            }
        };
    }

    private void calculateBeta() {
        beta = (initialTemperature - finalTemperature) / (numberOfIterations - 1);
    }
}
