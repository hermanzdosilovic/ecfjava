package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.ga;

import com.dosilovic.hermanzvonimir.ecfjava.models.crossovers.ICrossover;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.IMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.selections.ISelection;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

public abstract class ParallelGA<T extends ISolution> extends AbstractGA<T> {
    //
//    private int processors;
//    private Queue<ISolution<T>> nextPopulationQueue;
//    private Queue<Integer> taskQueue;
//
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
//        this.processors = Runtime.getRuntime().availableProcessors();
//        this.nextPopulationQueue = new ConcurrentLinkedQueue<>();
//        this.taskQueue = new ConcurrentLinkedQueue<>();
    }
//
//    @Override
//    protected List<ISolution<T>> createNextPopulation() {
//        nextPopulationQueue.clear();
//        taskQueue.clear();
//
//        if (useElitism) {
//            nextPopulationQueue.add(bestSolution);
//            nextPopulationQueue.add(Solutions.findSecondBestByFitness(population));
//        }
//
//        int numberOfTasks           = population.size() / processors;
//        int availablePopulationSize = population.size() - nextPopulationQueue.size();
//        int loadFactor              = 2 * availablePopulationSize / numberOfTasks;
//        for (int i = 0; i < numberOfTasks; i++) {
//            int nextTask = Math.min(loadFactor, availablePopulationSize);
//            taskQueue.add(nextTask);
//            availablePopulationSize -= nextTask;
//            if (availablePopulationSize <= 0) {
//                break;
//            }
//        }
//
//        for (int i = 0; i < processors; i++) {
//            new Thread(() -> {
//                while (true) {
//                    Integer numberOfChildren = taskQueue.poll();
//                    if (numberOfChildren == null) {
//                        break;
//                    }
//
//                    while (numberOfChildren-- > 0) {
//                        ISolution<T> mom = selection.select(population);
//                        ISolution<T> dad = selection.select(population);
//
//                        Collection<ISolution<T>> children = crossover.cross(mom, dad);
//
//                        if (!evaluateEveryGeneration) {
//                            List<ISolution<T>> mutatedChildren = new ArrayList<>(children.size());
//                            for (ISolution<T> child : children) {
//                                mutatedChildren.add(mutation.mutate(child));
//                            }
//
//                            Solutions.updateFitness(mutatedChildren, problem);
//                            nextPopulationQueue.add(Solutions.findBestByFitness(mutatedChildren));
//                        } else {
//                            for (ISolution<T> child : children) {
//                                nextPopulationQueue.add(mutation.mutate(child));
//                                break;
//                            }
//                        }
//                    }
//                }
//            }).start();
//        }
//
//        while (nextPopulationQueue.size() < population.size()) {}
//
//        return new ArrayList<>(nextPopulationQueue);
//    }
}
