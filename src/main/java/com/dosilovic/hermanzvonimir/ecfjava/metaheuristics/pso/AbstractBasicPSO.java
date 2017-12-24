package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.topologies.ITopology;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.util.RealVector;

public abstract class AbstractBasicPSO<T extends RealVector> extends AbstractPSO<T> {

    private RealVector minSpeed;
    private RealVector maxSpeed;

    public AbstractBasicPSO(
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
            problem,
            topology
        );

        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
    }

    @Override
    protected double limitSpeed(int component, double speed) {
        return Math.min(Math.max(speed, minSpeed.getValue(component)), maxSpeed.getValue(component));
    }
}
