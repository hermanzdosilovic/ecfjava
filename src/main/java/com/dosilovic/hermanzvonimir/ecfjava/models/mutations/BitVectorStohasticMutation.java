package com.dosilovic.hermanzvonimir.ecfjava.models.mutations;

import com.dosilovic.hermanzvonimir.ecfjava.util.BitVector;
import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;

import java.util.Random;

public class BitVectorStohasticMutation<T extends BitVector> implements IMutation<T> {

    private double mutationProbability;
    private boolean forceMutation;
    private static final Random RAND = new Random();

    public BitVectorStohasticMutation(double mutationProbability, boolean forceMutation) {
        this.mutationProbability = mutationProbability;
        this.forceMutation = forceMutation;
    }

    @SuppressWarnings("unchecked") @Override public Solution<T> mutate(Solution<T> solution) {
        T anteMutant = solution.getRepresentative();
        T postMutant = (T) anteMutant.clone();

        boolean mutationHappend = false;
        int i;

        for (i = 0; i < postMutant.getSize(); i++) {
            if (RAND.nextDouble() <= mutationProbability) {
                mutationHappend = true;
                postMutant.flip(i);
            }
        }

        if (!mutationHappend && forceMutation) {
            i = RAND.nextInt(postMutant.getSize());
            postMutant.flip(i);
        }

        return new Solution<>(postMutant);
    }
}
