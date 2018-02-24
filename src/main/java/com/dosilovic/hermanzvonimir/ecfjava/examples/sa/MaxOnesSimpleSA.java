package com.dosilovic.hermanzvonimir.ecfjava.examples.sa;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.ISimulatedAnnealing;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.SimpleSA;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.GeometricCoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.ICoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.BitVectorStochasticMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.IMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.adapters.CopyOnMutationAdapter;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.MaxOnesProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.BitVector;

public final class MaxOnesSimpleSA {

    public static void main(String[] args) {
        final int     NUMBER_OF_COMPONENTS = 80;
        final double  DESIRED_PENALTY      = -1;
        final double  DESIRED_PRECISION    = 0;
        final int     OUTER_ITERATIONS     = 4000;
        final double  OUTER_INITIAL_TEMP   = 10;
        final double  OUTER_FINAL_TEMP     = 1e-4;
        final int     INNER_ITERATIONS     = 3000;
        final double  INNER_INITIAL_TEMP   = 1000;
        final double  INNER_FINAL_TEMP     = 1e-4;
        final double  MUTATION_PROBABILITY = 0.0;
        final boolean FORCE_MUTATION       = true;

        IProblem<BitVector> problem = new MaxOnesProblem<>();
        IMutation<BitVector> mutation = new CopyOnMutationAdapter(
            new BitVectorStochasticMutation<>(MUTATION_PROBABILITY, FORCE_MUTATION)
        );

        ICoolingSchedule outerCoolingSchedule = new GeometricCoolingSchedule(
            OUTER_ITERATIONS, OUTER_INITIAL_TEMP, OUTER_FINAL_TEMP
        );

        ICoolingSchedule innerCoolingSchedule = new GeometricCoolingSchedule(
            INNER_ITERATIONS, INNER_INITIAL_TEMP, INNER_FINAL_TEMP
        );

        ISimulatedAnnealing<BitVector> simulatedAnnealing = new SimpleSA<>(
            DESIRED_PENALTY,
            DESIRED_PRECISION,
            problem,
            mutation,
            outerCoolingSchedule,
            innerCoolingSchedule
        );

        simulatedAnnealing.run(new BitVector(NUMBER_OF_COMPONENTS));
    }
}
