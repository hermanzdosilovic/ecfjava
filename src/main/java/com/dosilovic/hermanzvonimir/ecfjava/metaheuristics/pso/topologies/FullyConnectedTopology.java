package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.topologies;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.Particle;

import java.util.Collection;

public class FullyConnectedTopology implements ITopology {

    private Collection<Particle> particles;

    public FullyConnectedTopology(Collection<Particle> particles) {
        this.particles = particles;
    }

    @Override
    public Collection<Particle> getNeighbours(Particle particle) {
        return particles;
    }

    @Override
    public void updateTopology(Collection<Particle> particles) {
        this.particles = particles;
    }
}
