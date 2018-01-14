package com.dosilovic.hermanzvonimir.ecfjava.models.selections;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

import java.util.Collection;

public class RouletteWheelSelection<T> implements ISelection<T> {

    private boolean useFitness;

    public RouletteWheelSelection(boolean useFitness) {
        this.useFitness = useFitness;
    }

    public RouletteWheelSelection() {
        this(true);
    }

    @Override
    public ISolution<T> select(Collection<ISolution<T>> population) {
        double fitnessSum = 0;
        for (ISolution<T> individual : population) {
            fitnessSum += Math.abs(useFitness ? individual.getFitness() : individual.getPenalty());
        }

        double       cumulativeFitness = 0;
        double       randomDouble      = RAND.nextDouble();
        ISolution<T> lastIndividual    = null;

        for (ISolution<T> individual : population) {
            lastIndividual = individual;
            cumulativeFitness += Math.abs(useFitness ? individual.getFitness() : individual.getPenalty());

            if (randomDouble < cumulativeFitness / fitnessSum) {
                return individual;
            }
        }

        return lastIndividual;
    }
}
