package com.dosilovic.hermanzvonimir.ecfjava.models.crossovers;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.IVector;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.IRandom;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.Random;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UniformCrossover<T extends IVector> implements ICrossover<T> {

    private double crossoverProbability;

    public UniformCrossover(double crossoverProbability) {
        this.crossoverProbability = crossoverProbability;
    }

    public UniformCrossover() {
        this(0.5);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> cross(T mom, T dad) {
        IRandom random = Random.getRandom();

        T alice = (T) mom.copy();
        T bob   = (T) dad.copy();

        for (int i = 0; i < alice.getSize(); i++) {
            if (random.nextDouble() < crossoverProbability) {
                alice.setValue(i, dad.getValue(i));
                bob.setValue(i, mom.getValue(i));
            }
        }

        List<T> children = new ArrayList<>();
        children.add(alice);
        children.add(bob);

        return children;
    }
}
