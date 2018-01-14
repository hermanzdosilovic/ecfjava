package com.dosilovic.hermanzvonimir.ecfjava.models.solutions.factories;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.particle.Particle;

public class ParticleFactory<T> extends AbstractSolutionFactory<T> {

    private ISolutionFactory<T> initialSolutionFactory;
    private RealVectorFactory   initialSpeedFactory;

    public ParticleFactory(
        ISolutionFactory<T> initialSolutionFactory,
        RealVectorFactory initialSpeedFactory
    ) {
        this.initialSolutionFactory = initialSolutionFactory;
        this.initialSpeedFactory = initialSpeedFactory;
    }

    @Override
    public Particle<T> createInstance() {
        return new Particle<>(initialSolutionFactory.createInstance(), initialSpeedFactory.createInstance());
    }
}
