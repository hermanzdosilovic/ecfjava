package com.dosilovic.hermanzvonimir.ecfjava.models.crossovers;

import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;
import org.apache.commons.math3.linear.RealVector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class BLXAlphaCrossover<T extends RealVector> implements ICrossover<T> {

    private double alpha;
    private static final Random RAND = new Random();

    public BLXAlphaCrossover(double alpha) {
        this.alpha = alpha;
    }

    @Override public Collection<Solution<T>> cross(Solution<T> firstParent, Solution<T> secondParent) {
        T firstRepresentative  = firstParent.getRepresentative();
        T secondRepresentative = secondParent.getRepresentative();

        T child = (T) firstRepresentative.copy();

        double cmin;
        double cmax;
        double I;

        for (int i = 0; i < child.getDimension(); i++) {
            cmin = Math.min(firstRepresentative.getEntry(i), secondRepresentative.getEntry(i));
            cmax = Math.max(firstRepresentative.getEntry(i), secondRepresentative.getEntry(i));
            I = cmax - cmin;
            child.setEntry(i, (cmin - I * alpha) + RAND.nextDouble() * I * (1 + 2 * alpha));
        }

        Collection<Solution<T>> children = new ArrayList<>();
        children.add(new Solution<>(child));

        return children;
    }
}
