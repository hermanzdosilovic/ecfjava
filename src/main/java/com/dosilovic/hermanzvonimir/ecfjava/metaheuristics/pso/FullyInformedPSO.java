package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.ICoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.util.Particle;
import com.dosilovic.hermanzvonimir.ecfjava.util.RealVector;
import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;

import java.util.Collection;
import java.util.Random;

public class FullyInformedPSO<T extends RealVector> extends AbstractPSO<T> {

    private static final Random RAND = new Random();

    public FullyInformedPSO(
        int maxIterations,
        double desiredFitness,
        double desiredPrecision,
        IProblem<T> problem,
        ICoolingSchedule individualFactorSchedule,
        ICoolingSchedule socialFactorSchedule,
        ICoolingSchedule inertiaSchedule,
        ICoolingSchedule constrictionFactorSchedule
    ) {
        super(
            maxIterations,
            desiredFitness,
            desiredPrecision,
            problem,
            individualFactorSchedule,
            socialFactorSchedule,
            inertiaSchedule,
            constrictionFactorSchedule
        );
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void updateSpeedAndPosition(
        Collection<Particle<T>> particles,
        double individualFactor,
        double socialFactor,
        double inertiaFactor,
        double constrictionFactor
    ) {
        T          currentSolutionRepresentative;
        T          personalBestSolutionRepresentative;
        T          globalBestSolution = bestSolution.getRepresentative();
        RealVector currentSpeed;

        for (Particle<T> particle : particles) {
            currentSolutionRepresentative = (T) particle.getCurrentSolution().getRepresentative().clone();
            personalBestSolutionRepresentative = particle.getPersonalBestSolution().getRepresentative();
            currentSpeed = particle.getCurrentSpeed();

            for (int i = 0; i < currentSpeed.getSize(); i++) {
                double a = inertiaFactor * currentSpeed.getValue(i);
                double b =
                    individualFactor *
                    RAND.nextDouble() *
                    (personalBestSolutionRepresentative.getValue(i) - currentSolutionRepresentative.getValue(i));
                double c =
                    socialFactor *
                    RAND.nextDouble() *
                    (globalBestSolution.getValue(i) - currentSolutionRepresentative.getValue(i));

                currentSpeed.setValue(i, constrictionFactor * (a + b + c));
                currentSolutionRepresentative.setValue(
                    i,
                    currentSolutionRepresentative.getValue(i) + currentSpeed.getValue(i)
                );
            }

            particle.setCurrentSolution(new Solution<>(currentSolutionRepresentative));
        }
    }
}
