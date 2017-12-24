package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.topologies;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.Particle;

import java.util.Collection;

public class FullyConnectedTopology<T> implements ITopology<T> {

    private Collection<Particle<T>> particles;

    public FullyConnectedTopology() {}

    public FullyConnectedTopology(Collection<Particle<T>> particles) {
        this.particles = particles;
    }

    @Override
    public Collection<Particle<T>> getNeighbours(Particle<T> particle) {
        return particles;
    }

    @Override
    public void updateTopology(Collection<Particle<T>> particles) {
        this.particles = particles;
    }
}
