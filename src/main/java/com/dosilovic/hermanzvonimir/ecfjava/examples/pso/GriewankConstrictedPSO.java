package com.dosilovic.hermanzvonimir.ecfjava.examples.pso;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.ConstrictedPSO;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.IParticleSwarmOptimization;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.Particle;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.topologies.RingTopology;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.ConstantCoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.FunctionMinimizationProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.numeric.GriewankFunction;
import com.dosilovic.hermanzvonimir.ecfjava.util.RealVector;
import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;

import java.util.ArrayList;
import java.util.Collection;

public final class GriewankConstrictedPSO {

    public static void main(String[] args) {
        final int     NUMBER_OF_COMPONENTS = 300;
        final int     NUMBER_OF_PARTICLES  = 60;
        final int     MAX_ITERATIONS       = 1000000;
        final double  DESIRED_FITNESS      = -0.0001;
        final double  DESIRED_PRECISION    = 1e-4;
        final boolean IS_FULLY_INFORMED    = true;
        final double  INDIVIDUAL_FACTOR    = 2.05;
        final double  SOCIAL_FACTOR        = 2.05;
        final double  CONSTRICTION_FACTOR  = 0.729;
        final double  MIN_VALUE            = -600;
        final double  MAX_VALUE            = 600;
        final double  MIN_SPEED            = -6;
        final double  MAX_SPEED            = 6;

        IProblem<RealVector> problem = new FunctionMinimizationProblem<>(new GriewankFunction<>());

        Collection<Particle<RealVector>> initialParticles = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_PARTICLES; i++) {
            initialParticles.add(new Particle<>(
                new Solution<>(new RealVector(NUMBER_OF_COMPONENTS, MIN_VALUE, MAX_VALUE)),
                new RealVector(NUMBER_OF_COMPONENTS, MIN_SPEED, MAX_SPEED)
            ));
        }

        IParticleSwarmOptimization<RealVector> particleSwarmOptimization = new ConstrictedPSO<>(
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
            new ConstantCoolingSchedule(MAX_ITERATIONS, CONSTRICTION_FACTOR),
            problem,
            new RingTopology<>()
        );

        particleSwarmOptimization.run(initialParticles);
    }
}
