package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling;

import java.util.Iterator;

public class ConstantCoolingSchedule extends AbstractCoolingSchedule {

    private int numberOfIterations;

    public ConstantCoolingSchedule(int numberOfIterations, double constant) {
        super(constant);
        this.numberOfIterations = numberOfIterations;
    }

    public int getNumberOfIterations() {
        return numberOfIterations;
    }

    public void setNumberOfIterations(int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }

    @Override
    public double getTemperature(int iteration) {
        return initialTemperature;
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
                currentIteration++;
                return initialTemperature;
            }
        };
    }
}
