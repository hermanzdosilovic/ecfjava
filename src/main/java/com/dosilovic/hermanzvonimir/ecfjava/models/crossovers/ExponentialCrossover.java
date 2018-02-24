package com.dosilovic.hermanzvonimir.ecfjava.models.crossovers;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.IVector;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.IRandom;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.Random;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ExponentialCrossover<T extends IVector> implements ICrossover<T> {

    private double crossoverProbability;

    public ExponentialCrossover(double crossoverProbability) {
        this.crossoverProbability = crossoverProbability;
    }

    public ExponentialCrossover() {
        this(0.5);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> cross(T mom, T dad) {
        IRandom random = Random.getRandom();

        T alice = (T) mom.copy();
        T bob   = (T) dad.copy();

        int start = random.nextInt(dad.getSize());
        alice.setValue(start, dad.getValue(start));
        bob.setValue(start, mom.getValue(start));

        for (int i = start + 1; i < mom.getSize(); i++) {
            if (random.nextDouble() < crossoverProbability) {
                alice.setValue(i, dad.getValue(i));
                bob.setValue(i, mom.getValue(i));
            } else {
                break;
            }
        }

        List<T> children = new ArrayList<>();
        children.add(alice);
        children.add(bob);

        return children;
    }
}
