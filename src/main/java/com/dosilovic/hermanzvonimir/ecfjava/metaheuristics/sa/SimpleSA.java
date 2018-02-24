package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.ICoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.IMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.IRandom;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.Random;

public class SimpleSA<T extends ISolution> extends AbstractSA<T> {

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
    protected T onOuterTemperatureStart(double outerTemperature) {
        return solution;
    }

    @Override
    protected T onInnerTemperatureStart(double outerTemperature, double innerTemperature) {
        return solution;
    }

    @Override
    protected T selectNextSolution(
        T neighbourSolution, double outerTemperature, double innerTemperature
    ) {
        IRandom random      = Random.getRandom();
        double  penaltyDiff = neighbourSolution.getPenalty() - solution.getPenalty();

        if (Math.signum(penaltyDiff) != 1.0) {
            return neighbourSolution;
        } else if (random.nextDouble() < Math.exp(-1.0 * penaltyDiff / outerTemperature)) {
            return neighbourSolution;
        }

        return solution;
    }
}
