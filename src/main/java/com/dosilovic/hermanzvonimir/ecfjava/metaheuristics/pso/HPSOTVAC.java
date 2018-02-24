package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.topologies.ITopology;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.ICoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.particle.Particle;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.RealVector;

public class HPSOTVAC<T extends Particle> extends AbstractPSO<T> {

    private ICoolingSchedule individualFactorSchedule;
    private ICoolingSchedule socialFactorSchedule;
    private ICoolingSchedule minSpeedSchedule;
    private ICoolingSchedule maxSpeedSchedule;

    public HPSOTVAC(
        int maxIterations,
        double desiredFitness,
        double desiredPrecision,
        boolean isFullyInformed,
        ICoolingSchedule individualFactorSchedule,
        ICoolingSchedule socialFactorSchedule,
        ICoolingSchedule minSpeedSchedule,
        ICoolingSchedule maxSpeedSchedule,
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

        this.individualFactorSchedule = individualFactorSchedule;
        this.socialFactorSchedule = socialFactorSchedule;
        this.minSpeedSchedule = minSpeedSchedule;
        this.maxSpeedSchedule = maxSpeedSchedule;
    }

    @Override
    protected double getIndividualFactor(int iteration) {
        return individualFactorSchedule.getTemperature(iteration);
    }

    @Override
    protected double getSocialFactor(int iteration) {
        return socialFactorSchedule.getTemperature(iteration);
    }

    @Override
    protected void updateSpeed(int iteration, RealVector speed, RealVector neighboursContribution) {
        for (int i = 0; i < speed.getSize(); i++) {
            speed.setValue(i, speed.getValue(i) + neighboursContribution.getValue(i));
        }
    }

    @Override
    protected void limitSpeed(int iteration, RealVector speed) {
        double minSpeed = minSpeedSchedule.getTemperature(iteration);
        double maxSpeed = maxSpeedSchedule.getTemperature(iteration);
        for (int i = 0; i < speed.getSize(); i++) {
            speed.setValue(i, Math.min(Math.max(speed.getValue(i), minSpeed), maxSpeed));
        }
    }
}
