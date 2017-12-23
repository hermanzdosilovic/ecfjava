package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso;

import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.util.RealVector;
import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;

import java.util.Random;

public class BasicPSO<T extends RealVector> extends AbstractPSO<T> {

    protected RealVector minSpeed;
    protected RealVector maxSpeed;

    private static final Random RAND = new Random();

    public BasicPSO(
        int maxIterations,
        double desiredFitness,
        double desiredPrecision,
        double individualFactor,
        double socialFactor,
        RealVector minValue,
        RealVector maxValue,
        RealVector minSpeed,
        RealVector maxSpeed,
        IProblem<T> problem
    ) {
        super(
            maxIterations,
            desiredFitness,
            desiredPrecision,
            individualFactor,
            socialFactor,
            minValue,
            maxValue,
            problem
        );

        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
    }

    @Override
    protected void updateParticle(Particle<T> particle) {
        T          currentRepresentative      = particle.getCurrentSolution().getRepresentative();
        T          nextRepresentative         = (T) currentRepresentative.clone();
        T          personalBestRepresentative = particle.getPersonalBestSolution().getRepresentative();
        T          globalBestRepresentative   = bestSolution.getRepresentative();
        RealVector speed                      = particle.getCurrentSpeed();

        for (int i = 0; i < speed.getDimension(); i++) {
            double v  = speed.getValue(i);
            double pb = personalBestRepresentative.getValue(i);
            double x  = currentRepresentative.getValue(i);
            double gb = globalBestRepresentative.getValue(i);
            double vt = v +
                        individualFactor * RAND.nextDouble() * (pb - x) +
                        socialFactor * RAND.nextDouble() * (gb - x);

            vt = Math.max(vt, minSpeed.getValue(i));
            vt = Math.min(vt, maxSpeed.getValue(i));

            double xt = x + vt;
            xt = Math.max(xt, minValue.getValue(i));
            xt = Math.min(xt, maxValue.getValue(i));

            speed.setValue(i, vt);
            nextRepresentative.setValue(i, xt);
        }

        particle.setCurrentSolution(new Solution<>(nextRepresentative));
    }
}
