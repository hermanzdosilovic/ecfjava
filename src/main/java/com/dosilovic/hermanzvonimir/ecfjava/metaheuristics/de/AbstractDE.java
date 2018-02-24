package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.de;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.AbstractPopulationMetaheuristic;
import com.dosilovic.hermanzvonimir.ecfjava.models.crossovers.ICrossover;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.util.Solutions;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.RealVector;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class AbstractDE<T extends RealVector> extends AbstractPopulationMetaheuristic<T> implements IDifferentialEvolution<T> {

    protected int    maxGenerations;
    protected double desiredFitness;
    protected double desiredPrecision;

    protected IProblem<T>   problem;
    protected ICrossover<T> crossover;

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
    public T run() {
        long startTime = System.nanoTime();

        setPopulation(initialPopulation);
        isStopped.set(false);
        bestSolution = null;

        List<T> nextPopulation = new ArrayList<>(initialPopulation);
        List<T> tmpPopulation;

        int generation;
        for (generation = 1; generation <= maxGenerations; generation++) {
            problem.updateFitness(population);
            setBestSolution(Solutions.findBestByFitness(population));

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

            if (isStopped.get()) {
                System.err.println("Algorithm stopped.\n");
                break;
            }

            int k = 0;
            for (T individual : population) {
                T trialSolution = createTrialSolution(individual);
                if (trialSolution.getFitness() >= individual.getFitness()) {
                    nextPopulation.set(k, trialSolution);
                } else {
                    nextPopulation.set(k, individual);
                }
                k++;
            }

            tmpPopulation = nextPopulation;
            nextPopulation = population;
            population = tmpPopulation;

            if (generation == maxGenerations) {
                System.err.println("Max generations reached.\n");
                break;
            }
        }

        problem.updateFitness(population);
        setBestSolution(Solutions.findBestByFitness(population));

        long     stopTime = System.nanoTime();
        Duration duration = Duration.ofNanos(stopTime - startTime);

        System.err.printf("Solution: %s\nFitness: %f\n", bestSolution, bestSolution.getFitness());
        System.err.printf("Generation: #%d\n", generation);
        System.err.printf(
            "Time: %02d:%02d:%02d.%03d\n\n",
            duration.toHoursPart(),
            duration.toMinutesPart(),
            duration.toSecondsPart(),
            duration.toMillisPart()
        );
        System.err.flush();

        return bestSolution;
    }

    protected abstract T createTrialSolution(T solution);
}
