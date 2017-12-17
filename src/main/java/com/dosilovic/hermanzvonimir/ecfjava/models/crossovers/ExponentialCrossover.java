package com.dosilovic.hermanzvonimir.ecfjava.models.crossovers;

import com.dosilovic.hermanzvonimir.ecfjava.util.IVector;
import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class ExponentialCrossover<T extends IVector> implements ICrossover<T> {

    private double crossoverProbability = 0.5;

    private static final Random RAND = new Random();

    public ExponentialCrossover(double crossoverProbability) {
        this.crossoverProbability = crossoverProbability;
    }

    public ExponentialCrossover() {}

    @Override
    public Collection<Solution<T>> cross(
        Solution<T> firstParent, Solution<T> secondParent
    ) {
        T firstParentRepresentative  = firstParent.getRepresentative();
        T secondParentRepresentative = secondParent.getRepresentative();

        Collection<Solution<T>> children    = new ArrayList<>();
        T                       firstChild  = (T) firstParentRepresentative.clone();
        T                       secondChild = (T) secondParentRepresentative.clone();

        children.add(new Solution<>(firstChild));
        children.add(new Solution<>(secondChild));

        int start = RAND.nextInt(firstParentRepresentative.getSize());
        firstChild.setValue(start, secondParentRepresentative.getValue(start));
        secondChild.setValue(start, firstParentRepresentative.getValue(start));

        for (int i = start + 1; i < firstParentRepresentative.getSize(); i++) {
            if (RAND.nextDouble() <= crossoverProbability) {
                firstChild.setValue(i, secondParentRepresentative.getValue(i));
                secondChild.setValue(i, firstParentRepresentative.getValue(i));
            } else {
                break;
            }
        }

        return children;
    }
}
