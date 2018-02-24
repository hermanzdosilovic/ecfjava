package com.dosilovic.hermanzvonimir.ecfjava.models.mutations;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.RealVector;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.IRandom;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.Random;

public class RealVectorGaussianMutation<T extends RealVector> extends AbstractMutation<T> {

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

    @Override
    public T mutate(T child) {
        IRandom random = Random.getRandom();

        boolean mutationHappened = false;
        for (int i = 0; i < child.getSize(); i++) {
            if (random.nextDouble() < mutationProbability) {
                mutationHappened = true;
                child.setValue(
                    i,
                    child.getValue(i) + random.nextGaussian() * sigma[Math.min(i, sigma.length - 1)]
                );
            }
        }

        if (!mutationHappened && forceMutation) {
            int i = random.nextInt(child.getSize());
            child.setValue(
                i,
                child.getValue(i) + random.nextGaussian() * sigma[Math.min(i, sigma.length - 1)]
            );
        }

        return child;
    }
}
