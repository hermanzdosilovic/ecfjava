package com.dosilovic.hermanzvonimir.ecfjava.models.crossovers;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.IVector;

import java.util.ArrayList;
import java.util.Collection;

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
    public Collection<ISolution<T>> cross(ISolution<T> mom, ISolution<T> dad) {
        T momRepresentative = mom.getRepresentative();
        T dadRepresentative = dad.getRepresentative();

        ISolution<T> alice               = mom.copy();
        ISolution<T> bob                 = dad.copy();
        T            aliceRepresentative = (T) momRepresentative.copy();
        T            bobRepresentative   = (T) dadRepresentative.copy();

        alice.setRepresentative(aliceRepresentative);
        bob.setRepresentative(bobRepresentative);

        for (int i = 0; i < momRepresentative.getSize(); i++) {
            if (RAND.nextDouble() < crossoverProbability) {
                aliceRepresentative.setValue(i, dadRepresentative.getValue(i));
                bobRepresentative.setValue(i, momRepresentative.getValue(i));
            }
        }

        Collection<ISolution<T>> children = new ArrayList<>();
        children.add(alice);
        children.add(bob);

        return children;
    }
}
