package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.topologies;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.IRandom;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.Random;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class RandomTopology<T extends ISolution> implements ITopology<T> {

    private Map<T, Collection<T>> neighboursMap;
    private double                neighborhoodProbability;

    public RandomTopology(double neighborhoodProbability) {
        this(null, neighborhoodProbability);
    }

    public RandomTopology(
        Collection<T> population,
        double neighborhoodProbability
    ) {
        this.neighborhoodProbability = neighborhoodProbability;

        if (population != null) {
            createNeighborhood(population);
        }
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
        IRandom random = Random.getRandom();

        if (neighboursMap == null) {
            neighboursMap = new HashMap<>();
        } else {
            neighboursMap.clear();
        }

        for (T individual : population) {
            for (T neighbour : population) {
                if (
                    random.nextDouble() < neighborhoodProbability ||
                    individual == neighbour
                    ) {
                    connectNeighbours(individual, neighbour);
                }
            }
        }
    }

    private void connectNeighbours(T first, T second) {
        Collection<T> neighbours = neighboursMap.computeIfAbsent(first, k -> new HashSet<>());
        neighbours.add(second);

        neighbours = neighboursMap.computeIfAbsent(second, k -> new HashSet<>());
        neighbours.add(first);
    }
}
