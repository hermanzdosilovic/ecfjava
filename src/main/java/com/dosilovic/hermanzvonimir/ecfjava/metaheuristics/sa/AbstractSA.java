package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.AbstractIndividualMetaheuristic;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.ICoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.IMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

import java.time.Duration;
import java.util.Date;

public abstract class AbstractSA<T> extends AbstractIndividualMetaheuristic<T> implements ISimulatedAnnealing<T> {

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
    public ISolution<T> run() {
        long startTime = System.nanoTime();

        setSolution(initialSolution);

        if (initialSolution == null) {
            throw new IllegalStateException("No initial solution");
        }

        bestSolution = solution;

        solution.updatePenalty(problem);

        boolean innerStop = false;

        for (double outerTemperature : outerCoolingSchedule) {
            solution = onOuterTemperatureStart(outerTemperature);
            notifyObservers();

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

                ISolution<T> neighborSolution = mutation.mutate(solution);
                neighborSolution.updatePenalty(problem);

                solution = selectNextSolution(
                    neighborSolution,
                    outerTemperature,
                    innerTemperature
                );

                if (solution.getPenalty() <= bestSolution.getPenalty()) {
                    bestSolution = solution;
                }

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

    protected abstract ISolution<T> onOuterTemperatureStart(double outerTemperature);

    protected abstract ISolution<T> onInnerTemperatureStart(double outerTemperature, double innerTemperature);

    protected abstract ISolution<T> selectNextSolution(
        ISolution<T> nextSolution, double outerTemperature, double innerTemperature
    );
}
