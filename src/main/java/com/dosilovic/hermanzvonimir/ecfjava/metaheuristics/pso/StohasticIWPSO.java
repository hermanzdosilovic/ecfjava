package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.topologies.ITopology;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.util.RealVector;

import java.util.Random;

public class StohasticIWPSO<T extends RealVector> extends AbstractBasicPSO<T> {

    private static final Random RAND = new Random();

    private RealVector lowerBound;
    private RealVector upperBound;

    public StohasticIWPSO(
        int maxIterations,
        double desiredFitness,
        double desiredPrecision,
        boolean isFullyInformed,
        double individualFactor,
        double socialFactor,
        RealVector minValue,
        RealVector maxValue,
        RealVector minSpeed,
        RealVector maxSpeed,
        RealVector lowerBound,
        RealVector upperBound,
        IProblem<T> problem,
        ITopology<T> topology
    ) {
        super(
            maxIterations,
            desiredFitness,
            desiredPrecision,
            isFullyInformed,
            individualFactor,
            socialFactor,
            minValue,
            maxValue,
            minSpeed,
            maxSpeed,
            problem,
            topology
        );

        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    @Override
    protected void updateSpeed(
        int iteration, RealVector speed, RealVector neighboursContribution
    ) {
        for (int i = 0; i < speed.getSize(); i++) {
            speed.setValue(i, (
                                  lowerBound.getValue(i) + RAND.nextDouble() *
                                                           (upperBound.getValue(i) - lowerBound.getValue(i))
                              ) *
                              speed.getValue(i) + neighboursContribution.getValue(i)
            );
        }
    }
}
