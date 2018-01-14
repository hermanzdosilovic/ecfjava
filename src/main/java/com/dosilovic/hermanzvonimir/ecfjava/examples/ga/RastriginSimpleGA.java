package com.dosilovic.hermanzvonimir.ecfjava.examples.ga;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.ga.IGeneticAlgorithm;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.ga.SimpleGA;
import com.dosilovic.hermanzvonimir.ecfjava.models.crossovers.BLXAlphaCrossover;
import com.dosilovic.hermanzvonimir.ecfjava.models.crossovers.ICrossover;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.IMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.RealVectorGaussianMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.FunctionMinimizationProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.selections.ISelection;
import com.dosilovic.hermanzvonimir.ecfjava.models.selections.TournamentSelection;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.factories.ISolutionFactory;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.factories.RealVectorFactory;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.factories.SimpleSolutionFactory;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.RealVector;
import com.dosilovic.hermanzvonimir.ecfjava.numeric.RastriginFunction;

public final class RastriginSimpleGA {

    public static void main(String[] args) {
        final int     NUMBER_OF_COMPONENTS      = 30;
        final double  MIN_COMPONENT_VALUE       = -10;
        final double  MAX_COMPONENT_VALUE       = 10;
        final boolean IS_BOUNDED                = true;
        final int     POPULATION_SIZE           = 20;
        final boolean USE_ELITISM               = true;
        final int     MAX_GENERATIONS           = 100_000;
        final boolean EVALUATE_EVERY_GENERATION = false;
        final double  DESIRED_FITNESS           = 0;
        final double  DESIRED_PRECISION         = 1e-3;
        final int     TOURNAMENT_SIZE           = 10;
        final boolean IS_UNIQUE_TOURNAMENT      = true;
        final double  CROSSOVER_ALPHA           = 0.2;
        final double  MUTATION_PROBABILITY      = 0.1;
        final boolean FORCE_MUTATION            = true;
        final double  MUTATION_SIGMA            = 0.3;

        IProblem<RealVector>   problem   = new FunctionMinimizationProblem<>(new RastriginFunction<>());
        ISelection<RealVector> selection = new TournamentSelection<>(TOURNAMENT_SIZE, IS_UNIQUE_TOURNAMENT);
        ICrossover<RealVector> crossover = new BLXAlphaCrossover<>(CROSSOVER_ALPHA);
        IMutation<RealVector> mutation = new RealVectorGaussianMutation<>(
            MUTATION_PROBABILITY, FORCE_MUTATION, MUTATION_SIGMA
        );

        IGeneticAlgorithm<RealVector> geneticAlgorithm = new SimpleGA<>(
            USE_ELITISM,
            MAX_GENERATIONS,
            EVALUATE_EVERY_GENERATION,
            DESIRED_FITNESS,
            DESIRED_PRECISION,
            problem,
            selection,
            crossover,
            mutation
        );

        ISolutionFactory<RealVector> populationFactory = new SimpleSolutionFactory<>(
            new RealVectorFactory(
                new RealVector(NUMBER_OF_COMPONENTS, MIN_COMPONENT_VALUE, MAX_COMPONENT_VALUE, IS_BOUNDED)
            )
        );

        geneticAlgorithm.run(populationFactory.createMultipleInstances(POPULATION_SIZE));
    }
}
