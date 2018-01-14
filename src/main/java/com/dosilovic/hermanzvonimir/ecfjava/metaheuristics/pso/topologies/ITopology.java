package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.topologies;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.particle.Particle;

import java.util.Collection;

public interface ITopology<T> {

    public Collection<Particle<T>> getNeighbours(Particle<T> particle);

    public void updateTopology(Collection<Particle<T>> particles);
}
