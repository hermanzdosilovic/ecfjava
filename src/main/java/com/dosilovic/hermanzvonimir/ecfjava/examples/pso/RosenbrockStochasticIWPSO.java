package com.dosilovic.hermanzvonimir.ecfjava.examples.pso;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.IParticleSwarmOptimization;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.StochasticIWPSO;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.TimeVaryingIWPSO;
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
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.RealVector;
import com.dosilovic.hermanzvonimir.ecfjava.numeric.GriewankFunction;
import com.dosilovic.hermanzvonimir.ecfjava.numeric.adapters.ParticleFunctionAdapter;

public final class RosenbrockStochasticIWPSO {

    public static void main(String[] args) {
        final int     NUMBER_OF_COMPONENTS   = 30;
        final double  MIN_COMPONENT_VALUE    = -5.12;
        final double  MAX_COMPONENT_VALUE    = 5.12;
        final int     NUMBER_OF_PARTICLES    = 60;
        final int     MAX_ITERATIONS         = 1000;
        final double  DESIRED_FITNESS        = 0;
        final double  DESIRED_PRECISION      = 1e-4;
        final boolean IS_FULLY_INFORMED      = false;
        final double  INDIVIDUAL_FACTOR      = 1.494;
        final double  SOCIAL_FACTOR          = 1.494;
        final double  MIN_COMPONENT_SPEED    = -5.12;
        final double  MAX_COMPONENT_SPEED    = 5.12;
        final double  LOWER_STOCHASTIC_BOUND = 0.5;
        final double  UPPER_STOCHASTIC_BOUND = 1.0;

        IProblem<Particle> problem = new FunctionMinimizationProblem<>(
            new ParticleFunctionAdapter<>(new GriewankFunction<>())
        );

        RealVector minSpeedVector = new RealVector(NUMBER_OF_COMPONENTS, MIN_COMPONENT_SPEED);
        RealVector maxSpeedVector = new RealVector(NUMBER_OF_COMPONENTS, MAX_COMPONENT_SPEED);

        RealVector lowerStochasticBound = new RealVector(NUMBER_OF_COMPONENTS, LOWER_STOCHASTIC_BOUND);
        RealVector upperStochasticBound = new RealVector(NUMBER_OF_COMPONENTS, UPPER_STOCHASTIC_BOUND);

        ITopology<Particle> topology = new FullyConnectedTopology<>();

        IParticleSwarmOptimization<Particle> particleSwarmOptimization = new StochasticIWPSO<>(
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


        IFactory<Particle> particleFactory = new RandomParticleFactory(
            new ParticleFactory(
                new Particle(
                    new BoundedRealVector(NUMBER_OF_COMPONENTS, MIN_COMPONENT_VALUE, MAX_COMPONENT_VALUE),
                    new BoundedRealVector(NUMBER_OF_COMPONENTS, MIN_COMPONENT_SPEED, MAX_COMPONENT_SPEED)
                )
            )
        );

        particleSwarmOptimization.run(particleFactory.createMultipleInstances(NUMBER_OF_PARTICLES));
    }
}
