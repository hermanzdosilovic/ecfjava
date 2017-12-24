package com.dosilovic.hermanzvonimir.ecfjava.examples.pso;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.IParticleSwarmOptimization;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.Particle;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.StohasticIWPSO;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.TimeVaryingIWPSO;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.topologies.FullyConnectedTopology;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.LinearCoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.FunctionMinimizationProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.numeric.AckleyFunction;
import com.dosilovic.hermanzvonimir.ecfjava.numeric.SalomonFunction;
import com.dosilovic.hermanzvonimir.ecfjava.util.RealVector;
import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;

import java.util.ArrayList;
import java.util.Collection;

public final class SalomonStohasticIWPSO {

    public static void main(String[] args) {
        final int     NUMBER_OF_COMPONENTS = 100;
        final int     NUMBER_OF_PARTICLES  = 60;
        final int     MAX_ITERATIONS       = 1000000;
        final double  DESIRED_FITNESS      = -0.0001;
        final double  DESIRED_PRECISION    = 1e-4;
        final boolean IS_FULLY_INFORMED    = false;
        final double  INDIVIDUAL_FACTOR    = 1.494;
        final double  SOCIAL_FACTOR        = 1.494;
        final double  MIN_VALUE            = -100;
        final double  MAX_VALUE            = 100;
        final double  MIN_SPEED            = -5;
        final double  MAX_SPEED            = 5;
        final double  LOWER_BOUND          = 0.5;
        final double  UPPER_BOUND          = 1.0;

        IProblem<RealVector> problem = new FunctionMinimizationProblem<>(new SalomonFunction<>());

        Collection<Particle<RealVector>> initialParticles = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_PARTICLES; i++) {
            initialParticles.add(new Particle<>(
                new Solution<>(new RealVector(NUMBER_OF_COMPONENTS, MIN_VALUE, MAX_VALUE)),
                new RealVector(NUMBER_OF_COMPONENTS, MIN_SPEED, MAX_SPEED)
            ));
        }

        IParticleSwarmOptimization<RealVector> particleSwarmOptimization = new StohasticIWPSO<>(
            MAX_ITERATIONS,
            DESIRED_FITNESS,
            DESIRED_PRECISION,
            IS_FULLY_INFORMED,
            INDIVIDUAL_FACTOR,
            SOCIAL_FACTOR,
            new RealVector(NUMBER_OF_COMPONENTS, MIN_VALUE),
            new RealVector(NUMBER_OF_COMPONENTS, MAX_VALUE),
            new RealVector(NUMBER_OF_COMPONENTS, MIN_SPEED),
            new RealVector(NUMBER_OF_COMPONENTS, MAX_SPEED),
            new RealVector(NUMBER_OF_COMPONENTS, LOWER_BOUND),
            new RealVector(NUMBER_OF_COMPONENTS, UPPER_BOUND),
            problem,
            new FullyConnectedTopology<>()
        );

        particleSwarmOptimization.run(initialParticles);
    }
}
