package com.dosilovic.hermanzvonimir.ecfjava.models.crossovers;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.RealVector;

import java.util.ArrayList;
import java.util.Collection;

public class BLXAlphaCrossover<T extends RealVector> implements ICrossover<T> {

    private double alpha;

    public BLXAlphaCrossover(double alpha) {
        this.alpha = alpha;
    }

    public BLXAlphaCrossover() {
        this(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<ISolution<T>> cross(ISolution<T> mom, ISolution<T> dad) {
        T momRepresentative = mom.getRepresentative();
        T dadRepresentative = dad.getRepresentative();

        ISolution<T> child               = dad.copy();
        T            childRepresentative = (T) dadRepresentative.copy();

        child.setRepresentative(childRepresentative);

        double cmin, cmax, I;
        for (int i = 0; i < childRepresentative.getSize(); i++) {
            cmin = Math.min(momRepresentative.getValue(i), dadRepresentative.getValue(i));
            cmax = Math.max(momRepresentative.getValue(i), dadRepresentative.getValue(i));
            I = cmax - cmin;
            childRepresentative.setEntry(i, (cmin - I * alpha) + RAND.nextDouble() * I * (1 + 2 * alpha));
        }

        Collection<ISolution<T>> children = new ArrayList<>();
        children.add(child);

        return children;
    }
}
