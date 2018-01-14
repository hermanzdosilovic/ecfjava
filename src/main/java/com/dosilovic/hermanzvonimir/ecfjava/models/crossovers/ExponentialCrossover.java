package com.dosilovic.hermanzvonimir.ecfjava.models.crossovers;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.IVector;

import java.util.ArrayList;
import java.util.Collection;

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
    public Collection<ISolution<T>> cross(ISolution<T> mom, ISolution<T> dad) {
        T momRepresentative = mom.getRepresentative();
        T dadRepresentative = dad.getRepresentative();

        ISolution<T> alice               = mom.copy();
        ISolution<T> bob                 = dad.copy();
        T            aliceRepresentative = (T) momRepresentative.copy();
        T            bobRepresentative   = (T) dadRepresentative.copy();

        alice.setRepresentative(aliceRepresentative);
        bob.setRepresentative(bobRepresentative);

        int start = RAND.nextInt(dadRepresentative.getSize());
        aliceRepresentative.setValue(start, dadRepresentative.getValue(start));
        bobRepresentative.setValue(start, momRepresentative.getValue(start));

        for (int i = start + 1; i < momRepresentative.getSize(); i++) {
            if (RAND.nextDouble() < crossoverProbability) {
                aliceRepresentative.setValue(i, dadRepresentative.getValue(i));
                bobRepresentative.setValue(i, momRepresentative.getValue(i));
            } else {
                break;
            }
        }

        Collection<ISolution<T>> children = new ArrayList<>();
        children.add(alice);
        children.add(bob);

        return children;
    }
}
