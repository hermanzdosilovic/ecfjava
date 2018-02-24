package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.AbstractIndividualMetaheuristic;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.ICoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.IMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.util.Solutions;

import java.time.Duration;
import java.util.Date;

public abstract class AbstractSA<T extends ISolution> extends AbstractIndividualMetaheuristic<T> implements ISimulatedAnnealing<T> {

    protected IProblem<T>      problem;
    protected IMutation<T>     mutation;
    protected ICoolingSchedule outerCoolingSchedule;
    protected ICoolingSchedule innerCoolingSchedule;
    protected double           desiredPenalty;
    protected double           desiredPrecision;

    public AbstractSA(
        double desiredPenalty,
        double desiredPrecision,
        IProblem<T> problem,
        IMutation<T> mutation,
        ICoolingSchedule outerCoolingSchedule,
        ICoolingSchedule innerCoolingSchedule
    ) {
        this.desiredPenalty = desiredPenalty;
        this.desiredPrecision = desiredPrecision;
        this.problem = problem;
        this.mutation = mutation;
        this.outerCoolingSchedule = outerCoolingSchedule;
        this.innerCoolingSchedule = innerCoolingSchedule;
    }

    @Override
    public T run() {
        long startTime = System.nanoTime();

        setSolution(initialSolution);
        isStopped.set(false);
        bestSolution = null;

        boolean innerStop = false;

        problem.updatePenalty(solution);

        for (double outerTemperature : outerCoolingSchedule) {
            solution = onOuterTemperatureStart(outerTemperature);
            setBestSolution(Solutions.betterByPenalty(bestSolution, solution));

            System.err.printf(
                "Temperature %f (%s)\n" +
                "\t   bestPenalty: %f\n" +
                "\tcurrentPenalty: %f\n\n",
                outerTemperature,
                new Date(),
                bestSolution.getPenalty(),
                solution.getPenalty()
            );


            for (double innerTemperature : innerCoolingSchedule) {
                solution = onInnerTemperatureStart(
                    outerTemperature,
                    innerTemperature
                );

                T neighborSolution = mutation.mutate(solution);
                problem.updatePenalty(neighborSolution);

                solution = selectNextSolution(
                    neighborSolution,
                    outerTemperature,
                    innerTemperature
                );

                // We don't want to notify observers in every iteration of inner cooling.
                bestSolution = Solutions.betterByPenalty(bestSolution, solution);

                if (Math.abs(bestSolution.getPenalty() - desiredPenalty) <= desiredPrecision) {
                    System.err.println("Reached desired penalty.\n");
                    break;
                }

                if (isStopped.get()) {
                    System.err.println("Algorithm stopped.\n");
                    innerStop = true;
                    break;
                }
            }

            if (Math.abs(bestSolution.getPenalty() - desiredPenalty) <= desiredPrecision) {
                break;
            }

            if (isStopped.get()) {
                if (!innerStop) {
                    System.err.println("Algorithm stopped.\n");
                }
                break;
            }
        }

        setBestSolution(Solutions.betterByPenalty(bestSolution, solution));

        Duration duration = Duration.ofNanos(System.nanoTime() - startTime);
        System.err.printf(
            "Time: %02d:%02d:%02d.%03d\n\n",
            duration.toHoursPart(),
            duration.toMinutesPart(),
            duration.toSecondsPart(),
            duration.toMillisPart()
        );
        System.err.println(bestSolution);
        System.err.println();
        System.err.flush();

        return bestSolution;
    }

    protected abstract T onOuterTemperatureStart(double outerTemperature);

    protected abstract T onInnerTemperatureStart(double outerTemperature, double innerTemperature);

    protected abstract T selectNextSolution(
        T neighborSolution, double outerTemperature, double innerTemperature
    );
}
