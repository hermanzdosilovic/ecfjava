package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.ga;

import com.dosilovic.hermanzvonimir.ecfjava.models.crossovers.ICrossover;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.IMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.selections.ISelection;
import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public abstract class AbstractGA<T> implements IGeneticAlgorithm<T> {

    protected boolean useElitism;
    protected int     maxGenerations;
    protected double  desiredFitness;
    protected double  desiredPrecision;

    protected IProblem<T>   problem;
    protected ISelection<T> selection;
    protected ICrossover<T> crossover;
    protected IMutation<T>  mutation;

    protected Collection<Solution<T>> initialPopulation;

    public AbstractGA(
        boolean useElitism,
        int maxGenerations,
        double desiredFitness,
        double desiredPrecision,
        IProblem<T> problem,
        ISelection<T> selection,
        ICrossover<T> crossover,
        IMutation<T> mutation
    ) {
        this.useElitism = useElitism;
        this.maxGenerations = maxGenerations;
        this.desiredFitness = desiredFitness;
        this.desiredPrecision = desiredPrecision;
        this.problem = problem;
        this.selection = selection;
        this.crossover = crossover;
        this.mutation = mutation;
    }

    public void setInitialPopulation(final Collection<T> initialPopulation) {
        this.initialPopulation = new ArrayList<>();
        for (T individual : initialPopulation) {
            this.initialPopulation.add(new Solution(individual));
        }
    }

    @Override
    public T run(Collection<T> initialPopulation) {
        setInitialPopulation(initialPopulation);
        return run();
    }

    @Override
    public T run() {
        long startTime = System.nanoTime();

        if (initialPopulation == null) {
            throw new IllegalStateException("No initial population");
        }

        Collection<Solution<T>> currentPopulation = initialPopulation;
        Solution<T>             bestSolution;

        int generation;
        for (generation = 1; generation <= maxGenerations; generation++) {
            Solution.evaluateFitness(currentPopulation, problem);
            bestSolution = Solution.findBest(currentPopulation);

            System.err.printf(
                "Generation #%d (%s):\n\tbestFitness = %f\n\n",
                generation,
                new Date(),
                bestSolution.getFitness()
            );

            if (Math.abs(bestSolution.getFitness() - desiredFitness) <= desiredPrecision) {
                System.err.println("Reached desired fitness.\n");
                break;
            }

            currentPopulation = createNextPopulation(currentPopulation, bestSolution);

            if (generation == maxGenerations) {
                System.err.println("Max generations reached.\n");
                break;
            }
        }

        Solution.evaluateFitness(currentPopulation, problem);
        bestSolution = Solution.findBest(currentPopulation);

        long     stopTime = System.nanoTime();
        Duration duration = Duration.ofNanos(stopTime - startTime);

        System.err.printf("Solution: %s\nFitness: %f\n", bestSolution.getRepresentative(), bestSolution.getFitness());
        System.err.printf("Generation: #%d\n", generation);
        System.err.printf(
            "Time: %02d:%02d:%02d.%03d\n\n",
            duration.toHoursPart(),
            duration.toMinutesPart(),
            duration.toSecondsPart(),
            duration.toMillisPart()
        );
        System.err.flush();

        return bestSolution.getRepresentative();
    }

    protected abstract Collection<Solution<T>> createNextPopulation(
        Collection<Solution<T>> currentPopulation,
        Solution<T> bestSolution
    );
}
