package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.ga;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.ICoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.models.crossovers.ICrossover;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.IMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.selections.ISelection;
import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;

import java.util.*;

public class SimpleOSGA<T> extends AbstractOSGA<T> {

    public SimpleOSGA(
        boolean useElitism,
        int maxGenerations,
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
    protected Collection<Solution<T>> createSuccessfulPopulation(
        Collection<Solution<T>> currentPopulation,
        Collection<Solution<T>> unsuccessfulPopulation,
        double comparisonFactor,
        double successRatio
    ) {
        Collection<Solution<T>> successfulPopulation = new HashSet<>();
        double                  fitnessLimit;

        while (successfulPopulation.size() < currentPopulation.size() * successRatio &&
               (successfulPopulation.size() + unsuccessfulPopulation.size()) <
               currentPopulation.size() * maxSelectionPressure
            ) {
            Solution<T> firstParent  = selection.select(currentPopulation);
            Solution<T> secondParent = selection.select(currentPopulation);

            Collection<Solution<T>> children        = crossover.cross(firstParent, secondParent);
            List<Solution<T>>       mutatedChildren = new ArrayList<>(children.size());

            for (Solution<T> child : children) {
                mutatedChildren.add(mutation.mutate(child));
            }

            Solution.evaluateFitness(mutatedChildren, problem);
            mutatedChildren.sort(null);

            fitnessLimit = Math.min(firstParent.getFitness(), secondParent.getFitness()) +
                           comparisonFactor * Math.abs(firstParent.getFitness() - secondParent.getFitness());

            for (int i = mutatedChildren.size() - 1; i >= 0; i--) {
                Solution<T> mutatedChild = mutatedChildren.get(i);
                if (mutatedChild.getFitness() > fitnessLimit && !successfulPopulation.contains(mutatedChild)) {
                    successfulPopulation.add(mutatedChild);
                } else {
                    unsuccessfulPopulation.add(mutatedChild);
                }

                if (successfulPopulation.size() >= currentPopulation.size() * successRatio ||
                    (successfulPopulation.size() + unsuccessfulPopulation.size()) >=
                    currentPopulation.size() * maxSelectionPressure
                    ) {
                    break;
                }
            }
        }

        return successfulPopulation;
    }

    @Override
    protected Collection<Solution<T>> createNextPopulation(
        Collection<Solution<T>> currentPopulation,
        Collection<Solution<T>> successfulPopulation,
        Collection<Solution<T>> unsuccessfulPopulation
    ) {
        Collection<Solution<T>> nextPopulation             = new ArrayList<>(successfulPopulation);
        List<Solution<T>>       unsuccessfulPopulationList = new ArrayList<>(unsuccessfulPopulation);
        unsuccessfulPopulationList.sort(null);

        if (useElitism) {
            nextPopulation.add(bestSolution);
            nextPopulation.add(Solution.findSecondBest(currentPopulation));
        }

        for (int i = unsuccessfulPopulationList.size() - 1;
             i >= 0 && nextPopulation.size() < currentPopulation.size();
             i--
            ) {
            nextPopulation.add(unsuccessfulPopulationList.get(i));
        }

        while (nextPopulation.size() < currentPopulation.size()) {
            Solution<T> firstParent  = selection.select(currentPopulation);
            Solution<T> secondParent = selection.select(currentPopulation);

            Collection<Solution<T>> children        = crossover.cross(firstParent, secondParent);
            List<Solution<T>>       mutatedChildren = new ArrayList<>(children.size());

            for (Solution<T> child : children) {
                mutatedChildren.add(mutation.mutate(child));
            }

            Solution.evaluateFitness(mutatedChildren, problem);
            mutatedChildren.sort(null);

            for (int i = mutatedChildren.size() - 1; i >= 0; i--) {
                nextPopulation.add(mutatedChildren.get(i));
                if (nextPopulation.size() >= currentPopulation.size()) {
                    break;
                }
            }
        }

        return nextPopulation;
    }
}
