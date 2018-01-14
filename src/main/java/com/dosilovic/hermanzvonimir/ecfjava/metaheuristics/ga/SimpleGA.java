package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.ga;

import com.dosilovic.hermanzvonimir.ecfjava.models.crossovers.ICrossover;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.IMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.selections.ISelection;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.Solutions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SimpleGA<T> extends AbstractGA<T> {

    public SimpleGA(
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
    }

    @Override
    protected List<ISolution<T>> createNextPopulation() {
        List<ISolution<T>> nextPopulation = new ArrayList<>(population.size());

        if (useElitism) {
            nextPopulation.add(bestSolution);
            nextPopulation.add(Solutions.findSecondBestByFitness(population));
        }

        while (nextPopulation.size() < population.size()) {
            ISolution<T> mom = selection.select(population);
            ISolution<T> dad = selection.select(population);

            Collection<ISolution<T>> children = crossover.cross(mom, dad);

            Collection<ISolution<T>> mutatedChildren = new ArrayList<>(children.size());
            for (ISolution<T> child : children) {
                mutatedChildren.add(mutation.mutate(child));
            }

            Solutions.updateFitness(mutatedChildren, problem);
            nextPopulation.add(Solutions.findBestByFitness(mutatedChildren));
        }

        return nextPopulation;
    }
}
