package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.topologies;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.particle.Particle;

import java.util.*;

public class RandomTopology<T> implements ITopology<T> {

    private Map<Particle<T>, Collection<Particle<T>>> neighboursMap;
    private double                                    neighborhoodProbability;
    private boolean                                   forceNeighborhood;

    private static final Random RAND = new Random();

    public RandomTopology(double neighborhoodProbability, boolean forceNeighborhood) {
        this(null, neighborhoodProbability, forceNeighborhood);
    }

    public RandomTopology(
        Collection<Particle<T>> particles,
        double neighborhoodProbability,
        boolean forceNeighborhood
    ) {
        this.neighborhoodProbability = neighborhoodProbability;
        this.forceNeighborhood = forceNeighborhood;

        if (particles != null) {
            createNeighborhood(particles);
        }
    }

    @Override
    public Collection<Particle<T>> getNeighbours(Particle<T> particle) {
        return neighboursMap.get(particle);
    }

    @Override
    public void updateTopology(Collection<Particle<T>> particles) {
        createNeighborhood(particles);
    }

    private void createNeighborhood(Collection<Particle<T>> particles) {
        if (neighboursMap == null) {
            neighboursMap = new HashMap<>();
        } else {
            neighboursMap.clear();
        }

        for (Particle<T> particle : particles) {
            int index = RAND.nextInt(particles.size());
            int i     = 0;

            for (Particle<T> neighbour : particles) {
                if (
                    (forceNeighborhood && i == index) ||
                    RAND.nextDouble() <= neighborhoodProbability ||
                    particle.equals(neighbour)
                    ) {
                    connectNeighbours(particle, neighbour);
                }
                i++;
            }
        }
    }

    private void connectNeighbours(Particle<T> first, Particle<T> second) {
        Collection<Particle<T>> neighbours = neighboursMap.computeIfAbsent(first, k -> new HashSet<>());
        neighbours.add(second);

        neighbours = neighboursMap.computeIfAbsent(second, k -> new HashSet<>());
        neighbours.add(first);
    }
}
