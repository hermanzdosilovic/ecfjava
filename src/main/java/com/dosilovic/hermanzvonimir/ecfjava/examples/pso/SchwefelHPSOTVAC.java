package com.dosilovic.hermanzvonimir.ecfjava.examples.pso;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.HPSOTVAC;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.IParticleSwarmOptimization;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.Particle;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.topologies.FullyConnectedTopology;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.LinearCoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.FunctionMinimizationProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.numeric.SchwefelFunction;
import com.dosilovic.hermanzvonimir.ecfjava.util.RealVector;
import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;

import java.util.ArrayList;
import java.util.Collection;

public final class SchwefelHPSOTVAC {

    public static void main(String[] args) {
        final int     NUMBER_OF_COMPONENTS      = 10;
        final int     NUMBER_OF_PARTICLES       = 60;
        final int     MAX_ITERATIONS            = 1000000;
        final double  DESIRED_FITNESS           = -0.0001;
        final double  DESIRED_PRECISION         = 1e-4;
        final boolean IS_FULLY_INFORMED         = false;
        final double  INITIAL_INDIVIDUAL_FACTOR = 2.5;
        final double  FINAL_INDIVIDUAL_FACTOR   = 0.5;
        final double  INITIAL_SOCIAL_FACTOR     = 0.5;
        final double  FINAL_SOCIAL_FACTOR       = 2.5;
        final double  MIN_VALUE                 = -512;
        final double  MAX_VALUE                 = 512;
        final double  INITIAL_MIN_SPEED         = -51.2;
        final double  FINAL_MIN_SPEED           = -5.12;
        final double  INITIAL_MAX_SPEED         = 51.2;
        final double  FINAL_MAX_SPEED           = 5.12;

        IProblem<RealVector> problem = new FunctionMinimizationProblem<>(new SchwefelFunction<>());

        Collection<Particle<RealVector>> initialParticles = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_PARTICLES; i++) {
            initialParticles.add(new Particle<>(
                new Solution<>(new RealVector(NUMBER_OF_COMPONENTS, MIN_VALUE, MAX_VALUE)),
                new RealVector(NUMBER_OF_COMPONENTS, INITIAL_MIN_SPEED, INITIAL_MAX_SPEED)
            ));
        }

        IParticleSwarmOptimization<RealVector> particleSwarmOptimization = new HPSOTVAC<>(
            MAX_ITERATIONS,
            DESIRED_FITNESS,
            DESIRED_PRECISION,
            IS_FULLY_INFORMED,
            new LinearCoolingSchedule(MAX_ITERATIONS, INITIAL_INDIVIDUAL_FACTOR, FINAL_INDIVIDUAL_FACTOR),
            new LinearCoolingSchedule(MAX_ITERATIONS, INITIAL_SOCIAL_FACTOR, FINAL_SOCIAL_FACTOR),
            new RealVector(NUMBER_OF_COMPONENTS, MIN_VALUE),
            new RealVector(NUMBER_OF_COMPONENTS, MAX_VALUE),
            new LinearCoolingSchedule(MAX_ITERATIONS, INITIAL_MIN_SPEED, FINAL_MIN_SPEED),
            new LinearCoolingSchedule(MAX_ITERATIONS, INITIAL_MAX_SPEED, FINAL_MAX_SPEED),
            problem,
            new FullyConnectedTopology<>()
        );

        particleSwarmOptimization.run(initialParticles);
    }
}
