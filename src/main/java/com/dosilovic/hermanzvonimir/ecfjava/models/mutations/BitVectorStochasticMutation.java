package com.dosilovic.hermanzvonimir.ecfjava.models.mutations;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.BitVector;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.IRandom;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.Random;

public class BitVectorStochasticMutation<T extends BitVector> extends AbstractMutation<T> {

    private double  mutationProbability;
    private boolean forceMutation;

    public BitVectorStochasticMutation(double mutationProbability, boolean forceMutation) {
        this.mutationProbability = mutationProbability;
        this.forceMutation = forceMutation;
    }

    public BitVectorStochasticMutation(double mutationProbability) {
        this(mutationProbability, true);
    }

    public BitVectorStochasticMutation() {
        this(0.5);
    }

    @Override
    public T mutate(T child) {
        IRandom random = Random.getRandom();

        boolean mutationHappened = false;
        for (int i = 0; i < child.getSize(); i++) {
            if (random.nextDouble() < mutationProbability) {
                mutationHappened = true;
                child.flip(i);
            }
        }

        if (!mutationHappened && forceMutation) {
            child.flip(random.nextInt(child.getSize()));
        }

        return child;
    }
}
