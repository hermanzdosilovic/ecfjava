package com.dosilovic.hermanzvonimir.ecfjava.models.selections;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.IRandom;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.Random;

import java.util.Collection;

public class UniformSelection<T extends ISolution> implements ISelection<T> {

    @Override
    public T select(Collection<T> population) {
        IRandom random            = Random.getRandom();
        double  selectProbability = 1.0 / population.size();

        T lastIndividual = null;
        for (T individual : population) {
            lastIndividual = individual;
            if (random.nextDouble() < selectProbability) {
                return individual;
            }
        }

        return lastIndividual;
    }
}
