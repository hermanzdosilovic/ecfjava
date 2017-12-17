package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.de;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.AbstractMetaheuristic;
import com.dosilovic.hermanzvonimir.ecfjava.models.crossovers.ICrossover;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.util.RealVector;
import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public abstract class AbstractDE<T extends RealVector> extends AbstractMetaheuristic<T> implements IDifferentialEvolution<T> {

    protected int    maxGenerations;
    protected double desiredFitness;
    protected double desiredPrecision;

    protected IProblem<T>   problem;
    protected ICrossover<T> crossover;

    protected Solution<T> bestSolution;

    protected Collection<Solution<T>> initialPopulation;

    public AbstractDE(
        int maxGenerations,
        double desiredFitness,
        double desiredPrecision,
        IProblem<T> problem,
        ICrossover<T> crossover
    ) {
        this.maxGenerations = maxGenerations;
        this.desiredFitness = desiredFitness;
        this.desiredPrecision = desiredPrecision;
        this.problem = problem;
        this.crossover = crossover;
    }

    @Override
    public T run(Collection<Solution<T>> initialPopulation) {
        setInitialPopulation(initialPopulation);
        return run();
    }

    @Override
    public void setInitialPopulation(Collection<Solution<T>> initialPopulation) {
        this.initialPopulation = initialPopulation;
    }

    @Override
    public T run() {
        long startTime = System.nanoTime();

        if (initialPopulation == null) {
            throw new IllegalStateException("No initial population");
        }

        List<Solution<T>> currentPopulation = new ArrayList<>(initialPopulation);
        List<Solution<T>> nextPopulation    = new ArrayList<>(initialPopulation);
        List<Solution<T>> tmpPopulation;

        int generation;
        for (generation = 1; generation <= maxGenerations; generation++) {
            Solution.evaluateFitness(currentPopulation, problem);
            bestSolution = Solution.findBestByFitness(currentPopulation);
            notifyObservers(bestSolution);

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

            int k = 0;
            for (Solution<T> individual : currentPopulation) {
                Solution<T> trialSolution = createTrialSolution(individual, currentPopulation);
                if (trialSolution.getFitness() >= individual.getFitness()) {
                    nextPopulation.set(k, trialSolution);
                } else {
                    nextPopulation.set(k, individual);
                }
                k++;
            }

            tmpPopulation = nextPopulation;
            nextPopulation = currentPopulation;
            currentPopulation = tmpPopulation;

            if (generation == maxGenerations) {
                System.err.println("Max generations reached.\n");
                break;
            }
        }

        Solution.evaluateFitness(currentPopulation, problem);
        bestSolution = Solution.findBestByFitness(currentPopulation);
        notifyObservers(bestSolution);

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

    @Override
    public Solution<T> getBestSolution() {
        return bestSolution;
    }

    protected abstract Solution<T> createTrialSolution(
        Solution<T> individual,
        List<Solution<T>> currentPopulation
    );
}
