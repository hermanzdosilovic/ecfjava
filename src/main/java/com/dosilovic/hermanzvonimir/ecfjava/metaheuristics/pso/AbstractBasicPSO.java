package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.topologies.ITopology;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.particle.Particle;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.RealVector;

public abstract class AbstractBasicPSO<T extends Particle> extends AbstractPSO<T> {

    private double     individualFactor;
    private double     socialFactor;
    private RealVector minSpeed;
    private RealVector maxSpeed;

    public AbstractBasicPSO(
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
            problem,
            topology
        );
        this.individualFactor = individualFactor;
        this.socialFactor = socialFactor;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
    }

    @Override
    protected double getIndividualFactor(int iteration) {
        return individualFactor;
    }

    @Override
    protected double getSocialFactor(int iteration) {
        return socialFactor;
    }

    @Override
    protected void limitSpeed(int iteration, RealVector speed) {
        for (int i = 0; i < speed.getSize(); i++) {
            speed.setValue(i, Math.min(Math.max(speed.getValue(i), minSpeed.getValue(i)), maxSpeed.getValue(i)));
        }
    }
}
