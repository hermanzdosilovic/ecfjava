package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.ga;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.AbstractPopulationMetaheuristic;
import com.dosilovic.hermanzvonimir.ecfjava.models.crossovers.ICrossover;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.IMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.selections.ISelection;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.Solutions;

import java.time.Duration;
import java.util.Date;
import java.util.List;

public abstract class AbstractGA<T> extends AbstractPopulationMetaheuristic<T> implements IGeneticAlgorithm<T> {

    protected boolean useElitism;
    protected int     maxGenerations;
    protected boolean evaluateEveryGeneration;
    protected double  desiredFitness;
    protected double  desiredPrecision;

    protected IProblem<T> problem;
    protected ISelection<T> selection;
    protected ICrossover<T> crossover;
    protected IMutation<T> mutation;

    protected int generation;

    public AbstractGA(
        boolean useElitism,
        int maxGenerations,
        boolean evaluateEveryGeneration,
        double desiredFitness,
        double desiredPrecision,
        IProblem<T> problem,
        ISelection<T> selection,
        ICrossover<T> crossover,
        IMutation<T> mutation
    ) {
        this.useElitism = useElitism;
        this.maxGenerations = maxGenerations;
        this.evaluateEveryGeneration = evaluateEveryGeneration;
        this.desiredFitness = desiredFitness;
        this.desiredPrecision = desiredPrecision;
        this.problem = problem;
        this.selection = selection;
        this.crossover = crossover;
        this.mutation = mutation;
    }

    @Override
    public ISolution<T> run() {
        long startTime = System.nanoTime();

        setPopulation(initialPopulation);

        if (!evaluateEveryGeneration) {
            Solutions.updateFitness(population, problem);
        }

        for (generation = 1; generation <= maxGenerations; generation++) {

            if (evaluateEveryGeneration) {
                Solutions.updateFitness(population, problem);
            }
            setBestSolution(Solutions.findBestByFitness(population));

            System.err.printf(
                "Generation #%d (%s)\n\tbestFitness: %f\n\n",
                generation, new Date(), bestSolution.getFitness()
            );

            if (Math.abs(bestSolution.getFitness() - desiredFitness) <= desiredPrecision) {
                System.err.println("Reached desired fitness.\n");
                break;
            }

            if (isStopped.get()) {
                System.err.println("Algorithm stopped.\n");
                break;
            }

            population = createNextPopulation();

            if (generation == maxGenerations) {
                System.err.println("Max generations reached.\n");
                break;
            }
        }

        if (evaluateEveryGeneration) {
            Solutions.updateFitness(population, problem);
        }
        setBestSolution(Solutions.findBestByFitness(population));

        Duration duration = Duration.ofNanos(System.nanoTime() - startTime);
        System.err.printf(
            "Time: %02d:%02d:%02d.%03d\n\n",
            duration.toHoursPart(),
            duration.toMinutesPart(),
            duration.toSecondsPart(),
            duration.toMillisPart()
        );
        System.err.println(bestSolution);
        System.err.println();
        System.err.flush();

        return bestSolution;
    }

    @Override
    public int getGeneration() {
        return generation;
    }

    @Override
    public void setGeneration(int generation) {
        if (generation < 1) {
            throw new IllegalArgumentException("Generation cannot be smaller than 1");
        }
        this.generation = generation;
    }

    @Override
    public int getMaxGenerations() {
        return maxGenerations;
    }

    @Override
    public void setMaxGenerations(int maxGenerations) {
        if (maxGenerations < 1) {
            throw new IllegalArgumentException("Max generations cannot be smaller than 1");
        } else if (maxGenerations < generation) {
            throw new IllegalArgumentException("Max generations cannot be smaller than current generation");
        }
        this.maxGenerations = maxGenerations;
    }

    protected abstract List<ISolution<T>> createNextPopulation();
}
