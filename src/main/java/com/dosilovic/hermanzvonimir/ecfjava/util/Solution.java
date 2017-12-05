package com.dosilovic.hermanzvonimir.ecfjava.util;

import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;

import java.util.ArrayList;
import java.util.Collection;

public class Solution<T> {

    private T representative;
    private double fitness;
    private double penalty;
    private boolean hasFitness;
    private boolean hasPenalty;

    public Solution(T representative) {
        this.representative = representative;
        fitness = Double.MIN_VALUE;
        penalty = Double.MAX_VALUE;
        hasFitness = hasPenalty = false;
    }

    public T getRepresentative() {
        return representative;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
        hasFitness = true;
    }

    public double getPenalty() {
        return penalty;
    }

    public void setPenalty(double penalty) {
        this.penalty = penalty;
        hasPenalty = true;
    }

    public boolean isHasFitness() {
        return hasFitness;
    }

    public boolean isHasPenalty() {
        return hasPenalty;
    }

    public void evaluateFitness(IProblem<T> problem, boolean updateIfHasFitness) {
        if ((hasFitness && updateIfHasFitness) || !hasFitness) {
            setFitness(problem.fitness(representative));
        }
    }

    public void evaluateFitness(IProblem<T> problem) {
        evaluateFitness(problem, false);
    }

    public static <T> void evaluateFitness(Collection<Solution<T>> solutions, IProblem<T> problem,
        boolean updateIfHasFitness) {
        for (Solution<T> solution : solutions) {
            solution.evaluateFitness(problem, updateIfHasFitness);
        }
    }

    public static <T> void evaluateFitness(Collection<Solution<T>> solutions, IProblem<T> problem) {
        evaluateFitness(solutions, problem, false);
    }


    public void evaluatePenalty(IProblem<T> problem, boolean updateIfHasPenalty) {
        if ((hasPenalty && updateIfHasPenalty) || !hasPenalty) {
            setPenalty(problem.penalty(representative));
        }
    }

    public void evaluatePenalty(IProblem<T> problem) {
        evaluatePenalty(problem, false);
    }

    public static <T> void evaluatePenalty(Collection<Solution<T>> solutions, IProblem<T> problem,
        boolean updateIfHasPenalty) {
        for (Solution<T> solution : solutions) {
            solution.evaluatePenalty(problem, updateIfHasPenalty);
        }
    }

    public static <T> void evaluatePenalty(Collection<Solution<T>> solutions, IProblem<T> problem) {
        evaluatePenalty(solutions, problem, false);
    }

    public static <T> Collection<T> collectRepresentatives(Collection<Solution<T>> solutions) {
        Collection<T> representatives = new ArrayList<>(solutions.size());
        for (Solution<T> solution : solutions) {
            representatives.add(solution.representative);
        }
        return representatives;
    }

    public static <T> Solution<T> findBest(Collection<Solution<T>> solutions) {
        Solution<T> currentBestSolution = null;

        for (Solution<T> solution : solutions) {
            if (currentBestSolution == null || solution.getFitness() > currentBestSolution.getFitness()) {
                currentBestSolution = solution;
            }
        }

        return currentBestSolution;
    }

    public static <T> Solution<T> findSecondBest(Collection<Solution<T>> solutions) {
        Solution<T> currentBestSolution = null;
        Solution<T> currentSecondBestSolution = null;

        for (Solution<T> solution : solutions) {
            if (currentBestSolution == null || solution.getFitness() > currentBestSolution.getFitness()) {
                currentSecondBestSolution = currentBestSolution;
                currentBestSolution = solution;
            } else if (currentSecondBestSolution == null || solution.getFitness() > currentSecondBestSolution
                .getFitness()) {
                currentSecondBestSolution = solution;
            }
        }

        return currentSecondBestSolution;
    }
}
