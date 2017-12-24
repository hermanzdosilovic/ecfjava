package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.topologies;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.Particle;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class RingTopology<T> implements ITopology<T> {

    private Map<Particle<T>, Collection<Particle<T>>> neighboursMap;

    public RingTopology() {}

    public RingTopology(Collection<Particle<T>> particles) {
        createNeighborhood(particles);
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

        Particle<T> firstParticle    = null;
        Particle<T> previousParticle = null;

        for (Particle<T> particle : particles) {
            if (previousParticle == null) {
                previousParticle = firstParticle = particle;
                continue;
            }

            connectNeighbours(particle, previousParticle);
            connectNeighbours(particle, particle);
            previousParticle = particle;
        }

        connectNeighbours(firstParticle, previousParticle);
    }

    private void connectNeighbours(Particle<T> first, Particle<T> second) {
        Collection<Particle<T>> neighbours = neighboursMap.computeIfAbsent(first, k -> new HashSet<>());
        neighbours.add(second);

        neighbours = neighboursMap.computeIfAbsent(second, k -> new HashSet<>());
        neighbours.add(first);
    }
}
