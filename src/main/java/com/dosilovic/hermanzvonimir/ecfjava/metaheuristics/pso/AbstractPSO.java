package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.AbstractMetaheuristic;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.util.RealVector;
import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;

import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public abstract class AbstractPSO<T extends RealVector> extends AbstractMetaheuristic<T> implements IParticleSwarmOptimization<T> {

    protected int         maxIterations;
    protected double      desiredFitness;
    protected double      desiredPrecision;
    protected double      individualFactor;
    protected double      socialFactor;
    protected RealVector  minValue;
    protected RealVector  maxValue;
    protected IProblem<T> problem;

    protected Solution<T> bestSolution;

    Collection<Particle<T>> initialParticles;

    public AbstractPSO(
        int maxIterations,
        double desiredFitness,
        double desiredPrecision,
        double individualFactor,
        double socialFactor,
        RealVector minValue,
        RealVector maxValue,
        IProblem<T> problem
    ) {
        this.maxIterations = maxIterations;
        this.desiredFitness = desiredFitness;
        this.desiredPrecision = desiredPrecision;
        this.individualFactor = individualFactor;
        this.socialFactor = socialFactor;
        this.minValue = minValue;
        this.maxValue = maxValue;
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
                "\tbestFitness = %f\n\n",
                iteration,
                new Date(),
                bestSolution.getFitness()
            );

            if (Math.abs(bestSolution.getFitness() - desiredFitness) <= desiredPrecision) {
                System.err.println("Reached desired fitness.\n");
                break;
            }

            for (Particle<T> particle : particles) {
                updateParticle(particle);
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

    protected abstract void updateParticle(Particle<T> particle);
}
