package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.ga;

import com.dosilovic.hermanzvonimir.ecfjava.models.crossovers.ICrossover;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.IMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.selections.ISelection;
import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;

import java.util.ArrayList;
import java.util.Collection;

public class SimpleGA<T> extends AbstractGA<T> {

    public SimpleGA(
        boolean useElitism,
        int maxGenerations,
        double desiredFitness,
        double desiredPrecision,
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
            problem,
            selection,
            crossover,
            mutation
        );
    }

    @Override
    protected Collection<Solution<T>> createNextPopulation(
        Collection<Solution<T>> currentPopulation,
        Solution<T> bestSolution
    ) {
        Collection<Solution<T>> nextPopulation = new ArrayList<>(currentPopulation.size());

        if (useElitism) {
            nextPopulation.add(bestSolution);
            nextPopulation.add(Solution.findSecondBest(currentPopulation));
        }

        while (nextPopulation.size() < currentPopulation.size()) {
            Solution<T> firstParent  = selection.select(currentPopulation);
            Solution<T> secondParent = selection.select(currentPopulation);

            Collection<Solution<T>> children        = crossover.cross(firstParent, secondParent);
            Collection<Solution<T>> mutatedChildren = new ArrayList<>(children.size());

            for (Solution<T> child : children) {
                mutatedChildren.add(mutation.mutate(child));
            }

            Solution.evaluateFitness(mutatedChildren, problem, true);
            nextPopulation.add(Solution.findBest(mutatedChildren));
        }

        return nextPopulation;
    }
}
