package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.topologies.ITopology;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.ICoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.particle.Particle;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.RealVector;

public class ConstrictedPSO<T extends Particle> extends AbstractBasicPSO<T> {

    private ICoolingSchedule constrictedFactorSchedule;

    public ConstrictedPSO(
        int maxIterations,
        double desiredFitness,
        double desiredPrecision,
        boolean isFullyInformed,
        double individualFactor,
        double socialFactor,
        RealVector minSpeed,
        RealVector maxSpeed,
        ICoolingSchedule constrictedFactorSchedule,
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

        this.constrictedFactorSchedule = constrictedFactorSchedule;
    }

    @Override
    protected void updateSpeed(int iteration, RealVector speed, RealVector neighboursContribution) {
        double constrictionFactor = constrictedFactorSchedule.getTemperature(iteration);
        for (int i = 0; i < speed.getSize(); i++) {
            speed.setValue(i, constrictionFactor * (speed.getValue(i) + neighboursContribution.getValue(i)));
        }
    }
}
