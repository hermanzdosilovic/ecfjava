package com.dosilovic.hermanzvonimir.ecfjava.models.mutations;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.RealVector;

public class RealVectorGaussianMutation<T extends RealVector> implements IMutation<T> {

    private double   mutationProbability;
    private double[] sigma;
    private boolean  forceMutation;

    public RealVectorGaussianMutation(double mutationProbability, boolean forceMutation, double... sigma) {
        this.mutationProbability = mutationProbability;
        this.sigma = sigma;
        this.forceMutation = forceMutation;
    }

    public RealVectorGaussianMutation(double mutationProbability, boolean forceMutation) {
        this(mutationProbability, forceMutation, 1);
    }

    public RealVectorGaussianMutation(double mutationProbability) {
        this(mutationProbability, true);
    }

    public RealVectorGaussianMutation() {
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
                mutantRepresentative.setValue(
                    i,
                    mutantRepresentative.getValue(i) + RAND.nextGaussian() * sigma[Math.min(i, sigma.length - 1)]
                );
            }
        }

        if (!mutationHappened && forceMutation) {
            int i = RAND.nextInt(mutantRepresentative.getDimension());
            mutantRepresentative.setValue(
                i,
                mutantRepresentative.getValue(i) + RAND.nextGaussian() * sigma[Math.min(i, sigma.length - 1)]
            );
        }

        ISolution<T> mutant = child.copy();
        mutant.setRepresentative(mutantRepresentative);

        return mutant;
    }
}
