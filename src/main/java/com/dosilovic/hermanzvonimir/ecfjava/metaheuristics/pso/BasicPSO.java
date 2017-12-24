package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.topologies.ITopology;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.util.RealVector;
import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;

public class BasicPSO<T extends RealVector> extends AbstractPSO<T> {

    protected RealVector minSpeed;
    protected RealVector maxSpeed;

    public BasicPSO(
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

    @SuppressWarnings("unchecked")
    @Override
    protected void updateParticle(int iteration, Particle<T> particle, RealVector neighboursContribution) {
        RealVector speed                 = particle.getCurrentSpeed();
        T          currentRepresentative = particle.getCurrentSolution().getRepresentative();
        T          nextRepresentative    = (T) currentRepresentative.clone();

        double v, x;
        for (int i = 0; i < speed.getSize(); i++) {
            v = speed.getValue(i) + neighboursContribution.getValue(i);
            v = Math.max(v, minSpeed.getValue(i));
            v = Math.min(v, maxSpeed.getValue(i));

            x = currentRepresentative.getValue(i) + v;
            x = Math.max(x, minValue.getValue(i));
            x = Math.min(x, maxValue.getValue(i));

            speed.setValue(i, v);
            nextRepresentative.setValue(i, x);
        }

        particle.setCurrentSolution(new Solution<>(nextRepresentative));
    }
}
