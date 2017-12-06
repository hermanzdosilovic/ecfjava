package com.dosilovic.hermanzvonimir.ecfjava.examples.ga;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.ga.IGeneticAlgorithm;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.ga.SimpleOSGA;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.GeometricCoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.ICoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.models.crossovers.ICrossover;
import com.dosilovic.hermanzvonimir.ecfjava.models.crossovers.UniformCrossover;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.BitVectorStohasticMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.IMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.MaxOnesProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.selections.ISelection;
import com.dosilovic.hermanzvonimir.ecfjava.models.selections.TournamentSelection;
import com.dosilovic.hermanzvonimir.ecfjava.util.BitVector;

import java.util.ArrayList;
import java.util.Collection;

public class MaxOnesSimpleOSGA {

    public static void main(String[] args) {
        final int     POPULATION_SIZE        = 80;
        final int     NUMBER_OF_COMPONENTS   = 100;
        final int     TOURNAMENT_SIZE        = 3;
        final boolean ALLOW_REPEAT           = false;
        final double  MUTATION_PROBABILITY   = 0.1;
        final boolean FORCE_MUTATION         = true;
        final boolean USE_ELITISM            = true;
        final int     MAX_GENERATIONS        = 1000;
        final double  DESIRED_FITNESS        = 1.0;
        final double  DESIRED_PRECISION      = 1e-3;
        final double  MAX_SELECTION_PRESSURE = 1000_000;
        final double  MIN_SUCCESS_RATIO      = 0.6;
        final double  MAX_SUCCESS_RATIO      = 1;
        final double  MIN_COMPARISON_FACTOR  = 0.1;
        final double  MAX_COMPARISON_FACTOR  = 1;

        Collection<BitVector> initialPopulation = createInitialPopulation(POPULATION_SIZE, NUMBER_OF_COMPONENTS);

        IProblem<BitVector>   problem   = new MaxOnesProblem();
        ISelection<BitVector> selection = new TournamentSelection<>(TOURNAMENT_SIZE, ALLOW_REPEAT);
        ICrossover<BitVector> crossover = new UniformCrossover<>();
        IMutation<BitVector>  mutation  = new BitVectorStohasticMutation<>(MUTATION_PROBABILITY, FORCE_MUTATION);

        ICoolingSchedule successRatioSchedule = new GeometricCoolingSchedule(
            MAX_GENERATIONS,
            MIN_SUCCESS_RATIO,
            MAX_SUCCESS_RATIO
        );
        ICoolingSchedule comparisonFactorSchedule = new GeometricCoolingSchedule(
            MAX_GENERATIONS,
            MIN_COMPARISON_FACTOR,
            MAX_COMPARISON_FACTOR
        );

        IGeneticAlgorithm<BitVector> geneticAlgorithm = new SimpleOSGA<>(
            USE_ELITISM,
            MAX_GENERATIONS,
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

        geneticAlgorithm.run(initialPopulation);
    }

    private static Collection<BitVector> createInitialPopulation(int populationSize, int numberOfComponents) {
        Collection<BitVector> initialPopulation = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            initialPopulation.add(new BitVector(numberOfComponents));
        }

        return initialPopulation;
    }
}
