package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling;

import java.util.Iterator;

public class LogCoolingSchedule extends AbstractCoolingSchedule {

    private int numberOfIterations;

    public LogCoolingSchedule(int numberOfIterations, double initialTemperature) {
        super(initialTemperature);
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
        if (iteration == 0 || iteration == 1) {
            return initialTemperature;
        }
        return initialTemperature / Math.log(Math.max(2, iteration));
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
}
