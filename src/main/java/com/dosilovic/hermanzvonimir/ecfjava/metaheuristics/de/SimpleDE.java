package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.de;

import com.dosilovic.hermanzvonimir.ecfjava.models.crossovers.ICrossover;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.Solutions;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.RealVector;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SimpleDE<T extends RealVector> extends AbstractDE<T> {

    private double            scaleFactor;
    private Set<ISolution<T>> picked;

    private static final Random RAND = new Random();

    public SimpleDE(
        int maxGenerations,
        double desiredFitness,
        double desiredPrecision,
        double scaleFactor,
        IProblem<T> problem,
        ICrossover<T> crossover
    ) {
        super(
            maxGenerations,
            desiredFitness,
            desiredPrecision,
            problem,
            crossover
        );
        this.scaleFactor = scaleFactor;
        picked = new HashSet<>();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected ISolution<T> createTrialSolution(ISolution<T> individual) {
        picked.clear();
        picked.add(individual);

        ISolution<T> baseVectorSolution   = randomPick();
        ISolution<T> firstVectorSolution  = randomPick();
        ISolution<T> secondVectorSolution = randomPick();

        T baseVector   = baseVectorSolution.getRepresentative();
        T firstVector  = firstVectorSolution.getRepresentative();
        T secondVector = secondVectorSolution.getRepresentative();

        RealVector tmpVector = new RealVector(
            firstVector.subtract(secondVector).mapMultiply(scaleFactor).add(baseVector).toArray()
        );

        ISolution<T> mutantSolution = individual.copy();
        T            mutantVector   = (T) individual.getRepresentative().copy();

        mutantSolution.setRepresentative(mutantVector);

        for (int i = 0; i < tmpVector.getSize(); i++) {
            mutantVector.setValue(i, tmpVector.getValue(i));
        }

        Collection<ISolution<T>> children = crossover.cross(individual, mutantSolution);
        Solutions.updateFitness(children, problem);

        return Solutions.findBestByFitness(children);
    }

    private ISolution<T> randomPick() {
        ISolution<T> pick;
        while (true) {
            pick = population.get(RAND.nextInt(population.size()));
            if (!picked.contains(pick)) {
                picked.add(pick);
                return pick;
            }
        }
    }
}
