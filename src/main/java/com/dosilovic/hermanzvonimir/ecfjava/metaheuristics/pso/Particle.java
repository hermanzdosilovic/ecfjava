package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso;

import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.util.RealVector;
import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;

import java.util.Collection;
import java.util.Objects;

public class Particle<T> implements Comparable<Particle<T>> {

    private Solution<T> currentSolution;
    private Solution<T> personalBestSolution;
    private RealVector  currentSpeed;

    public Particle(Solution<T> initialSolution, RealVector initialSpeed) {
        this.currentSolution = initialSolution;
        this.currentSpeed = initialSpeed;
    }

    public Solution<T> getCurrentSolution() {
        return currentSolution;
    }

    public void setCurrentSolution(Solution<T> currentSolution) {
        this.currentSolution = currentSolution;
    }

    public Solution<T> getPersonalBestSolution() {
        return personalBestSolution;
    }

    public void setPersonalBestSolution(Solution<T> personalBestSolution) {
        this.personalBestSolution = personalBestSolution;
    }

    public RealVector getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(RealVector currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public void evaluateFitness(IProblem<T> problem, boolean updateIfHasFitness) {
        currentSolution.evaluateFitness(problem, updateIfHasFitness);
    }

    public void evaluateFitness(IProblem<T> problem) {
        evaluateFitness(problem, false);
    }

    public void evaluatePenalty(IProblem<T> problem, boolean updateIfHasPenalty) {
        currentSolution.evaluatePenalty(problem, updateIfHasPenalty);
    }

    public void evaluatePenalty(IProblem<T> problem) {
        evaluatePenalty(problem, false);
    }

    public void updatePersonalBestByFitness() {
        personalBestSolution = Solution.betterByFitness(personalBestSolution, currentSolution);
    }

    public void updatePersonalBestByPenalty() {
        personalBestSolution = Solution.betterByPenalty(personalBestSolution, currentSolution);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentSolution, personalBestSolution);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Particle<?> particle = (Particle<?>) o;
        return Objects.equals(currentSolution, particle.currentSolution) &&
               Objects.equals(personalBestSolution, particle.personalBestSolution);
    }

    @Override
    public int compareTo(Particle<T> o) {
        return personalBestSolution.compareTo(o.personalBestSolution);
    }

    public static <T> void evaluateFitness(
        Collection<Particle<T>> particles,
        IProblem<T> problem,
        boolean updateIfHasFitness
    ) {
        for (Particle<T> particle : particles) {
            particle.evaluateFitness(problem, updateIfHasFitness);
        }
    }

    public static <T> void evaluateFitness(Collection<Particle<T>> particles, IProblem<T> problem) {
        evaluateFitness(particles, problem, false);
    }

    public static <T> void evaluatePenalty(
        Collection<Particle<T>> particles,
        IProblem<T> problem,
        boolean updateIfHasPenalty
    ) {
        for (Particle<T> particle : particles) {
            particle.evaluatePenalty(problem, updateIfHasPenalty);
        }
    }

    public static <T> void evaluatePenalty(Collection<Particle<T>> particles, IProblem<T> problem) {
        evaluatePenalty(particles, problem, false);
    }

    public static <T> void updatePersonalBestByFitness(Collection<Particle<T>> particles) {
        for (Particle<T> particle : particles) {
            particle.updatePersonalBestByFitness();
        }
    }

    public static <T> void updatePersonalBestByPenalty(Collection<Particle<T>> particles) {
        for (Particle<T> particle : particles) {
            particle.updatePersonalBestByPenalty();
        }
    }

    public static <T> Particle<T> betterByCurrentFitness(Particle<T> first, Particle<T> second) {
        if (first == null) {
            return second;
        } else if (second == null) {
            return first;
        } else if (Solution.betterByFitness(first.currentSolution, second.currentSolution) == first.currentSolution) {
            return first;
        }
        return second;
    }

    public static <T> Particle<T> worstByCurrentFitness(Particle<T> first, Particle<T> second) {
        if (betterByCurrentFitness(first, second) == first) {
            return second;
        }
        return first;
    }

    public static <T> Particle<T> betterByPersonalBestFitness(Particle<T> first, Particle<T> second) {
        if (first == null) {
            return second;
        } else if (second == null) {
            return first;
        } else if (Solution.betterByFitness(first.personalBestSolution, second.personalBestSolution) ==
                   first.personalBestSolution) {
            return first;
        }
        return second;
    }

    public static <T> Particle<T> worstByPersonalBestFitness(Particle<T> first, Particle<T> second) {
        if (betterByPersonalBestFitness(first, second) == first) {
            return second;
        }
        return first;
    }

    public static <T> Particle<T> betterByCurrentPenalty(Particle<T> first, Particle<T> second) {
        if (first == null) {
            return second;
        } else if (second == null) {
            return first;
        } else if (Solution.betterByPenalty(first.currentSolution, second.currentSolution) == first.currentSolution) {
            return first;
        }
        return second;
    }

    public static <T> Particle<T> worstByCurrentPenalty(Particle<T> first, Particle<T> second) {
        if (betterByCurrentPenalty(first, second) == first) {
            return second;
        }
        return first;
    }

    public static <T> Particle<T> betterByPersonalBestPenalty(Particle<T> first, Particle<T> second) {
        if (first == null) {
            return second;
        } else if (second == null) {
            return first;
        } else if (Solution.betterByPenalty(first.personalBestSolution, second.personalBestSolution) ==
                   first.personalBestSolution) {
            return first;
        }
        return second;
    }

    public static <T> Particle<T> worstByPersonalBestPenalty(Particle<T> first, Particle<T> second) {
        if (betterByPersonalBestPenalty(first, second) == first) {
            return second;
        }
        return first;
    }

    public static <T> Particle<T> findBestByCurrentFitness(Collection<Particle<T>> particles) {
        Particle<T> bestParticle = null;
        for (Particle<T> particle : particles) {
            bestParticle = betterByCurrentFitness(bestParticle, particle);
        }
        return bestParticle;
    }

    public static <T> Particle<T> findBestByPersonalBestFitness(Collection<Particle<T>> particles) {
        Particle<T> bestParticle = null;
        for (Particle<T> particle : particles) {
            bestParticle = betterByPersonalBestFitness(bestParticle, particle);
        }
        return bestParticle;
    }

    public static <T> Particle<T> findBestByCurrentPenalty(Collection<Particle<T>> particles) {
        Particle<T> bestParticle = null;
        for (Particle<T> particle : particles) {
            bestParticle = betterByCurrentPenalty(bestParticle, particle);
        }
        return bestParticle;
    }

    public static <T> Particle<T> findBestByPersonalBestPenalty(Collection<Particle<T>> particles) {
        Particle<T> bestParticle = null;
        for (Particle<T> particle : particles) {
            bestParticle = betterByPersonalBestPenalty(bestParticle, particle);
        }
        return bestParticle;
    }
}
