package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.de;

import com.dosilovic.hermanzvonimir.ecfjava.models.crossovers.ICrossover;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.util.RealVector;
import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;

import java.util.*;

public class SimpleDE<T extends RealVector> extends AbstractDE<T> {

    private double scaleFactor;

    private static final Random RAND = new Random();

    private Set<Solution<T>> picked;

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
    protected Solution<T> createTrialSolution(
        Solution<T> individual, List<Solution<T>> currentPopulation
    ) {
        picked.clear();
        picked.add(individual);

        Solution<T> baseVectorSolution   = randomPick(currentPopulation);
        Solution<T> firstVectorSolution  = randomPick(currentPopulation);
        Solution<T> secondVectorSolution = randomPick(currentPopulation);

        T baseVector   = baseVectorSolution.getRepresentative();
        T firstVector  = firstVectorSolution.getRepresentative();
        T secondVector = secondVectorSolution.getRepresentative();

        RealVector tmpVector =
            new RealVector(firstVector.subtract(secondVector).mapMultiply(scaleFactor).add(baseVector).toArray());

        T           mutantVector   = (T) individual.getRepresentative().clone();
        Solution<T> mutantSolution = new Solution<>(mutantVector);
        for (int i = 0; i < tmpVector.getSize(); i++) {
            mutantVector.setValue(i, tmpVector.getValue(i));
        }

        Collection<Solution<T>> children = crossover.cross(individual, mutantSolution);
        Solution.evaluateFitness(children, problem);

        return Solution.findBestByFitness(children);
    }

    private Solution<T> randomPick(List<Solution<T>> population) {
        Solution<T> pick;
        while (true) {
            pick = population.get(RAND.nextInt(population.size()));
            if (!picked.contains(pick)) {
                picked.add(pick);
                return pick;
            }
        }
    }
}
