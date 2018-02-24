package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.topologies.ITopology;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.particle.Particle;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.RealVector;

public class BasicPSO<T extends Particle> extends AbstractBasicPSO<T> {

    public BasicPSO(
        int maxIterations,
        double desiredFitness,
        double desiredPrecision,
        boolean isFullyInformed,
        double individualFactor,
        double socialFactor,
        RealVector minSpeed,
        RealVector maxSpeed,
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
    }

    @Override
    protected void updateSpeed(int iteration, RealVector speed, RealVector neighboursContribution) {
        for (int i = 0; i < speed.getSize(); i++) {
            speed.setValue(i, speed.getValue(i) + neighboursContribution.getValue(i));
        }
    }
}
