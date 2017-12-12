package com.dosilovic.hermanzvonimir.ecfjava.examples.pso;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.FullyInformedPSO;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.IParticleSwarmOptimization;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.GeometricCoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.ICoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.FunctionMinimizationProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.numeric.RastriginFunction;
import com.dosilovic.hermanzvonimir.ecfjava.util.RealVector;

public final class RastriginFIPSO {

    public static void main(String[] args) {
        final int    NUMBER_OF_COMPONENTS     = 20;
        final int    NUMBER_OF_PARTICLES      = 500;
        final int    MAX_NUMBER_OF_ITERATIONS = 20000;
        final double DESIRED_FITNESS          = 0;
        final double DESIRED_PRECISION        = 1e-2;
        final double MIN_INDIVIDUAL_FACTOR    = 1.0;
        final double MAX_INDIVIDUAL_FACTOR    = 2.0;
        final double MIN_SOCIAL_FACTOR        = 3.0;
        final double MAX_SOCIAL_FACTOR        = 4.0;
        final double MIN_INERTIA_FACTOR       = 1;
        final double MAX_INERTIA_FACTOR       = 1;
        final double MIN_CONSTRICTION_FACTOR  = 0.7;
        final double MAX_CONSTRICTION_FACTOR  = 0.7;
        final double MIN_COMPONENT_VALUE      = -1;
        final double MAX_COMPONENT_VALUE      = 1;

        IProblem<RealVector> problem = new FunctionMinimizationProblem<>(new RastriginFunction<>());
        ICoolingSchedule individualFactorSchedule = new GeometricCoolingSchedule(
            MAX_NUMBER_OF_ITERATIONS,
            MAX_INDIVIDUAL_FACTOR,
            MIN_INDIVIDUAL_FACTOR
        );
        ICoolingSchedule socialFactorSchedule = new GeometricCoolingSchedule(
            MAX_NUMBER_OF_ITERATIONS,
            MAX_SOCIAL_FACTOR,
            MIN_SOCIAL_FACTOR
        );
        ICoolingSchedule inertiaSchedule = new GeometricCoolingSchedule(
            MAX_NUMBER_OF_ITERATIONS,
            MAX_INERTIA_FACTOR,
            MIN_INERTIA_FACTOR
        );
        ICoolingSchedule constrictionFactorSchedule = new GeometricCoolingSchedule(
            MAX_NUMBER_OF_ITERATIONS,
            MAX_CONSTRICTION_FACTOR,
            MIN_CONSTRICTION_FACTOR
        );

        IParticleSwarmOptimization<RealVector> particleSwarmOptimization = new FullyInformedPSO<>(
            MAX_NUMBER_OF_ITERATIONS,
            DESIRED_FITNESS,
            DESIRED_PRECISION,
            problem,
            individualFactorSchedule,
            socialFactorSchedule,
            inertiaSchedule,
            constrictionFactorSchedule
        );

        particleSwarmOptimization.run(RealVector.createCollection(
            NUMBER_OF_PARTICLES,
            NUMBER_OF_COMPONENTS,
            MIN_COMPONENT_VALUE,
            MAX_COMPONENT_VALUE
        ));
    }
}
