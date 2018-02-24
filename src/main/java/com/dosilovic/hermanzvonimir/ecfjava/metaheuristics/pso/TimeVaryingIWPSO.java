package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.topologies.ITopology;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.ICoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.particle.Particle;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.RealVector;

public class TimeVaryingIWPSO<T extends Particle> extends AbstractBasicPSO<T> {

    private ICoolingSchedule inertiaWeightSchedule;

    public TimeVaryingIWPSO(
        int maxIterations,
        double desiredFitness,
        double desiredPrecision,
        boolean isFullyInformed,
        double individualFactor,
        double socialFactor,
        RealVector minSpeed,
        RealVector maxSpeed,
        ICoolingSchedule inertiaWeightSchedule,
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

        this.inertiaWeightSchedule = inertiaWeightSchedule;
    }

    @Override
    protected void updateSpeed(int iteration, RealVector speed, RealVector neighboursContribution) {
        double inertiaWeightFactor = inertiaWeightSchedule.getTemperature(iteration);
        for (int i = 0; i < speed.getSize(); i++) {
            speed.setValue(i, inertiaWeightFactor * speed.getValue(i) + neighboursContribution.getValue(i));
        }
    }
}
