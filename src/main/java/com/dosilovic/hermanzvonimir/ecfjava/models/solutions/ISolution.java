package com.dosilovic.hermanzvonimir.ecfjava.models.solutions;

public interface ISolution {

    double getFitness();

    void setFitness(double fitness);

    double getPenalty();

    void setPenalty(double penalty);

    ISolution copy();
}
