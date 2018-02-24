package com.dosilovic.hermanzvonimir.ecfjava.models.solutions.util;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

import java.util.Collection;
import java.util.Comparator;

public final class Solutions {

    public static final Comparator<ISolution> FITNESS_COMPARATOR = (s1, s2) -> {
        if (s1.getFitness() == s2.getFitness()) {
            return 0;
        } else if (betterByFitness(s1, s2) == s1) {
            return 1;
        }
        return -1;
    };
//
//    public static final Comparator<ISolution> PENALTY_COMPARATOR = (s1, s2) -> {
//        if (s1.getPenalty() == s2.getPenalty()) {
//            return 0;
//        } else if (betterByPenalty(s1, s2) == s1) {
//            return 1;
//        }
//        return -1;
//    };

    public static <T extends ISolution> T betterByFitness(T first, T second) {
        if (first == null) {
            return second;
        } else if (second == null) {
            return first;
        } else if (first.getFitness() > second.getFitness()) {
            return first;
        }
        return second;
    }

    //    public static ISolution worstByFitness(ISolution first, ISolution second) {
//        if (betterByFitness(first, second) == first) {
//            return second;
//        }
//        return first;
//    }
//
    public static <T extends ISolution> T betterByPenalty(T first, T second) {
        if (first == null) {
            return second;
        } else if (second == null) {
            return first;
        } else if (first.getPenalty() < second.getPenalty()) {
            return first;
        }
        return second;
    }

    //
//    public static ISolution worstByPenalty(ISolution first, ISolution second) {
//        if (betterByPenalty(first, second) == first) {
//            return second;
//        }
//        return first;
//    }
//
    public static <T extends ISolution> T findBestByFitness(Collection<T> solutions) {
        T best = null;
        for (T solution : solutions) {
            best = betterByFitness(best, solution);
        }
        return best;
    }

    public static <T extends ISolution> T findSecondBestByFitness(Collection<T> solutions) {
        T best       = null;
        T secondBest = null;

        for (T solution : solutions) {
            if (betterByFitness(best, solution) == solution) {
                secondBest = best;
                best = solution;
            } else if (betterByFitness(secondBest, solution) == solution) {
                secondBest = solution;
            }
        }

        return secondBest;
    }
//
//    public static ISolution findBestByPenalty(Collection<? extends ISolution> solutions) {
//        ISolution best = null;
//        for (ISolution solution : solutions) {
//            best = betterByPenalty(best, solution);
//        }
//        return best;
//    }
//
//    public static ISolution findSecondBestByPenalty(Collection<? extends ISolution> solutions) {
//        ISolution best       = null;
//        ISolution secondBest = null;
//
//        for (ISolution solution : solutions) {
//            if (betterByPenalty(best, solution) == solution) {
//                secondBest = best;
//                best = solution;
//            } else if (betterByPenalty(secondBest, solution) == solution) {
//                secondBest = solution;
//            }
//        }
//
//        return secondBest;
//    }
}
