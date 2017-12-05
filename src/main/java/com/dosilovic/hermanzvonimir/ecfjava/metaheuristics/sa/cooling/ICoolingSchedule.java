package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling;

public interface ICoolingSchedule extends Iterable<Double> {

    public double getTemperature(int iteration);

    public double getInitialTemperature();

    public void setInitialTemperature(double initialTemperature);
}
