package com.dosilovic.hermanzvonimir.ecfjava.models.solutions;

import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;

import java.util.Collection;

public final class Solutions {

    public static <T> void updateFitness(Collection<? extends ISolution<T>> population, IProblem<T> problem) {
        problem.updateFitness(population);
    }

    public static <T> void updatePenalty(Collection<? extends ISolution<T>> population, IProblem<T> problem) {
        problem.updatePenalty(population);
    }

    public static <T> ISolution<T> betterByFitness(ISolution<T> first, ISolution<T> second) {
        if (first == null) {
            return second;
        } else if (second == null) {
            return first;
        } else if (first.getFitness() > second.getFitness()) {
            return first;
        }
        return second;
    }

    public static <T> ISolution<T> worstByFitness(ISolution<T> first, ISolution<T> second) {
        if (betterByFitness(first, second) == first) {
            return second;
        }
        return first;
    }

    public static <T> ISolution<T> betterByPenalty(ISolution<T> first, ISolution<T> second) {
        if (first == null) {
            return second;
        } else if (second == null) {
            return first;
        } else if (first.getPenalty() < second.getPenalty()) {
            return first;
        }
        return second;
    }

    public static <T> ISolution<T> worstByPenalty(ISolution<T> first, ISolution<T> second) {
        if (betterByPenalty(first, second) == first) {
            return second;
        }
        return first;
    }

    public static <T> ISolution<T> findBestByFitness(Collection<? extends ISolution<T>> solutions) {
        ISolution<T> best = null;
        for (ISolution<T> solution : solutions) {
            best = betterByFitness(best, solution);
        }
        return best;
    }

    public static <T> ISolution<T> findSecondBestByFitness(Collection<? extends ISolution<T>> solutions) {
        ISolution<T> best       = null;
        ISolution<T> secondBest = null;

        for (ISolution<T> solution : solutions) {
            if (betterByFitness(best, solution) == solution) {
                secondBest = best;
                best = solution;
            } else if (betterByFitness(secondBest, solution) == solution) {
                secondBest = solution;
            }
        }

        return secondBest;
    }

    public static <T> ISolution<T> findBestByPenalty(Collection<? extends ISolution<T>> solutions) {
        ISolution<T> best = null;
        for (ISolution<T> solution : solutions) {
            best = betterByPenalty(best, solution);
        }
        return best;
    }

    public static <T> ISolution<T> findSecondBestByPenalty(Collection<? extends ISolution<T>> solutions) {
        ISolution<T> best       = null;
        ISolution<T> secondBest = null;

        for (ISolution<T> solution : solutions) {
            if (betterByPenalty(best, solution) == solution) {
                secondBest = best;
                best = solution;
            } else if (betterByPenalty(secondBest, solution) == solution) {
                secondBest = solution;
            }
        }

        return secondBest;
    }
}
