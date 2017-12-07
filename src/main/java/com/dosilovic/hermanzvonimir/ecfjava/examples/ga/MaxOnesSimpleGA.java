package com.dosilovic.hermanzvonimir.ecfjava.examples.ga;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.ga.IGeneticAlgorithm;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.ga.SimpleGA;
import com.dosilovic.hermanzvonimir.ecfjava.models.crossovers.ICrossover;
import com.dosilovic.hermanzvonimir.ecfjava.models.crossovers.UniformCrossover;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.BitVectorStochasticMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.IMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.MaxOnesProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.selections.ISelection;
import com.dosilovic.hermanzvonimir.ecfjava.models.selections.TournamentSelection;
import com.dosilovic.hermanzvonimir.ecfjava.util.BitVector;

import java.util.ArrayList;
import java.util.Collection;

public final class MaxOnesSimpleGA {

    public static void main(String[] args) {
        final int     NUMBER_OF_COMPONENTS = 300;
        final int     POPULATION_SIZE      = 200;
        final int     TOURNAMENT_SIZE      = 3;
        final boolean ALLOW_REPEAT         = false;
        final double  MUTATION_PROBABILITY = 0.0;
        final boolean FORCE_MUTATION       = true;
        final boolean USE_ELITISM          = true;
        final int     MAX_GENERATIONS      = 100_000;
        final double  DESIRED_FITNESS      = 1.0;
        final double  DESIRED_PRECISION    = 1e-3;

        IProblem<BitVector>   problem   = new MaxOnesProblem();
        ISelection<BitVector> selection = new TournamentSelection<>(TOURNAMENT_SIZE, ALLOW_REPEAT);
        ICrossover<BitVector> crossover = new UniformCrossover<>();
        IMutation<BitVector>  mutation  = new BitVectorStochasticMutation<>(MUTATION_PROBABILITY, FORCE_MUTATION);

        IGeneticAlgorithm<BitVector> geneticAlgorithm = new SimpleGA<>(
            USE_ELITISM,
            MAX_GENERATIONS,
            DESIRED_FITNESS,
            DESIRED_PRECISION,
            problem,
            selection,
            crossover,
            mutation
        );

        geneticAlgorithm.run(createInitialPopulation(POPULATION_SIZE, NUMBER_OF_COMPONENTS));
    }

    private static Collection<BitVector> createInitialPopulation(int populationSize, int numberOfComponents) {
        Collection<BitVector> initialPopulation = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            initialPopulation.add(new BitVector(numberOfComponents));
        }

        return initialPopulation;
    }
}
