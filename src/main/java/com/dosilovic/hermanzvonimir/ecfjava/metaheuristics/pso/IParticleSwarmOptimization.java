package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.IMetaheuristic;
import com.dosilovic.hermanzvonimir.ecfjava.util.RealVector;

import java.util.Collection;

public interface IParticleSwarmOptimization<T extends RealVector> extends IMetaheuristic<T> {

    public T run(Collection<Particle<T>> initialParticles);

    public void setInitialParticles(Collection<Particle<T>> initialParticles);
}
