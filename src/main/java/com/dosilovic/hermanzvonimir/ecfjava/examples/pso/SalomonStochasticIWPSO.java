package com.dosilovic.hermanzvonimir.ecfjava.examples.pso;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.IParticleSwarmOptimization;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.StochasticIWPSO;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.topologies.FullyConnectedTopology;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.topologies.ITopology;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.FunctionMinimizationProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.factories.ISolutionFactory;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.factories.ParticleFactory;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.factories.RealVectorFactory;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.factories.SimpleSolutionFactory;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.RealVector;
import com.dosilovic.hermanzvonimir.ecfjava.numeric.SalomonFunction;

public final class SalomonStochasticIWPSO {

    public static void main(String[] args) {
        final int     NUMBER_OF_COMPONENTS   = 10;
        final double  MIN_COMPONENT_VALUE    = -5.12;
        final double  MAX_COMPONENT_VALUE    = 5.12;
        final boolean IS_BOUNDED             = true;
        final int     NUMBER_OF_PARTICLES    = 60;
        final int     MAX_ITERATIONS         = 1000_000;
        final double  DESIRED_FITNESS        = 0;
        final double  DESIRED_PRECISION      = 1e-3;
        final boolean IS_FULLY_INFORMED      = true;
        final double  INDIVIDUAL_FACTOR      = 2.05;
        final double  SOCIAL_FACTOR          = 2.05;
        final double  MIN_COMPONENT_SPEED    = -5.12;
        final double  MAX_COMPONENT_SPEED    = 5.12;
        final double  LOWER_STOCHASTIC_BOUND = 0.5;
        final double  UPPER_STOCHASTIC_BOUND = 1.0;

        IProblem<RealVector>  problem              = new FunctionMinimizationProblem<>(new SalomonFunction<>());
        RealVector            minSpeedVector       = new RealVector(NUMBER_OF_COMPONENTS, MIN_COMPONENT_SPEED);
        RealVector            maxSpeedVector       = new RealVector(NUMBER_OF_COMPONENTS, MAX_COMPONENT_SPEED);
        RealVector            lowerStochasticBound = new RealVector(NUMBER_OF_COMPONENTS, LOWER_STOCHASTIC_BOUND);
        RealVector            upperStochasticBound = new RealVector(NUMBER_OF_COMPONENTS, UPPER_STOCHASTIC_BOUND);
        ITopology<RealVector> topology             = new FullyConnectedTopology<>();

        IParticleSwarmOptimization<RealVector> particleSwarmOptimization = new StochasticIWPSO<>(
            MAX_ITERATIONS,
            DESIRED_FITNESS,
            DESIRED_PRECISION,
            IS_FULLY_INFORMED,
            INDIVIDUAL_FACTOR,
            SOCIAL_FACTOR,
            minSpeedVector,
            maxSpeedVector,
            lowerStochasticBound,
            upperStochasticBound,
            problem,
            topology
        );

        ISolutionFactory<RealVector> particleFactory = new ParticleFactory<>(
            new SimpleSolutionFactory<>(
                new RealVectorFactory(
                    new RealVector(NUMBER_OF_COMPONENTS, MIN_COMPONENT_VALUE, MAX_COMPONENT_VALUE, IS_BOUNDED)
                )
            ),
            new RealVectorFactory(new RealVector(NUMBER_OF_COMPONENTS, MIN_COMPONENT_SPEED, MAX_COMPONENT_SPEED))
        );

        particleSwarmOptimization.run(particleFactory.createMultipleInstances(NUMBER_OF_PARTICLES));
    }
}