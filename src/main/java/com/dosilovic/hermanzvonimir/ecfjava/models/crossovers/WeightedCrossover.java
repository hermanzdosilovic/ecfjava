package com.dosilovic.hermanzvonimir.ecfjava.models.crossovers;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.IVector;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.IRandom;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.Random;

import java.util.ArrayList;
import java.util.List;

public class WeightedCrossover<T extends IVector> implements ICrossover<T> {

    public boolean useFitness;

    public WeightedCrossover(boolean useFitness) {
        this.useFitness = useFitness;
    }

    public WeightedCrossover() {
        this(true);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> cross(T mom, T dad) {
        IRandom random = Random.getRandom();

        T alice = (T) mom.copy();
        T bob   = (T) dad.copy();

        double momFitness = Math.abs(useFitness ? mom.getFitness() : mom.getPenalty());
        double dadFitness = Math.abs(useFitness ? dad.getFitness() : dad.getPenalty());
        double momWeight  = momFitness / (momFitness + dadFitness);

        for (int i = 0; i < alice.getSize(); i++) {
            if (random.nextDouble() < momWeight) {
                bob.setValue(i, mom.getValue(i));
            } else {
                alice.setValue(i, dad.getValue(i));
            }
        }

        List<T> children = new ArrayList<>();
        children.add(alice);
        children.add(bob);

        return children;
    }
}
