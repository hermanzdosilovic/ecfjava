package com.dosilovic.hermanzvonimir.ecfjava.models.solutions;

import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;

public interface ISolution<T> extends Comparable<ISolution<T>> {

    T getRepresentative();

    void setRepresentative(T representative);

    double getFitness();

    void setFitness(double fitness);

    double getPenalty();

    void setPenalty(double penalty);

    double updateFitness(IProblem<T> problem);

    double updatePenalty(IProblem<T> problem);

    ISolution<T> copy();
}
