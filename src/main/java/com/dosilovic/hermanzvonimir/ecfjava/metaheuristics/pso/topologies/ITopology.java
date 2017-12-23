package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.topologies;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.Particle;

import java.util.Collection;

public interface ITopology {

    public Collection<Particle> getNeighbours(Particle particle);

    public void updateTopology(Collection<Particle> particles);
}
