package com.dosilovic.hermanzvonimir.ecfjava.examples.sa;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.ISimulatedAnnealing;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.SimpleSA;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.GeometricCoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.ICoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.IMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.RealVectorGaussianMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.FunctionMinimizationProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.numeric.RastriginFunction;
import com.dosilovic.hermanzvonimir.ecfjava.util.RealVector;

import java.util.Random;

public final class RastringSimpleSA {

    public static void main(String[] args) {
        final int     NUMBER_OF_COMPONENTS = 30;
        final double  MIN_COMPONENT_VALUE  = -10;
        final double  MAX_COMPONENT_VALUE  = 10;
        final int     OUTER_ITERATIONS     = 4000;
        final double  OUTER_INITIAL_TEMP   = 1000;
        final double  OUTER_FINAL_TEMP     = 1e-4;
        final int     INNER_ITERATIONS     = 1000;
        final double  MUTATION_PROBABILITY = 0.2;
        final boolean FORCE_MUTATION       = true;
        final double  SIGMA                = 0.9;
        final double  DESIRED_PENALTY      = 0;
        final double  DESIRED_PRECISION    = 1e-3;

        IProblem<RealVector>  problem  = new FunctionMinimizationProblem<>(new RastriginFunction<>());
        IMutation<RealVector> mutation = new RealVectorGaussianMutation<>(MUTATION_PROBABILITY, FORCE_MUTATION, SIGMA);

        ICoolingSchedule outerCoolingSchedule = new GeometricCoolingSchedule(
            OUTER_ITERATIONS,
            OUTER_INITIAL_TEMP,
            OUTER_FINAL_TEMP
        );
        ICoolingSchedule innerCoolingSchedule = new GeometricCoolingSchedule(
            INNER_ITERATIONS, // we just want INNER_ITERATIONS to happen
            0,                // don't care for this example
            0                 // don't care for this example
        );

        ISimulatedAnnealing<RealVector> simulatedAnnealing = new SimpleSA<>(
            DESIRED_PENALTY,
            DESIRED_PRECISION,
            problem,
            mutation,
            outerCoolingSchedule,
            innerCoolingSchedule
        );

        simulatedAnnealing.run(new RealVector(NUMBER_OF_COMPONENTS, MIN_COMPONENT_VALUE, MAX_COMPONENT_VALUE));
    }
}
