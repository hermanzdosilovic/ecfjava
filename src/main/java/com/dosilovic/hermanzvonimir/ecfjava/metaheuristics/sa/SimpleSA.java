package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.ICoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.IMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;

import java.util.Random;

public class SimpleSA<T> extends AbstractSA<T> {

    private static final Random RAND = new Random();

    public SimpleSA(
        double desiredFitness,
        double desiredPrecision,
        IProblem<T> problem,
        IMutation<T> mutation,
        ICoolingSchedule outerCoolingSchedule,
        ICoolingSchedule innerCoolingSchedule
    ) {
        super(
            desiredFitness,
            desiredPrecision,
            problem,
            mutation,
            outerCoolingSchedule,
            innerCoolingSchedule
        );
    }

    @Override
    protected Solution<T> onOuterTemperatureStart(
        Solution<T> currentSolution,
        Solution<T> bestSolution,
        double outerTemperature
    ) {
        return currentSolution;
    }

    @Override
    protected Solution<T> onInnerTemperatureStart(
        Solution<T> currentSolution,
        Solution<T> bestSolution,
        double outerTemperature,
        double innerTemperature
    ) {
        return currentSolution;
    }

    @Override
    protected Solution<T> selectNextSolution(
        Solution<T> currentSolution,
        Solution<T> neighbourSolution,
        Solution<T> bestSolution,
        double outerTemperature,
        double innerTemperature
    ) {
        double penaltyDiff = neighbourSolution.getPenalty() - currentSolution.getPenalty();

        if (penaltyDiff <= 0) {
            return neighbourSolution;
        } else if (RAND.nextDouble() <= Math.exp(-1.0 * penaltyDiff / outerTemperature)) {
            return neighbourSolution;
        }

        return currentSolution;
    }
}
