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
import com.dosilovic.hermanzvonimir.ecfjava.numeric.RastriginFunction;
import com.dosilovic.hermanzvonimir.ecfjava.util.RealVector;

public final class RastriginSimpleGA {

    public static void main(String[] args) {
        final int     NUMBER_OF_COMPONENTS = 30;
        final double  MIN_COMPONENT_VALUE  = -10;
        final double  MAX_COMPONENT_VALUE  = 10;
        final int     POPULATION_SIZE      = 20;
        final int     TOURNAMENT_SIZE      = 3;
        final boolean ALLOW_REPEAT         = false;
        final double  ALPHA                = 0.9;
        final double  MUTATION_PROBABILITY = 0.1;
        final boolean FORCE_MUTATION       = true;
        final double  SIGMA                = 0.9;
        final boolean USE_ELITISM          = true;
        final int     MAX_GENERATIONS      = 100_000;
        final double  DESIRED_FITNESS      = 0;
        final double  DESIRED_PRECISION    = 1e-3;

        IProblem<RealVector>   problem   = new FunctionMinimizationProblem<>(new RastriginFunction<>());
        ISelection<RealVector> selection = new TournamentSelection<>(TOURNAMENT_SIZE, ALLOW_REPEAT);
        ICrossover<RealVector> crossover = new BLXAlphaCrossover<>(ALPHA);
        IMutation<RealVector>  mutation  = new RealVectorGaussianMutation<>(
            MUTATION_PROBABILITY,
            FORCE_MUTATION,
            SIGMA
        );

        IGeneticAlgorithm<RealVector> geneticAlgorithm = new SimpleGA<>(
            USE_ELITISM,
            MAX_GENERATIONS,
            DESIRED_FITNESS,
            DESIRED_PRECISION,
            problem,
            selection,
            crossover,
            mutation
        );

        geneticAlgorithm.run(RealVector.createCollection(
            POPULATION_SIZE,
            NUMBER_OF_COMPONENTS,
            MIN_COMPONENT_VALUE,
            MAX_COMPONENT_VALUE
        ));
    }
}
