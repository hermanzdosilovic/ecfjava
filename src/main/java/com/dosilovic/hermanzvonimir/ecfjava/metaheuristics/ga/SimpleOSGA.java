package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.ga;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.ICoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.models.crossovers.ICrossover;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.IMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.selections.ISelection;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.Solutions;

import java.util.*;

public class SimpleOSGA<T> extends AbstractOSGA<T> {

    public SimpleOSGA(
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
            maxSelectionPressure,
            successRatioSchedule,
            comparisonFactorSchedule,
            problem,
            selection,
            crossover,
            mutation
        );
    }

    @Override
    protected Collection<ISolution<T>> createSuccessfulPopulation(
        List<ISolution<T>> unsuccessfulPopulation
    ) {
        Set<ISolution<T>> successfulPopulation = new HashSet<>();

        while (successfulPopulation.size() < population.size() * successRatio &&
               (successfulPopulation.size() + unsuccessfulPopulation.size()) < population.size() * maxSelectionPressure
            ) {
            ISolution<T> mom = selection.select(population);
            ISolution<T> dad = selection.select(population);

            Collection<ISolution<T>> children = crossover.cross(mom, dad);

            List<ISolution<T>> mutatedChildren = new ArrayList<>(children.size());
            for (ISolution<T> child : children) {
                mutatedChildren.add(mutation.mutate(child));
            }

            Solutions.updateFitness(mutatedChildren, problem);
            mutatedChildren.sort(null);

            double fitnessLimit = Math.min(mom.getFitness(), dad.getFitness()) +
                                  comparisonFactor * Math.abs(mom.getFitness() - dad.getFitness());

            for (int i = mutatedChildren.size() - 1; i >= 0; i--) {
                ISolution<T> mutatedChild = mutatedChildren.get(i);
                if (mutatedChild.getFitness() > fitnessLimit && !successfulPopulation.contains(mutatedChild)) {
                    successfulPopulation.add(mutatedChild);
                } else {
                    unsuccessfulPopulation.add(mutatedChild);
                }

                if (successfulPopulation.size() >= population.size() * successRatio ||
                    (successfulPopulation.size() + unsuccessfulPopulation.size()) >=
                    population.size() * maxSelectionPressure
                    ) {
                    break;
                }
            }
        }

        return successfulPopulation;
    }

    @Override
    protected List<ISolution<T>> createNextPopulation(
        Collection<ISolution<T>> successfulPopulation,
        List<ISolution<T>> unsuccessfulPopulation
    ) {
        List<ISolution<T>> nextPopulation = new ArrayList<>(successfulPopulation);

        unsuccessfulPopulation.sort(null);

        if (useElitism) {
            nextPopulation.add(bestSolution);
            nextPopulation.add(Solutions.findSecondBestByFitness(population));
        }

        for (int i = unsuccessfulPopulation.size() - 1; i >= 0; i--) {
            if (nextPopulation.size() >= population.size()) {
                break;
            }
            nextPopulation.add(unsuccessfulPopulation.get(i));
        }

        while (nextPopulation.size() < population.size()) {
            ISolution<T> mom = selection.select(population);
            ISolution<T> dad = selection.select(population);

            Collection<ISolution<T>> children = crossover.cross(mom, dad);

            List<ISolution<T>> mutatedChildren = new ArrayList<>(children.size());
            for (ISolution<T> child : children) {
                mutatedChildren.add(mutation.mutate(child));
            }

            Solutions.updateFitness(mutatedChildren, problem);
            mutatedChildren.sort(null);

            for (int i = mutatedChildren.size() - 1; i >= 0; i--) {
                if (nextPopulation.size() >= population.size()) {
                    break;
                }
                nextPopulation.add(mutatedChildren.get(i));
            }
        }

        return nextPopulation;
    }
}
