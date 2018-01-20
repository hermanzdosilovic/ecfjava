package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.ga;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.ICoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.models.crossovers.ICrossover;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.IMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.selections.ISelection;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.Solutions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public abstract class AbstractOSGA<T> extends AbstractGA<T> {

    protected double           maxSelectionPressure;
    protected ICoolingSchedule successRatioSchedule;
    protected ICoolingSchedule comparisonFactorSchedule;

    protected double actualSelectionPressure;
    protected double comparisonFactor;
    protected double successRatio;

    private Collection<ISolution<T>> successfulPopulation;
    private List<ISolution<T>>       unsuccessfulPopulation;

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
    public ISolution<T> run() {
        long startTime = System.nanoTime();

        setPopulation(initialPopulation);
        isStopped.set(false);
        bestSolution = null;

        if (!evaluateEveryGeneration) {
            Solutions.updateFitness(population, problem);
        }

        actualSelectionPressure = 0;

        for (generation = 1; generation <= maxGenerations; generation++) {
            comparisonFactor = comparisonFactorSchedule.getTemperature(generation - 1);
            successRatio = successRatioSchedule.getTemperature(generation - 1);

            if (evaluateEveryGeneration) {
                Solutions.updateFitness(population, problem);
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

            unsuccessfulPopulation = new ArrayList<>((int) (population.size() * (1 - successRatio)) + 1);
            successfulPopulation = createSuccessfulPopulation(unsuccessfulPopulation);

            actualSelectionPressure =
                (double) (successfulPopulation.size() + unsuccessfulPopulation.size()) / population.size();

            if (actualSelectionPressure >= maxSelectionPressure) {
                System.err.println("Max selection pressure reached.\n");
                break;
            }

            population = createNextPopulation();

            if (generation == maxGenerations) {
                System.err.println("Max generations reached.");
                break;
            }
        }

        Solutions.updateFitness(population, problem);
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

    protected abstract Collection<ISolution<T>> createSuccessfulPopulation(
        List<ISolution<T>> unsuccessfulPopulation
    );

    protected abstract List<ISolution<T>> createNextPopulation(
        Collection<ISolution<T>> successfulPopulation,
        List<ISolution<T>> unsuccessfulPopulation
    );

    protected List<ISolution<T>> createNextPopulation() {
        return createNextPopulation(successfulPopulation, unsuccessfulPopulation);
    }
}
