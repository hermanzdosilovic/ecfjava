package com.dosilovic.hermanzvonimir.ecfjava.models.solutions.particle.util;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.particle.Particle;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.util.Solutions;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.RealVector;

import java.util.Collection;

public final class Particles {

    public static <T extends Particle> void updateBestSolutionByFitness(T particle) {
        RealVector particleSolution = particle.getSolution();
        RealVector particleBestSolution = particle.getBestSolution();

        RealVector newBestSolution = Solutions.betterByFitness(particleSolution, particleBestSolution);
        if (newBestSolution == particleSolution) {
            if (particleBestSolution == null) {
                particle.setBestSolution(particleSolution.copy());
            } else {
                for (int i = 0, size = particleSolution.getSize(); i < size; i++) {
                    particleBestSolution.setValue(i, particleSolution.getValue(i));
                    particleBestSolution.setFitness(particleSolution.getFitness());
                    particleBestSolution.setPenalty(particleSolution.getPenalty());
                }
            }
        }
    }

    public static <T extends Particle> void updateBestSolutionByFitness(Collection<T> particles) {
        for (T particle : particles) {
            updateBestSolutionByFitness(particle);
        }
    }

//    public static  void updateBestSolutionByPenalty(Collection<Particle> particles) {
//        for (Particle particle : particles) {
//            particle.updateBestSolutionByPenalty();
//        }
//    }
//
//    public static  Particle betterByFitness(Particle first, Particle second) {
//        if (first == null) {
//            return second;
//        } else if (second == null) {
//            return first;
//        } else if (Solutions.betterByFitness(first.getSolution(), second.getSolution()) ==
//                   first.getSolution()) {
//            return first;
//        }
//        return second;
//    }
//
//    public static  Particle worstByFitness(Particle first, Particle second) {
//        if (betterByFitness(first, second) == first) {
//            return second;
//        }
//        return first;
//    }

    public static <T extends Particle> T betterByBestFitness(T first, T second) {
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

    //    public static  Particle worstByBestFitness(Particle first, Particle second) {
//        if (betterByBestFitness(first, second) == first) {
//            return second;
//        }
//        return first;
//    }
//
//    public static  Particle betterByPenalty(Particle first, Particle second) {
//        if (first == null) {
//            return second;
//        } else if (second == null) {
//            return first;
//        } else if (Solutions.betterByPenalty(first.getSolution(), second.getSolution()) ==
//                   first.getSolution()) {
//            return first;
//        }
//        return second;
//    }
//
//    public static  Particle worstByPenalty(Particle first, Particle second) {
//        if (betterByPenalty(first, second) == first) {
//            return second;
//        }
//        return first;
//    }
//
//    public static  Particle betterByBestPenalty(Particle first, Particle second) {
//        if (first == null) {
//            return second;
//        } else if (second == null) {
//            return first;
//        } else if (Solutions.betterByPenalty(first.getBestSolution(), second.getBestSolution()) ==
//                   first.getBestSolution()) {
//            return first;
//        }
//        return second;
//    }
//
//    public static  Particle worstByBestPenalty(Particle first, Particle second) {
//        if (betterByBestPenalty(first, second) == first) {
//            return second;
//        }
//        return first;
//    }
//
//    public static  Particle findBestByFitness(Collection<Particle> particles) {
//        Particle best = null;
//        for (Particle particle : particles) {
//            best = betterByFitness(best, particle);
//        }
//        return best;
//    }
//
    public static <T extends Particle> T findBestByBestFitness(Collection<T> particles) {
        T best = null;
        for (T particle : particles) {
            best = betterByBestFitness(best, particle);
        }
        return best;
    }
//
//    public static  Particle findBestByPenalty(Collection<Particle> particles) {
//        Particle best = null;
//        for (Particle particle : particles) {
//            best = betterByPenalty(best, particle);
//        }
//        return best;
//    }
//
//    public static  Particle findBestByBestPenalty(Collection<Particle> particles) {
//        Particle best = null;
//        for (Particle particle : particles) {
//            best = betterByBestPenalty(best, particle);
//        }
//        return best;
//    }
}
