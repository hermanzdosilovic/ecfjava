package com.dosilovic.hermanzvonimir.ecfjava.examples.pso;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.HPSOTVAC;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.IParticleSwarmOptimization;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.topologies.FullyConnectedTopology;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.topologies.ITopology;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.ICoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.LinearCoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.FunctionMinimizationProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.factories.IFactory;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.factories.ParticleFactory;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.factories.RandomParticleFactory;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.particle.Particle;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.BoundedRealVector;
import com.dosilovic.hermanzvonimir.ecfjava.numeric.SchwefelFunction;
import com.dosilovic.hermanzvonimir.ecfjava.numeric.adapters.ParticleFunctionAdapter;

public final class SchwefelHPSOTVAC {

    public static void main(String[] args) {
        final int     NUMBER_OF_COMPONENTS      = 30;
        final double  MIN_COMPONENT_VALUE       = -512;
        final double  MAX_COMPONENT_VALUE       = 512;
        final int     NUMBER_OF_PARTICLES       = 60;
        final int     MAX_ITERATIONS            = 100_000;
        final double  DESIRED_FITNESS           = 0;
        final double  DESIRED_PRECISION         = 1e-3;
        final boolean IS_FULLY_INFORMED         = false;
        final double  INITIAL_INDIVIDUAL_FACTOR = 2.5;
        final double  FINAL_INDIVIDUAL_FACTOR   = 0.5;
        final double  INITIAL_SOCIAL_FACTOR     = 0.5;
        final double  FINAL_SOCIAL_FACTOR       = 2.5;
        final double  INITIAL_MIN_SPEED         = -512;
        final double  FINAL_MIN_SPEED           = -51.2;
        final double  INITIAL_MAX_SPEED         = 512;
        final double  FINAL_MAX_SPEED           = 51.2;

        IProblem<Particle> problem = new FunctionMinimizationProblem<>(
            new ParticleFunctionAdapter<>(new SchwefelFunction<>())
        );

        ICoolingSchedule individualFactorSchedule = new LinearCoolingSchedule(MAX_ITERATIONS, INITIAL_INDIVIDUAL_FACTOR, FINAL_INDIVIDUAL_FACTOR);
        ICoolingSchedule socialFactorSchedule = new LinearCoolingSchedule(MAX_ITERATIONS, INITIAL_SOCIAL_FACTOR, FINAL_SOCIAL_FACTOR);

        ICoolingSchedule minSpeedSchedule = new LinearCoolingSchedule(MAX_ITERATIONS, INITIAL_MIN_SPEED, FINAL_MIN_SPEED);
        ICoolingSchedule maxSpeedSchedule = new LinearCoolingSchedule(MAX_ITERATIONS, INITIAL_MAX_SPEED, FINAL_MAX_SPEED);

        ITopology<Particle> topology = new FullyConnectedTopology<>();

        IParticleSwarmOptimization<Particle> particleSwarmOptimization = new HPSOTVAC<>(
            MAX_ITERATIONS,
            DESIRED_FITNESS,
            DESIRED_PRECISION,
            IS_FULLY_INFORMED,
            individualFactorSchedule,
            socialFactorSchedule,
            minSpeedSchedule,
            maxSpeedSchedule,
            problem,
            topology
        );

        IFactory<Particle> particleFactory = new RandomParticleFactory(
            new ParticleFactory(
                new Particle(
                    new BoundedRealVector(NUMBER_OF_COMPONENTS, MIN_COMPONENT_VALUE, MAX_COMPONENT_VALUE),
                    new BoundedRealVector(NUMBER_OF_COMPONENTS, INITIAL_MIN_SPEED, INITIAL_MAX_SPEED)
                )
            )
        );

        particleSwarmOptimization.run(particleFactory.createMultipleInstances(NUMBER_OF_PARTICLES));
    }
}
