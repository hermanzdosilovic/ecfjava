package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.topologies.ITopology;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.particle.Particle;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.RealVector;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.IRandom;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.Random;

public class StochasticIWPSO<T extends Particle> extends AbstractBasicPSO<T> {

    private RealVector lowerStochasticBound;
    private RealVector upperStochasticBound;

    public StochasticIWPSO(
        int maxIterations,
        double desiredFitness,
        double desiredPrecision,
        boolean isFullyInformed,
        double individualFactor,
        double socialFactor,
        RealVector minSpeed,
        RealVector maxSpeed,
        RealVector lowerStochasticBound,
        RealVector upperStochasticBound,
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
            minSpeed,
            maxSpeed,
            problem,
            topology
        );
        this.lowerStochasticBound = lowerStochasticBound;
        this.upperStochasticBound = upperStochasticBound;
    }

    @Override
    protected void updateSpeed(int iteration, RealVector speed, RealVector neighboursContribution) {
        IRandom random = Random.getRandom();

        for (int i = 0; i < speed.getSize(); i++) {
            double lowerBound                     = lowerStochasticBound.getValue(i);
            double upperBound                     = upperStochasticBound.getValue(i);
            double boundWidth                     = Math.abs(upperBound - lowerBound);
            double componentSpeed                 = speed.getValue(i);
            double componentNeighbourContribution = neighboursContribution.getValue(i);

            double newSpeed =
                (lowerBound + random.nextDouble() * boundWidth) * componentSpeed + componentNeighbourContribution;

            speed.setValue(i, newSpeed);
        }
    }
}
