package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.ga;

import com.dosilovic.hermanzvonimir.ecfjava.models.crossovers.ICrossover;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.IMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.selections.ISelection;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.util.Solutions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SimpleGA<T extends ISolution> extends AbstractGA<T> {

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
    protected void createNextPopulation(List<T> nextPopulation) {
        if (useElitism) {
            nextPopulation.set(0, bestSolution);
            nextPopulation.set(1, Solutions.findSecondBestByFitness(population));
        }

        for (int i = (useElitism ? 2 : 0); i < population.size(); i++) {
            List<T> children = mutation.mutate(
                crossover.cross(
                    selection.select(population),
                    selection.select(population)
                )
            );

            if (!evaluateEveryGeneration) {
                problem.updateFitness(children);
                nextPopulation.set(i, Solutions.findBestByFitness(children));
            } else {
                nextPopulation.set(i, children.get(0));
            }
        }
    }
}
