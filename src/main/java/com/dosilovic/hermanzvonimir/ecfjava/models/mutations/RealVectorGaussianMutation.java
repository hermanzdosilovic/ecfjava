package com.dosilovic.hermanzvonimir.ecfjava.models.mutations;

import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;
import org.apache.commons.math3.linear.RealVector;

import java.util.Random;

public class RealVectorGaussianMutation<T extends RealVector> implements IMutation<T> {

    private double   mutationProbability;
    private double[] sigma;
    private boolean  forceMutation;
    private static final Random RAND = new Random();

    public RealVectorGaussianMutation(double mutationProbability, boolean forceMutation, double... sigma) {
        this.mutationProbability = mutationProbability;
        this.sigma = sigma;
        this.forceMutation = forceMutation;
    }

    @Override
    public Solution<T> mutate(Solution<T> individual) {
        T anteMutant = individual.getRepresentative();
        T postMutant = (T) anteMutant.copy();

        boolean mutationHappened = false;
        int     i;
        double  newValue;

        for (i = 0; i < anteMutant.getDimension(); i++) {
            newValue = postMutant.getEntry(i) + RAND.nextGaussian() * sigma[Math.min(i, sigma.length - 1)];
            if (RAND.nextDouble() <= mutationProbability) {
                mutationHappened = true;
                postMutant.setEntry(i, newValue);
            }
        }

        if (!mutationHappened && forceMutation) {
            i = RAND.nextInt(postMutant.getDimension());
            newValue = postMutant.getEntry(i) + RAND.nextGaussian() * sigma[Math.min(i, sigma.length - 1)];
            postMutant.setEntry(i, newValue);
        }

        return new Solution<>(postMutant);
    }
}
