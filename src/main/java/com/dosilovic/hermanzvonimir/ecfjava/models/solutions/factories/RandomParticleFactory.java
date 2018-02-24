package com.dosilovic.hermanzvonimir.ecfjava.models.solutions.factories;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.particle.Particle;

public class RandomParticleFactory extends AbstractFactory<Particle> {

    ParticleFactory particleFactory;

    public RandomParticleFactory(ParticleFactory particleFactory) {
        this.particleFactory = particleFactory;
    }

    @Override
    public Particle createInstance() {
        Particle particle = particleFactory.createInstance();

        particle.getSolution().randomizeValues();
        particle.getSpeed().randomizeValues();

        return particle;
    }
}
