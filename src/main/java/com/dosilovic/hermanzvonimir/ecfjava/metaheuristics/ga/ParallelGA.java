package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.ga;

import com.dosilovic.hermanzvonimir.ecfjava.models.crossovers.ICrossover;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.IMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.selections.ISelection;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.util.Solutions;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ParallelGA<T extends ISolution> extends AbstractGA<T> {

    private int            processors;
    private Queue<Integer> taskQueue;
    private Queue<T>       nextPopulationQueue;

    public ParallelGA(
        boolean useElitism,
        int maxGenerations,
        boolean evaluateEveryGeneration,
        double desiredFitness,
        double desiredPrecision,
        IProblem<T> problem,
        ISelection<T> selection,
        ICrossover<T> crossover,
        IMutation<T> mutation
    ) {
        super(
            useElitism,
            maxGenerations,
            evaluateEveryGeneration,
            desiredFitness,
            desiredPrecision,
            problem,
            selection,
            crossover,
            mutation
        );
        this.processors = Runtime.getRuntime().availableProcessors();
        this.taskQueue = new ConcurrentLinkedQueue<>();
        this.nextPopulationQueue = new ConcurrentLinkedQueue<>();
    }

    @Override
    protected void createNextPopulation(List<T> nextPopulation) {
        taskQueue.clear();
        nextPopulationQueue.clear();

        if (useElitism) {
            nextPopulation.set(0, bestSolution);
            nextPopulation.set(1, Solutions.findSecondBestByFitness(population));
        }

        int numberOfChildrenPerThread = (population.size() - (useElitism ? 2 : 0)) / processors;
        int leftOfNumberOfChildren    = (population.size() - (useElitism ? 2 : 0)) % processors;

        for (int i = 0; i < processors; i++) {
            taskQueue.add(numberOfChildrenPerThread);
        }

        if (leftOfNumberOfChildren != 0) {
            taskQueue.add(leftOfNumberOfChildren);
        }

        for (int i = 0; i < processors; i++) {
            taskQueue.add(-1);
        }

        Thread[] workers = new Thread[processors];
        for (int i = 0; i < processors; i++) {
            workers[i] = new Thread(() -> {
                while (true) {
                    Integer numberOfChildren = taskQueue.poll();
                    if (numberOfChildren == null) {
                        Thread.yield();
                    } else if (numberOfChildren == -1) {
                        break;
                    }

                    while (numberOfChildren-- > 0) {
                        List<T> children = mutation.mutate(
                            crossover.cross(
                                selection.select(population),
                                selection.select(population)
                            )
                        );

                        if (!evaluateEveryGeneration) {
                            problem.updateFitness(children);
                            nextPopulationQueue.offer(Solutions.findBestByFitness(children));
                        } else {
                            nextPopulationQueue.offer(children.get(0));
                        }
                    }
                }
            });
            workers[i].start();
        }

        try {
            for (int i = 0; i < processors; i++) {
                workers[i].join();
            }
        } catch (Exception ignorable) {
            System.err.println(ignorable.getMessage());
            System.exit(-1);
        }

        int k = useElitism ? 2 : 0;
        for (T individual : nextPopulationQueue) {
            nextPopulation.set(k++, individual);
        }
    }
}
