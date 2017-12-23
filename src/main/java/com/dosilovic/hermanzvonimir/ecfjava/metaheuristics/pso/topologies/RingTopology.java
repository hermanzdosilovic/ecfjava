package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.topologies;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.Particle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RingTopology implements ITopology {

    private Map<Particle, Collection<Particle>> neighboursMap;

    public RingTopology(Collection<Particle> particles) {
        createNeighbourhood(particles);
    }

    @Override
    public Collection<Particle> getNeighbours(Particle particle) {
        return neighboursMap.get(particle);
    }

    @Override
    public void updateTopology(Collection<Particle> particles) {
        createNeighbourhood(particles);
    }

    private void createNeighbourhood(Collection<Particle> particles) {
        if (neighboursMap == null) {
            neighboursMap = new HashMap<>();
        } else {
            neighboursMap.clear();
        }

        Particle firstParticle    = null;
        Particle previousParticle = null;

        for (Particle particle : particles) {
            if (previousParticle == null) {
                previousParticle = firstParticle = particle;
                continue;
            }

            connectNeighbours(particle, previousParticle);
            previousParticle = particle;
        }

        connectNeighbours(firstParticle, previousParticle);
    }

    private void connectNeighbours(Particle first, Particle second) {
        Collection<Particle> neighbours = neighboursMap.computeIfAbsent(first, k -> new ArrayList<>());
        neighbours.add(second);

        neighbours = neighboursMap.computeIfAbsent(second, k -> new ArrayList<>());
        neighbours.add(first);
    }
}
