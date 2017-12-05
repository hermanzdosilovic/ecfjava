package com.dosilovic.hermanzvonimir.ecfjava.models.problems;

public interface IProblem<T> {

    public double fitness(T individual);

    public double penalty(T individual);
}
