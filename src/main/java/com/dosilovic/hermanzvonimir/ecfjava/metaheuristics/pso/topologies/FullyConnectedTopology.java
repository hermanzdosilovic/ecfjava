package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.topologies;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

import java.util.Collection;

public class FullyConnectedTopology<T extends ISolution> implements ITopology<T> {

    private Collection<T> population;

    public FullyConnectedTopology() {}

    public FullyConnectedTopology(Collection<T> population) {
        this.population = population;
    }

    @Override
    public Collection<T> getNeighbours(T individual) {
        return population;
    }

    @Override
    public void updateTopology(Collection<T> population) {
        this.population = population;
    }
}
