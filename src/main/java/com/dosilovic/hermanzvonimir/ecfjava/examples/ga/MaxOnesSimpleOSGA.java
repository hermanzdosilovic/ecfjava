package com.dosilovic.hermanzvonimir.ecfjava.examples.ga;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.ga.IGeneticAlgorithm;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.ga.SimpleOSGA;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.GeometricCoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.ICoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.models.crossovers.ICrossover;
import com.dosilovic.hermanzvonimir.ecfjava.models.crossovers.WeightedCrossover;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.BitVectorStochasticMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.IMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.MaxOnesProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.selections.ISelection;
import com.dosilovic.hermanzvonimir.ecfjava.models.selections.TournamentSelection;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.factories.BitVectorFactory;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.BitVector;

public class MaxOnesSimpleOSGA {

    public static void main(String[] args) {
        final int     NUMBER_OF_COMPONENTS      = 100;
        final int     POPULATION_SIZE           = 75;
        final boolean USE_ELITISM               = true;
        final int     MAX_GENERATIONS           = 2000;
        final boolean EVALUATE_EVERY_GENERATION = false;
        final double  DESIRED_FITNESS           = 1.0;
        final double  DESIRED_PRECISION         = 0;
        final double  MAX_SELECTION_PRESSURE    = 10_000;
        final double  MIN_SUCCESS_RATIO         = 0.2;
        final double  MAX_SUCCESS_RATIO         = 1;
        final double  MIN_COMPARISON_FACTOR     = 0.5;
        final double  MAX_COMPARISON_FACTOR     = 1;
        final int     TOURNAMENT_SIZE           = 3;
        final boolean IS_UNIQUE_TOURNAMENT      = true;
        final double  MUTATION_PROBABILITY      = 0.0;
        final boolean FORCE_MUTATION            = true;

        IProblem<BitVector>   problem   = new MaxOnesProblem<>();
        ISelection<BitVector> selection = new TournamentSelection<>(TOURNAMENT_SIZE, IS_UNIQUE_TOURNAMENT);
        ICrossover<BitVector> crossover = new WeightedCrossover<>();
        IMutation<BitVector>  mutation  = new BitVectorStochasticMutation<>(MUTATION_PROBABILITY, FORCE_MUTATION);

        ICoolingSchedule successRatioSchedule = new GeometricCoolingSchedule(
            MAX_GENERATIONS, MIN_SUCCESS_RATIO, MAX_SUCCESS_RATIO
        );

        ICoolingSchedule comparisonFactorSchedule = new GeometricCoolingSchedule(
            MAX_GENERATIONS, MIN_COMPARISON_FACTOR, MAX_COMPARISON_FACTOR
        );

        IGeneticAlgorithm<BitVector> geneticAlgorithm = new SimpleOSGA<>(
            USE_ELITISM,
            MAX_GENERATIONS,
            EVALUATE_EVERY_GENERATION,
            DESIRED_FITNESS,
            DESIRED_PRECISION,
            MAX_SELECTION_PRESSURE,
            successRatioSchedule,
            comparisonFactorSchedule,
            problem,
            selection,
            crossover,
            mutation
        );

        geneticAlgorithm.run(
            new BitVectorFactory(new BitVector(NUMBER_OF_COMPONENTS)).createMultipleInstances(POPULATION_SIZE)
        );
    }
}
