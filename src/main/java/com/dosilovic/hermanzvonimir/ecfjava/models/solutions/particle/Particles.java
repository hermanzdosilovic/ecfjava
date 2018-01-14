package com.dosilovic.hermanzvonimir.ecfjava.models.solutions.particle;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.Solutions;

import java.util.Collection;

public final class Particles {

    public static <T> void updateBestSolutionByFitness(Collection<Particle<T>> particles) {
        for (Particle<T> particle : particles) {
            particle.updateBestSolutionByFitness();
        }
    }

    public static <T> void updateBestSolutionByPenalty(Collection<Particle<T>> particles) {
        for (Particle<T> particle : particles) {
            particle.updateBestSolutionByPenalty();
        }
    }

    public static <T> Particle<T> betterByFitness(Particle<T> first, Particle<T> second) {
        if (first == null) {
            return second;
        } else if (second == null) {
            return first;
        } else if (Solutions.betterByFitness(first.getSolution(), second.getSolution()) ==
                   first.getSolution()) {
            return first;
        }
        return second;
    }

    public static <T> Particle worstByFitness(Particle<T> first, Particle<T> second) {
        if (betterByFitness(first, second) == first) {
            return second;
        }
        return first;
    }

    public static <T> Particle betterByBestFitness(Particle<T> first, Particle<T> second) {
        if (first == null) {
            return second;
        } else if (second == null) {
            return first;
        } else if (Solutions.betterByFitness(first.getBestSolution(), second.getBestSolution()) ==
                   first.getBestSolution()) {
            return first;
        }
        return second;
    }

    public static <T> Particle worstByBestFitness(Particle<T> first, Particle<T> second) {
        if (betterByBestFitness(first, second) == first) {
            return second;
        }
        return first;
    }

    public static <T> Particle betterByPenalty(Particle<T> first, Particle<T> second) {
        if (first == null) {
            return second;
        } else if (second == null) {
            return first;
        } else if (Solutions.betterByPenalty(first.getSolution(), second.getSolution()) ==
                   first.getSolution()) {
            return first;
        }
        return second;
    }

    public static <T> Particle worstByPenalty(Particle<T> first, Particle<T> second) {
        if (betterByPenalty(first, second) == first) {
            return second;
        }
        return first;
    }

    public static <T> Particle<T> betterByBestPenalty(Particle<T> first, Particle<T> second) {
        if (first == null) {
            return second;
        } else if (second == null) {
            return first;
        } else if (Solutions.betterByPenalty(first.getBestSolution(), second.getBestSolution()) ==
                   first.getBestSolution()) {
            return first;
        }
        return second;
    }

    public static <T> Particle<T> worstByBestPenalty(Particle<T> first, Particle<T> second) {
        if (betterByBestPenalty(first, second) == first) {
            return second;
        }
        return first;
    }

    public static <T> Particle<T> findBestByFitness(Collection<Particle<T>> particles) {
        Particle<T> best = null;
        for (Particle<T> particle : particles) {
            best = betterByFitness(best, particle);
        }
        return best;
    }

    public static <T> Particle<T> findBestByBestFitness(Collection<Particle<T>> particles) {
        Particle<T> best = null;
        for (Particle<T> particle : particles) {
            best = betterByBestFitness(best, particle);
        }
        return best;
    }

    public static <T> Particle<T> findBestByPenalty(Collection<Particle<T>> particles) {
        Particle<T> best = null;
        for (Particle<T> particle : particles) {
            best = betterByPenalty(best, particle);
        }
        return best;
    }

    public static <T> Particle<T> findBestByBestPenalty(Collection<Particle<T>> particles) {
        Particle<T> best = null;
        for (Particle<T> particle : particles) {
            best = betterByBestPenalty(best, particle);
        }
        return best;
    }
}
