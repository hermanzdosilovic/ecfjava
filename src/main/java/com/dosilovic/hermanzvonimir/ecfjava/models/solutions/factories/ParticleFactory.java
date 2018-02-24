package com.dosilovic.hermanzvonimir.ecfjava.models.solutions.factories;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.particle.Particle;

public class ParticleFactory extends AbstractFactory<Particle> {

    private Particle template;

    public ParticleFactory(Particle template) {
        this.template = template;
    }

    @Override
    public Particle createInstance() {
        return template.copy();
    }
}
