package com.dosilovic.hermanzvonimir.ecfjava.util;

import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;

import java.util.Collection;

public class Solution<T> implements Comparable<Solution<T>> {

    private T       representative;
    private double  fitness;
    private double  penalty;
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

    public void setRepresentative(T representative) {
        this.representative = representative;
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

    public boolean hasFitness() {
        return hasFitness;
    }

    public boolean hasPenalty() {
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

    public void evaluatePenalty(IProblem<T> problem, boolean updateIfHasPenalty) {
        if ((hasPenalty && updateIfHasPenalty) || !hasPenalty) {
            setPenalty(problem.penalty(representative));
        }
    }

    public void evaluatePenalty(IProblem<T> problem) {
        evaluatePenalty(problem, false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Solution<?> solution = (Solution<?>) o;

        return representative != null ?
               representative.equals(solution.representative) :
               solution.representative == null;
    }

    @Override
    public int hashCode() {
        return representative != null ? representative.hashCode() : 0;
    }

    @Override
    public String toString() {
        return representative.toString();
    }

    @Override
    public int compareTo(Solution<T> o) {
        if (this.fitness < o.fitness) {
            return -1;
        } else if (this.fitness > o.fitness) {
            return 1;
        }
        return 0;
    }

    public static <T> void evaluateFitness(
        Collection<Solution<T>> solutions,
        IProblem<T> problem,
        boolean updateIfHasFitness
    ) {
        for (Solution<T> solution : solutions) {
            solution.evaluateFitness(problem, updateIfHasFitness);
        }
    }

    public static <T> void evaluateFitness(Collection<Solution<T>> solutions, IProblem<T> problem) {
        evaluateFitness(solutions, problem, false);
    }

    public static <T> void evaluatePenalty(
        Collection<Solution<T>> solutions,
        IProblem<T> problem,
        boolean updateIfHasPenalty
    ) {
        for (Solution<T> solution : solutions) {
            solution.evaluatePenalty(problem, updateIfHasPenalty);
        }
    }

    public static <T> void evaluatePenalty(Collection<Solution<T>> solutions, IProblem<T> problem) {
        evaluatePenalty(solutions, problem, false);
    }

    public static <T> Solution<T> betterByFitness(Solution<T> first, Solution<T> second) {
        if (first == null) {
            return second;
        } else if (second == null) {
            return first;
        } else if (first.getFitness() > second.getFitness()) {
            return first;
        }
        return second;
    }

    public static <T> Solution<T> worstByFitness(Solution<T> first, Solution<T> second) {
        if (betterByFitness(first, second) == first) {
            return second;
        }
        return first;
    }

    public static <T> Solution<T> betterByPenalty(Solution<T> first, Solution<T> second) {
        if (first == null) {
            return second;
        } else if (second == null) {
            return first;
        } else if (first.getPenalty() < second.getPenalty()) {
            return first;
        }
        return second;
    }

    public static <T> Solution<T> worstByPenalty(Solution<T> first, Solution<T> second) {
        if (betterByPenalty(first, second) == first) {
            return second;
        }
        return first;
    }

    public static <T> Solution<T> findBestByFitness(Collection<Solution<T>> solutions) {
        Solution<T> bestSolution = null;
        for (Solution<T> solution : solutions) {
            bestSolution = betterByFitness(bestSolution, solution);
        }
        return bestSolution;
    }

    public static <T> Solution<T> findSecondBestByFitness(Collection<Solution<T>> solutions) {
        Solution<T> bestSolution       = null;
        Solution<T> secondBestSolution = null;

        for (Solution<T> solution : solutions) {
            if (betterByFitness(bestSolution, solution) == solution) {
                secondBestSolution = bestSolution;
                bestSolution = solution;
            } else if (betterByFitness(secondBestSolution, solution) == solution) {
                secondBestSolution = solution;
            }
        }

        return secondBestSolution;
    }

    public static <T> Solution<T> findBestByPenalty(Collection<Solution<T>> solutions) {
        Solution<T> bestSolution = null;
        for (Solution<T> solution : solutions) {
            bestSolution = betterByPenalty(bestSolution, solution);
        }
        return bestSolution;
    }

    public static <T> Solution<T> findSecondBestByPenalty(Collection<Solution<T>> solutions) {
        Solution<T> bestSolution       = null;
        Solution<T> secondBestSolution = null;

        for (Solution<T> solution : solutions) {
            if (betterByPenalty(bestSolution, solution) == solution) {
                secondBestSolution = bestSolution;
                bestSolution = solution;
            } else if (betterByPenalty(secondBestSolution, solution) == solution) {
                secondBestSolution = solution;
            }
        }

        return secondBestSolution;
    }
}
