package com.dosilovic.hermanzvonimir.ecfjava.models.problems;

import java.util.Collection;

public interface IProblem<T> {

    public double fitness(T individual);

    public double penalty(T individual);

    public double[] fitness(Collection<T> population);

    public double[] penalty(Collection<T> population);
}
