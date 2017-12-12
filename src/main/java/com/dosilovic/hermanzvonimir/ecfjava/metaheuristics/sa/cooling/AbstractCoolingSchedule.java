package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling;

public abstract class AbstractCoolingSchedule implements ICoolingSchedule {

    protected double initialTemperature;

    public AbstractCoolingSchedule(double initialTemperature) {
        this.initialTemperature = initialTemperature;
    }

    @Override
    public double getInitialTemperature() {
        return initialTemperature;
    }

    @Override
    public void setInitialTemperature(double initialTemperature) {
        this.initialTemperature = initialTemperature;
    }
}
