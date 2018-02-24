package com.dosilovic.hermanzvonimir.ecfjava.models.selections;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.IRandom;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.Random;

import java.util.Collection;

public class RouletteWheelSelection<T extends ISolution> implements ISelection<T> {

    private boolean useFitness;

    public RouletteWheelSelection(boolean useFitness) {
        this.useFitness = useFitness;
    }

    public RouletteWheelSelection() {
        this(true);
    }

    @Override
    public T select(Collection<T> population) {
        IRandom random = Random.getRandom();

        double fitnessSum = 0;
        for (T individual : population) {
            fitnessSum += Math.abs(useFitness ? individual.getFitness() : individual.getPenalty());
        }

        double cumulativeFitness = 0;
        double randomDouble      = random.nextDouble();
        T      lastIndividual    = null;

        for (T individual : population) {
            lastIndividual = individual;
            cumulativeFitness += Math.abs(useFitness ? individual.getFitness() : individual.getPenalty());

            if (randomDouble < cumulativeFitness / fitnessSum) {
                return individual;
            }
        }

        return lastIndividual;
    }
}
