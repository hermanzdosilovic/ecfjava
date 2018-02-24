package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.topologies;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

import java.util.Collection;

public interface ITopology<T extends ISolution> {

    Collection<T> getNeighbours(T individual);

    void updateTopology(Collection<T> population);
}
