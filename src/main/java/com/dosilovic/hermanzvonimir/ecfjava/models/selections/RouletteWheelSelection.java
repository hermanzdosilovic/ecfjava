package com.dosilovic.hermanzvonimir.ecfjava.models.selections;

import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;

import java.util.Collection;
import java.util.Random;

public class RouletteWheelSelection<T> implements ISelection<T> {

    private boolean useFitness;
    private static final Random RAND = new Random();

    public RouletteWheelSelection(boolean useFitness) {
        this.useFitness = useFitness;
    }

    public RouletteWheelSelection() {
        this(true);
    }

    @Override
    public Solution<T> select(Collection<Solution<T>> population) {
        double totalFitness = 0;
        for (Solution<T> individual : population) {
            totalFitness += useFitness ? individual.getFitness() : individual.getPenalty();
        }

        double cumulativeFitness = 0;
        double randomDouble      = RAND.nextDouble();

        Solution<T> lastIndividual = null;
        for (Solution<T> individual : population) {
            lastIndividual = individual;
            cumulativeFitness += useFitness ? individual.getFitness() : individual.getPenalty();
            if (randomDouble <= Math.abs(cumulativeFitness / totalFitness)) {
                return individual;
            }
        }

        return lastIndividual;
    }
}
