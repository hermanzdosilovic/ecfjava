package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.topologies;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class RingTopology<T extends ISolution> implements ITopology<T> {

    private Map<T, Collection<T>> neighboursMap;

    public RingTopology() {}

    public RingTopology(Collection<T> population) {
        createNeighborhood(population);
    }

    @Override
    public Collection<T> getNeighbours(T individual) {
        return neighboursMap.get(individual);
    }

    @Override
    public void updateTopology(Collection<T> population) {
        createNeighborhood(population);
    }

    private void createNeighborhood(Collection<T> population) {
        if (neighboursMap == null) {
            neighboursMap = new HashMap<>();
        } else {
            neighboursMap.clear();
        }

        T firstIndividual    = null;
        T previousIndividual = null;

        for (T individual : population) {
            if (previousIndividual == null) {
                previousIndividual = firstIndividual = individual;
                continue;
            }

            connectNeighbours(individual, previousIndividual);
            connectNeighbours(individual, individual);
            previousIndividual = individual;
        }

        connectNeighbours(firstIndividual, previousIndividual);
    }

    private void connectNeighbours(T first, T second) {
        Collection<T> neighbours = neighboursMap.computeIfAbsent(first, k -> new HashSet<>());
        neighbours.add(second);

        neighbours = neighboursMap.computeIfAbsent(second, k -> new HashSet<>());
        neighbours.add(first);
    }
}
