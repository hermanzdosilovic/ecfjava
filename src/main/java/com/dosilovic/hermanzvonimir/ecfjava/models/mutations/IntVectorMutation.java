package com.dosilovic.hermanzvonimir.ecfjava.models.mutations;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.IntVector;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.IRandom;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.Random;

public class IntVectorMutation<T extends IntVector> implements IMutation<T> {

    private int     minValue;
    private int     maxValue;
    private double  mutationProbability;
    private boolean forceMutation;

    public IntVectorMutation(int minValue, int maxValue, double mutationProbability, boolean forceMutation) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.mutationProbability = mutationProbability;
        this.forceMutation = forceMutation;
    }

    public IntVectorMutation(double mutationProbability, boolean forceMutation) {
        this(Integer.MIN_VALUE, Integer.MAX_VALUE, mutationProbability, forceMutation);
    }

    public IntVectorMutation(double mutationProbability) {
        this(mutationProbability, true);
    }

    public IntVectorMutation() {
        this(0.5);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ISolution<T> mutate(ISolution<T> child) {
        IRandom random = Random.getRandom();

        T childRepresentative  = child.getRepresentative();
        T mutantRepresentative = (T) childRepresentative.copy();

        boolean mutationHappened = false;
        for (int i = 0; i < mutantRepresentative.getSize(); i++) {
            if (random.nextDouble() < mutationProbability) {
                mutationHappened = true;
                mutantRepresentative.setValue(
                    i,
                    mutantRepresentative.getValue(i) + minValue + random.nextInt(maxValue - minValue)
                );
            }
        }

        if (!mutationHappened && forceMutation) {
            int i = random.nextInt(mutantRepresentative.getSize());
            mutantRepresentative.setValue(
                i,
                mutantRepresentative.getValue(i) + minValue + random.nextInt(maxValue - minValue)
            );
        }

        ISolution<T> mutant = child.copy();
        mutant.setRepresentative(mutantRepresentative);

        return mutant;
    }
}