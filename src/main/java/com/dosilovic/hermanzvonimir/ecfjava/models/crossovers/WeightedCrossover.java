package com.dosilovic.hermanzvonimir.ecfjava.models.crossovers;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.IVector;

import java.util.ArrayList;
import java.util.Collection;

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
    public Collection<ISolution<T>> cross(ISolution<T> mom, ISolution<T> dad) {
        T momRepresentative = mom.getRepresentative();
        T dadRepresentative = dad.getRepresentative();

        ISolution<T> alice               = mom.copy();
        ISolution<T> bob                 = dad.copy();
        T            aliceRepresentative = (T) momRepresentative.copy();
        T            bobRepresentative   = (T) dadRepresentative.copy();

        alice.setRepresentative(aliceRepresentative);
        bob.setRepresentative(bobRepresentative);

        double momFitness = Math.abs(useFitness ? mom.getFitness() : mom.getPenalty());
        double dadFitness = Math.abs(useFitness ? dad.getFitness() : dad.getPenalty());
        double fitnessSum = momFitness + dadFitness;

        for (int i = 0; i < momRepresentative.getSize(); i++) {
            if (RAND.nextDouble() < momFitness / fitnessSum) {
                bobRepresentative.setValue(i, momRepresentative.getValue(i));
            } else {
                aliceRepresentative.setValue(i, dadRepresentative.getValue(i));
            }
        }

        Collection<ISolution<T>> children = new ArrayList<>();
        children.add(alice);
        children.add(bob);

        return children;
    }
}
