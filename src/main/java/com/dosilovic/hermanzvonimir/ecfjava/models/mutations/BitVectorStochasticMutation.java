package com.dosilovic.hermanzvonimir.ecfjava.models.mutations;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.BitVector;

public class BitVectorStochasticMutation<T extends BitVector> implements IMutation<T> {

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

    @SuppressWarnings("unchecked")
    @Override
    public ISolution<T> mutate(ISolution<T> child) {
        T childRepresentative  = child.getRepresentative();
        T mutantRepresentative = (T) childRepresentative.copy();

        boolean mutationHappened = false;
        for (int i = 0; i < mutantRepresentative.getSize(); i++) {
            if (RAND.nextDouble() < mutationProbability) {
                mutationHappened = true;
                mutantRepresentative.flip(i);
            }
        }

        if (!mutationHappened && forceMutation) {
            mutantRepresentative.flip(RAND.nextInt(mutantRepresentative.getSize()));
        }

        ISolution<T> mutant = child.copy();
        mutant.setRepresentative(mutantRepresentative);

        return mutant;
    }
}
