package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.AbstractMetaheuristic;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.util.RealVector;
import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;

import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

public class BasicPSO<T extends RealVector> extends AbstractMetaheuristic<T> implements IParticleSwarmOptimization<T> {

    private int         maxIterations;
    private double      desiredFitness;
    private double      desiredPrecision;
    private double      individualFactor;
    private double      socialFactor;
    private RealVector  minValue;
    private RealVector  maxValue;
    private RealVector  minSpeed;
    private RealVector  maxSpeed;
    private IProblem<T> problem;

    private Solution<T> bestSolution;

    private Collection<Particle<T>> initialParticles;

    private static final Random RAND = new Random();

    public BasicPSO(
        int maxIterations,
        double desiredFitness,
        double desiredPrecision,
        double individualFactor,
        double socialFactor,
        RealVector minValue,
        RealVector maxValue,
        RealVector minSpeed,
        RealVector maxSpeed,
        IProblem<T> problem
    ) {
        this.maxIterations = maxIterations;
        this.desiredFitness = desiredFitness;
        this.desiredPrecision = desiredPrecision;
        this.individualFactor = individualFactor;
        this.socialFactor = socialFactor;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.problem = problem;
    }

    @Override
    public T run(Collection<Particle<T>> initialParticles) {
        setInitialParticles(initialParticles);
        return run();
    }

    @Override
    public void setInitialParticles(Collection<Particle<T>> initialParticles) {
        this.initialParticles = initialParticles;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T run() {
        long startTime = System.nanoTime();

        if (initialParticles == null) {
            throw new IllegalStateException("No initial particles");
        }

        Collection<Particle<T>> particles = initialParticles;

        int iteration;
        for (iteration = 1; iteration <= maxIterations; iteration++) {
            Particle.evaluateFitness(particles, problem, true);
            Particle.updatePersonalBestByFitness(particles);
            bestSolution = Solution.betterByFitness(
                bestSolution,
                Particle.findBestByCurrentFitness(particles).getCurrentSolution()
            );

            notifyObservers(bestSolution);

            System.err.printf(
                "Iteration #%d (%s):\n" +
                "\tbestFitness            = %f\n\n",
                iteration,
                new Date(),
                bestSolution.getFitness()
            );

            if (Math.abs(bestSolution.getFitness() - desiredFitness) <= desiredPrecision) {
                System.err.println("Reached desired fitness.\n");
                break;
            }

            for (Particle<T> particle : particles) {
                T          currentRepresentative      = particle.getCurrentSolution().getRepresentative();
                T          nextRepresentative         = (T) currentRepresentative.clone();
                T          personalBestRepresentative = particle.getPersonalBestSolution().getRepresentative();
                T          globalBestRepresentative   = bestSolution.getRepresentative();
                RealVector speed                      = particle.getCurrentSpeed();

                for (int i = 0; i < speed.getDimension(); i++) {
                    double v  = speed.getValue(i);
                    double pb = personalBestRepresentative.getValue(i);
                    double x  = currentRepresentative.getValue(i);
                    double gb = globalBestRepresentative.getValue(i);
                    double vt = v +
                                individualFactor * RAND.nextDouble() * (pb - x) +
                                socialFactor * RAND.nextDouble() * (gb - x);

                    vt = Math.max(vt, minSpeed.getValue(i));
                    vt = Math.min(vt, maxSpeed.getValue(i));

                    double xt = x + vt;
                    xt = Math.max(xt, minValue.getValue(i));
                    xt = Math.min(xt, maxValue.getValue(i));

                    speed.setValue(i, vt);
                    nextRepresentative.setValue(i, xt);
                }

                particle.setCurrentSolution(new Solution<>(nextRepresentative));
            }

            if (iteration == maxIterations) {
                System.err.println("Max iterations reached.\n");
                break;
            }
        }

        Particle.evaluateFitness(particles, problem, true);
        Particle.updatePersonalBestByFitness(particles);
        bestSolution = Solution.betterByFitness(
            bestSolution,
            Particle.findBestByCurrentFitness(particles).getCurrentSolution()
        );

        notifyObservers(bestSolution);

        long     stopTime = System.nanoTime();
        Duration duration = Duration.ofNanos(stopTime - startTime);

        System.err.printf("Solution: %s\nFitness: %f\n", bestSolution.getRepresentative(), bestSolution.getFitness());
        System.err.printf("Iteration: #%d\n", iteration);
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

    @Override
    public Solution<T> getBestSolution() {
        return bestSolution;
    }
}
