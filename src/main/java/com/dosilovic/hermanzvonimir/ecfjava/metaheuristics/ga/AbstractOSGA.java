package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.ga;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.ICoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.models.crossovers.ICrossover;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.IMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.selections.ISelection;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.util.Solutions;
import jdk.jshell.spi.ExecutionControl;

import java.time.Duration;
import java.util.*;

public abstract class AbstractOSGA<T extends ISolution> extends AbstractGA<T> {

    protected double           maxSelectionPressure;
    protected ICoolingSchedule successRatioSchedule;
    protected ICoolingSchedule comparisonFactorSchedule;

    protected double actualSelectionPressure;
    protected double comparisonFactor;
    protected double successRatio;

    public AbstractOSGA(
        boolean useElitism,
        int maxGenerations,
        boolean evaluateEveryGeneration,
        double desiredFitness,
        double desiredPrecision,
        double maxSelectionPressure,
        ICoolingSchedule successRatioSchedule,
        ICoolingSchedule comparisonFactorSchedule,
        IProblem<T> problem,
        ISelection<T> selection,
        ICrossover<T> crossover,
        IMutation<T> mutation
    ) {
        super(
            useElitism,
            maxGenerations,
            evaluateEveryGeneration,
            desiredFitness,
            desiredPrecision,
            problem,
            selection,
            crossover,
            mutation
        );
        this.maxSelectionPressure = maxSelectionPressure;
        this.comparisonFactorSchedule = comparisonFactorSchedule;
        this.successRatioSchedule = successRatioSchedule;
    }

    @Override
    public T run() {
        long startTime = System.nanoTime();

        setPopulation(initialPopulation);
        isStopped.set(false);
        bestSolution = null;

        if (!evaluateEveryGeneration) {
            problem.updateFitness(population);
        }

        actualSelectionPressure = 0;

        List<T> unsuccessfulPopulation = new ArrayList<>((int) (population.size() * (1 - successRatio)) + 1);
        Set<T>  successfulPopulation   = new HashSet<>((int) (population.size() * successRatio) + 1);

        List<T> nextPopulation = new ArrayList<>(population);
        List<T> tmpPopulation;

        for (generation = 1; generation <= maxGenerations; generation++) {
            comparisonFactor = comparisonFactorSchedule.getTemperature(generation - 1);
            successRatio = successRatioSchedule.getTemperature(generation - 1);

            if (evaluateEveryGeneration) {
                problem.updateFitness(population);
            }
            setBestSolution(Solutions.findBestByFitness(population));

            System.err.printf(
                "Generation #%d (%s)\n" +
                "\t bestFitness: %f\n" +
                "\tsuccessRatio: %f\n" +
                "\t  compFactor: %f\n" +
                "\tactSellPress: %f\n" +
                "\t     popSize: %d\n\n",
                generation,
                new Date(),
                bestSolution.getFitness(),
                successRatio,
                comparisonFactor,
                actualSelectionPressure,
                population.size()
            );

            if (Math.abs(bestSolution.getFitness() - desiredFitness) <= desiredPrecision) {
                System.err.println("Reached desired fitness.\n");
                break;
            }

            if (isStopped.get()) {
                System.err.println("Algorithm stopped.\n");
                break;
            }

            successfulPopulation.clear();
            unsuccessfulPopulation.clear();
            createSuccessfulPopulation(successfulPopulation, unsuccessfulPopulation);

            actualSelectionPressure =
                (double) (successfulPopulation.size() + unsuccessfulPopulation.size()) / population.size();

            createNextPopulation(nextPopulation, successfulPopulation, unsuccessfulPopulation);

            tmpPopulation = population;
            population = nextPopulation;
            nextPopulation = tmpPopulation;

            if (actualSelectionPressure >= maxSelectionPressure) {
                System.err.println("Max selection pressure reached.\n");
                break;
            }

            if (generation == maxGenerations) {
                System.err.println("Max generations reached.");
                break;
            }
        }

        if (evaluateEveryGeneration) {
            problem.updateFitness(population);
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

    protected abstract void createSuccessfulPopulation(
        Set<T> successfulPopulation,
        List<T> unsuccessfulPopulation
    );

    protected abstract void createNextPopulation(
        List<T> nextPopulation,
        Set<T> successfulPopulation,
        List<T> unsuccessfulPopulation
    );

    protected void createNextPopulation(List<T> nextPopulation) {
        throw new UnsupportedOperationException();
    }
}
