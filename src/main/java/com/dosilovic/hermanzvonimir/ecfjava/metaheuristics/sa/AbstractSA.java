package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.AbstractMetaheuristic;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.ICoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.IMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;

import java.time.Duration;
import java.util.Date;

public abstract class AbstractSA<T> extends AbstractMetaheuristic<T> implements ISimulatedAnnealing<T> {

    protected IProblem<T>      problem;
    protected IMutation<T>     mutation;
    protected ICoolingSchedule outerCoolingSchedule;
    protected ICoolingSchedule innerCoolingSchedule;
    protected double           desiredPenalty;
    protected double           desiredPrecision;

    protected Solution<T> bestSolution;
    protected Solution<T> initialSolution;

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
    public void setInitialSolution(final T initialSolution) {
        this.initialSolution = new Solution<>(initialSolution);
    }

    public Solution<T> getBestSolution() {
        return bestSolution;
    }

    @Override
    public T run(T initialSolution) {
        setInitialSolution(initialSolution);
        return run();
    }

    @Override
    public T run() {
        long startTime = System.nanoTime();

        if (initialSolution == null) {
            throw new IllegalStateException("No initial solution");
        }

        Solution<T> currentSolution = initialSolution;
        bestSolution = initialSolution;

        currentSolution.evaluatePenalty(problem);

        for (double outerTemperature : outerCoolingSchedule) {
            currentSolution = onOuterTemperatureStart(currentSolution, outerTemperature);
            notifyObservers(currentSolution);

            System.err.printf(
                "Temperature %f (%s):\n" +
                "\tbestPenalty    = %f\n" +
                "\tcurrentPenalty = %f\n\n",
                outerTemperature,
                new Date(),
                bestSolution.getPenalty(),
                currentSolution.getPenalty()
            );


            for (double innerTemperature : innerCoolingSchedule) {
                currentSolution = onInnerTemperatureStart(
                    currentSolution,
                    outerTemperature,
                    innerTemperature
                );

                Solution<T> neighborSolution = mutation.mutate(currentSolution);
                neighborSolution.evaluatePenalty(problem);

                currentSolution = selectNextSolution(
                    currentSolution,
                    neighborSolution,
                    outerTemperature,
                    innerTemperature
                );

                if (currentSolution.getPenalty() <= bestSolution.getPenalty()) {
                    bestSolution = currentSolution;
                }

                if (Math.abs(bestSolution.getPenalty() - desiredPenalty) <= desiredPrecision) {
                    System.err.println("Reached desired penalty.\n");
                    break;
                }
            }

            if (Math.abs(bestSolution.getPenalty() - desiredPenalty) <= desiredPrecision) {
                break;
            }
        }

        long     stopTime = System.nanoTime();
        Duration duration = Duration.ofNanos(stopTime - startTime);

        System.err.printf("Solution: %s\nPenalty: %f\n", bestSolution.getRepresentative(), bestSolution.getPenalty());
        System.err.printf(
            "Time: %02d:%02d:%02d.%03d\n\n",
            duration.toHoursPart(),
            duration.toMinutesPart(),
            duration.toSecondsPart(),
            duration.toMillisPart()
        );
        System.err.flush();

        return bestSolution.getRepresentative();
    }

    protected abstract Solution<T> onOuterTemperatureStart(
        Solution<T> currentSolution,
        double outerTemperature
    );

    protected abstract Solution<T> onInnerTemperatureStart(
        Solution<T> currentSolution,
        double outerTemperature,
        double innerTemperature
    );

    protected abstract Solution<T> selectNextSolution(
        Solution<T> currentSolution,
        Solution<T> nextSolution,
        double outerTemperature,
        double innerTemperature
    );
}
