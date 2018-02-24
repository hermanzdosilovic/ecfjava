package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.ga;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.ICoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.models.crossovers.ICrossover;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.IMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.selections.ISelection;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.util.Solutions;

import java.util.*;

public class SimpleOSGA<T extends ISolution> extends AbstractOSGA<T> {

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
    protected void createSuccessfulPopulation(
        Set<T> successfulPopulation,
        List<T> unsuccessfulPopulation
    ) {
        while (successfulPopulation.size() < population.size() * successRatio &&
               (successfulPopulation.size() + unsuccessfulPopulation.size()) < population.size() * maxSelectionPressure
            ) {
            T mom = selection.select(population);
            T dad = selection.select(population);

            List<T> children = mutation.mutate(crossover.cross(mom, dad));

            problem.updateFitness(children);
            children.sort(Solutions.FITNESS_COMPARATOR);

            double fitnessLimit = Math.min(mom.getFitness(), dad.getFitness()) +
                                  comparisonFactor * Math.abs(mom.getFitness() - dad.getFitness());

            for (int i = children.size() - 1; i >= 0; i--) {
                T child = children.get(i);
                if (child.getFitness() > fitnessLimit && !successfulPopulation.contains(child)) {
                    successfulPopulation.add(child);
                } else {
                    unsuccessfulPopulation.add(child);
                }

                if (successfulPopulation.size() >= population.size() * successRatio ||
                    (successfulPopulation.size() + unsuccessfulPopulation.size()) >=
                    population.size() * maxSelectionPressure
                    ) {
                    break;
                }
            }
        }
    }

    @Override
    protected void createNextPopulation(
        List<T> nextPopulation,
        Set<T> successfulPopulation,
        List<T> unsuccessfulPopulation
    ) {
        int nextPopulationSize = 0;

        if (useElitism) {
            nextPopulation.set(0, bestSolution);
            nextPopulation.set(1, Solutions.findSecondBestByFitness(population));
            nextPopulationSize = 2;
        }

        unsuccessfulPopulation.sort(Solutions.FITNESS_COMPARATOR);
        for (int i = unsuccessfulPopulation.size() - 1; i >= 0; i--) {
            if (nextPopulationSize >= population.size()) {
                break;
            }
            nextPopulation.set(nextPopulationSize++, unsuccessfulPopulation.get(i));
        }

        while (nextPopulationSize < population.size()) {
            List<T> children = mutation.mutate(
                crossover.cross(
                    selection.select(population),
                    selection.select(population)
                )
            );

            problem.updateFitness(children);
            children.sort(Solutions.FITNESS_COMPARATOR);

            for (int i = children.size() - 1; i >= 0; i--) {
                if (nextPopulationSize >= population.size()) {
                    break;
                }
                nextPopulation.set(nextPopulationSize++, children.get(i));
            }
        }
    }
}
