package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.topologies.ITopology;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.ICoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.util.RealVector;

public class HPSOTVAC<T extends RealVector> extends AbstractPSO<T> {

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
        RealVector minValue,
        RealVector maxValue,
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
            minValue,
            maxValue,
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
    protected void updateSpeed(
        int iteration, RealVector speed, RealVector neighboursContribution
    ) {
        for (int i = 0; i < speed.getSize(); i++) {
            speed.setValue(i, speed.getValue(i) + neighboursContribution.getValue(i));
        }
    }

    @Override
    protected void limitSpeed(int iteration, RealVector speed) {
        double vmax = maxSpeedSchedule.getTemperature(iteration);
        double vmin = minSpeedSchedule.getTemperature(iteration);

        for (int i = 0; i < speed.getSize(); i++) {
            speed.setValue(i, Math.min(Math.max(speed.getValue(i), vmin), vmax));
        }
    }
}
