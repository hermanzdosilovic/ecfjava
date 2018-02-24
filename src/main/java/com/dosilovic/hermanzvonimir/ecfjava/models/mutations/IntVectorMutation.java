package com.dosilovic.hermanzvonimir.ecfjava.models.mutations;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.IntVector;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.IRandom;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.Random;

public class IntVectorMutation<T extends IntVector> extends AbstractMutation<T> {

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

    @Override
    public T mutate(T child) {
        IRandom random = Random.getRandom();

        boolean mutationHappened = false;
        for (int i = 0; i < child.getSize(); i++) {
            if (random.nextDouble() < mutationProbability) {
                mutationHappened = true;
                child.setValue(
                    i,
                    child.getValue(i) + random.nextInt(minValue, maxValue + 1)
                );
            }
        }

        if (!mutationHappened && forceMutation) {
            int i = random.nextInt(child.getSize());
            child.setValue(
                i,
                child.getValue(i) + random.nextInt(minValue, maxValue + 1)
            );
        }

        return child;
    }
}
