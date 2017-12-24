package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.topologies.ITopology;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.ICoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.util.RealVector;
import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;

public class ConstrictedPSO<T extends RealVector> extends AbstractPSO<T> {

    private ICoolingSchedule constrictedFactorSchedule;

    public ConstrictedPSO(
        int maxIterations,
        double desiredFitness,
        double desiredPrecision,
        boolean isFullyInformed,
        double individualFactor,
        double socialFactor,
        RealVector minValue,
        RealVector maxValue,
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
            minValue,
            maxValue,
            problem,
            topology
        );

        this.constrictedFactorSchedule = constrictedFactorSchedule;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void updateParticle(int iteration, Particle<T> particle, RealVector neighboursContribution) {
        RealVector speed                 = particle.getCurrentSpeed();
        T          currentRepresentative = particle.getCurrentSolution().getRepresentative();
        T          nextRepresentative    = (T) currentRepresentative.clone();

        double constrictionFactor = constrictedFactorSchedule.getTemperature(iteration);
        double v, x;
        for (int i = 0; i < speed.getSize(); i++) {
            v = constrictionFactor * (speed.getValue(i) + neighboursContribution.getValue(i));

            x = currentRepresentative.getValue(i) + v;
            x = Math.max(x, minValue.getValue(i));
            x = Math.min(x, maxValue.getValue(i));

            speed.setValue(i, v);
            nextRepresentative.setValue(i, x);
        }

        particle.setCurrentSolution(new Solution<>(nextRepresentative));
    }
}
