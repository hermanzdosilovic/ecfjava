package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.ICoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.IMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

import java.util.Random;

public class SimpleSA<T> extends AbstractSA<T> {

    private static final Random RAND = new Random();

    public SimpleSA(
        double desiredPenalty,
        double desiredPrecision,
        IProblem<T> problem,
        IMutation<T> mutation,
        ICoolingSchedule outerCoolingSchedule,
        ICoolingSchedule innerCoolingSchedule
    ) {
        super(
            desiredPenalty,
            desiredPrecision,
            problem,
            mutation,
            outerCoolingSchedule,
            innerCoolingSchedule
        );
    }

    @Override
    protected ISolution<T> onOuterTemperatureStart(double outerTemperature) {
        return solution;
    }

    @Override
    protected ISolution<T> onInnerTemperatureStart(double outerTemperature, double innerTemperature) {
        return solution;
    }

    @Override
    protected ISolution<T> selectNextSolution(
        ISolution<T> neighbourSolution, double outerTemperature, double innerTemperature
    ) {
        double penaltyDiff = neighbourSolution.getPenalty() - solution.getPenalty();

        if (penaltyDiff <= 0) {
            return neighbourSolution;
        } else if (RAND.nextDouble() < Math.exp(-1.0 * penaltyDiff / outerTemperature)) {
            return neighbourSolution;
        }

        return solution;
    }
}
