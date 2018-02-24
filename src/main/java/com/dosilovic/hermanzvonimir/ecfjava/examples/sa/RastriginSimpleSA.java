package com.dosilovic.hermanzvonimir.ecfjava.examples.sa;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.ISimulatedAnnealing;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.SimpleSA;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.GeometricCoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.ICoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.IMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.RealVectorGaussianMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.adapters.CopyOnMutationAdapter;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.FunctionMinimizationProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.BoundedRealVector;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.RealVector;
import com.dosilovic.hermanzvonimir.ecfjava.numeric.RastriginFunction;

public final class RastriginSimpleSA {

    public static void main(String[] args) {
        final int     NUMBER_OF_COMPONENTS = 30;
        final double  MIN_COMPONENT_VALUE  = -10;
        final double  MAX_COMPONENT_VALUE  = 10;
        final double  DESIRED_PENALTY      = 0;
        final double  DESIRED_PRECISION    = 1e-3;
        final int     OUTER_ITERATIONS     = 4000;
        final double  OUTER_INITIAL_TEMP   = 1000;
        final double  OUTER_FINAL_TEMP     = 1e-4;
        final int     INNER_ITERATIONS     = 1000;
        final double  INNER_INITIAL_TEMP   = 1000;
        final double  INNER_FINAL_TEMP     = 1e-4;
        final double  MUTATION_PROBABILITY = 0.2;
        final boolean FORCE_MUTATION       = true;
        final double  MUTATION_SIGMA       = 0.9;

        IProblem<RealVector> problem = new FunctionMinimizationProblem<>(new RastriginFunction<>());

        IMutation<RealVector> mutation = new CopyOnMutationAdapter<>(new RealVectorGaussianMutation<>(
            MUTATION_PROBABILITY, FORCE_MUTATION, MUTATION_SIGMA
        ));

        ICoolingSchedule outerCoolingSchedule = new GeometricCoolingSchedule(
            OUTER_ITERATIONS, OUTER_INITIAL_TEMP, OUTER_FINAL_TEMP
        );

        ICoolingSchedule innerCoolingSchedule = new GeometricCoolingSchedule(
            INNER_ITERATIONS, INNER_INITIAL_TEMP, INNER_FINAL_TEMP
        );

        ISimulatedAnnealing<RealVector> simulatedAnnealing = new SimpleSA<>(
            DESIRED_PENALTY,
            DESIRED_PRECISION,
            problem,
            mutation,
            outerCoolingSchedule,
            innerCoolingSchedule
        );

        RealVector initialSolution = new BoundedRealVector(NUMBER_OF_COMPONENTS, MIN_COMPONENT_VALUE, MAX_COMPONENT_VALUE);
        initialSolution.randomizeValues();

        simulatedAnnealing.run(initialSolution);
    }
}
