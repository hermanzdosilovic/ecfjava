package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.ga;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.ICoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.models.crossovers.ICrossover;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.IMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.selections.ISelection;
import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;

import java.time.Duration;
import java.util.*;

public abstract class AbstractOSGA<T> implements IGeneticAlgorithm<T> {

    protected boolean useElitism;
    protected int maxGenerations;
    protected double desiredFitness;
    protected double desiredPrecision;
    protected double maxSelectionPressure;
    protected ICoolingSchedule successRatioSchedule;
    protected ICoolingSchedule comparisonFactorSchedule;

    protected IProblem<T> problem;
    protected ISelection<T> selection;
    protected ICrossover<T> crossover;
    protected IMutation<T> mutation;

    protected Collection<Solution<T>> initialPopulation;

    protected static final Random RAND = new Random();

    public AbstractOSGA(boolean useElitism, int maxGenerations, double desiredFitness, double desiredPrecision,
        double maxSelectionPressure, ICoolingSchedule successRatioSchedule, ICoolingSchedule comparisonFactorSchedule,
        IProblem<T> problem, ISelection<T> selection, ICrossover<T> crossover, IMutation<T> mutation) {
        this.useElitism = useElitism;
        this.maxGenerations = maxGenerations;
        this.desiredFitness = desiredFitness;
        this.desiredPrecision = desiredPrecision;
        this.maxSelectionPressure = maxSelectionPressure;
        this.comparisonFactorSchedule = comparisonFactorSchedule;
        this.successRatioSchedule = successRatioSchedule;
        this.problem = problem;
        this.selection = selection;
        this.crossover = crossover;
        this.mutation = mutation;
    }

    public void setInitialPopulation(final Collection<T> initialPopulation) {
        this.initialPopulation = new ArrayList<>();
        for (T individual : initialPopulation) {
            this.initialPopulation.add(new Solution<T>(individual));
        }
    }

    @Override public T run(Collection<T> initialPopulation) {
        setInitialPopulation(initialPopulation);
        return run();
    }

    @Override public T run() {
        long startTime = System.nanoTime();

        if (initialPopulation == null) {
            throw new IllegalStateException("no initial population");
        }

        Collection<Solution<T>> currentPopulation = initialPopulation;
        Collection<Solution<T>> successfulPopulation;
        List<Solution<T>> unsuccessfulPopulation;
        Solution<T> bestSolution;
        double actualSelectionPressure = 0;
        double comparisonFactor;
        double successRatio;

        int generation;
        for (generation = 1; generation <= maxGenerations; generation++) {
            comparisonFactor = comparisonFactorSchedule.getTemperature(generation - 1);
            successRatio = successRatioSchedule.getTemperature(generation - 1);

            Solution.evaluateFitness(currentPopulation, problem);
            bestSolution = Solution.findBest(currentPopulation);

            System.err.printf(
                "Generation #%d (%s):\n\tbestFitness = %f\n\tsuccessRatio = %f\n\tcompFactor = %f\n\tpopSize = %d\n",
                generation, new Date(), bestSolution.getFitness(), successRatio, comparisonFactor,
                currentPopulation.size());

            if (Math.abs(bestSolution.getFitness() - desiredFitness) <= desiredPrecision) {
                System.err.println("\nReached desired fitness.");
                break;
            }

            unsuccessfulPopulation = new ArrayList<>((int) (currentPopulation.size() * (1 - successRatio)) + 1);
            successfulPopulation =
                createSuccessfulPopulation(currentPopulation, unsuccessfulPopulation, bestSolution, comparisonFactor,
                    successRatio);

            actualSelectionPressure =
                (double) (successfulPopulation.size() + unsuccessfulPopulation.size()) / currentPopulation.size();
            System.err.printf("\tactSelPress = %f\n\n", actualSelectionPressure);

            if (actualSelectionPressure >= maxSelectionPressure) {
                System.err.println("Max selection pressure reached.");
                break;
            }

            currentPopulation =
                createNextPopulation(currentPopulation, successfulPopulation, unsuccessfulPopulation, bestSolution);

            if (generation == maxGenerations) {
                System.err.println("Max generations reached.");
                break;
            }
        }

        Solution.evaluateFitness(currentPopulation, problem);
        bestSolution = Solution.findBest(currentPopulation);

        long stopTime = System.nanoTime();
        Duration duration = Duration.ofNanos(stopTime - startTime);

        System.err.printf("\nSolution: %s\nFitness: %f\n", bestSolution.getRepresentative(), bestSolution.getFitness());
        System.err.printf("Generation: #%d\n", generation);
        System.err.printf("Time: %02d:%02d:%02d.%03d\n\n", duration.toHoursPart(), duration.toMinutesPart(),
            duration.toSecondsPart(), duration.toMillisPart());
        System.err.flush();

        return bestSolution.getRepresentative();
    }

    protected abstract Collection<Solution<T>> createSuccessfulPopulation(Collection<Solution<T>> currentPopulation,
        Collection<Solution<T>> unsuccessfulPopulation, Solution<T> bestSolution, double comparisonFactor,
        double successRatio);

    protected abstract Collection<Solution<T>> createNextPopulation(Collection<Solution<T>> currentPopulation,
        Collection<Solution<T>> successfulPopulation, List<Solution<T>> unsuccessfulPopulation,
        Solution<T> bestSolution);
}
