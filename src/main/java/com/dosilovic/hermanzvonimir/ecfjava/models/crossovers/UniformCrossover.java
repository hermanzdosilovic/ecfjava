package com.dosilovic.hermanzvonimir.ecfjava.models.crossovers;

import com.dosilovic.hermanzvonimir.ecfjava.util.IVector;
import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class UniformCrossover<T extends IVector> implements ICrossover<T> {

    private static final Random RAND = new Random();

    @SuppressWarnings("unchecked")
    @Override
    public Collection<Solution<T>> cross(Solution<T> firstParent, Solution<T> secondParent) {
        T firstParentRepresentative  = firstParent.getRepresentative();
        T secondParentRepresentative = secondParent.getRepresentative();

        Collection<Solution<T>> children    = new ArrayList<>();
        T                       firstChild  = (T) firstParentRepresentative.clone();
        T                       secondChild = (T) secondParentRepresentative.clone();

        children.add(new Solution<>(firstChild));
        children.add(new Solution<>(secondChild));

        for (int i = 0; i < firstParentRepresentative.getSize(); i++) {
            if (RAND.nextDouble() <= 0.5) {
                firstChild.setValue(i, secondParentRepresentative.getValue(i));
                secondChild.setValue(i, firstParentRepresentative.getValue(i));
            }
        }

        return children;
    }
}
