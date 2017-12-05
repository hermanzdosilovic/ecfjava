package com.dosilovic.hermanzvonimir.ecfjava.examples.sa;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.ISimulatedAnnealing;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.SimpleSA;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.GeometricCoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.ICoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.BitVectorStohasticMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.IMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.MaxOnesProblem;
import com.dosilovic.hermanzvonimir.ecfjava.util.BitVector;

public final class MaxOnesSimpleSA {

    public static void main(String[] args) {
        final int     NUMBER_OF_COMPONENTS = 80;
        final int     OUTER_ITERATIONS     = 4000;
        final double  OUTER_INITIAL_TEMP   = 500;
        final double  OUTER_FINAL_TEMP     = 1e-4;
        final int     INNER_ITERATIONS     = 2000;
        final double  MUTATION_PROBABILITY = 0.1;
        final boolean FORCE_MUTATION       = true;
        final double  DESIRED_PENALTY      = -1;
        final double  DESIRED_PRECISION    = 1e-3;

        IProblem<BitVector> problem = new MaxOnesProblem<>();
        IMutation<BitVector> mutation = new BitVectorStohasticMutation<>(MUTATION_PROBABILITY, FORCE_MUTATION);
        ICoolingSchedule outerCoolingSchedule =
            new GeometricCoolingSchedule(OUTER_ITERATIONS, OUTER_INITIAL_TEMP, OUTER_FINAL_TEMP);
        ICoolingSchedule innerCoolingSchedule = new GeometricCoolingSchedule(INNER_ITERATIONS, 0, 0);

        ISimulatedAnnealing<BitVector> simulatedAnnealing =
            new SimpleSA<>(DESIRED_PENALTY, DESIRED_PRECISION, problem, mutation, outerCoolingSchedule,
                innerCoolingSchedule);

        simulatedAnnealing.run(new BitVector(NUMBER_OF_COMPONENTS));
    }
}
