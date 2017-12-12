package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.AbstractMetaheuristic;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.sa.cooling.ICoolingSchedule;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.util.Particle;
import com.dosilovic.hermanzvonimir.ecfjava.util.RealVector;
import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public abstract class AbstractPSO<T extends RealVector> extends AbstractMetaheuristic<T> implements IParticleSwarmOptimization<T> {

    protected int    maxIterations;
    protected double desiredFitness;
    protected double desiredPrecision;

    protected IProblem<T>      problem;
    protected ICoolingSchedule individualFactorSchedule;
    protected ICoolingSchedule socialFactorSchedule;
    protected ICoolingSchedule inertiaSchedule;
    protected ICoolingSchedule constrictionFactorSchedule;

    protected Solution<T> bestSolution;

    protected Collection<Particle<T>> initialParticles;

    public AbstractPSO(
        int maxIterations,
        double desiredFitness,
        double desiredPrecision,
        IProblem<T> problem,
        ICoolingSchedule individualFactorSchedule,
        ICoolingSchedule socialFactorSchedule,
        ICoolingSchedule inertiaSchedule,
        ICoolingSchedule constrictionFactorSchedule
    ) {
        this.maxIterations = maxIterations;
        this.desiredFitness = desiredFitness;
        this.desiredPrecision = desiredPrecision;
        this.problem = problem;
        this.individualFactorSchedule = individualFactorSchedule;
        this.socialFactorSchedule = socialFactorSchedule;
        this.inertiaSchedule = inertiaSchedule;
        this.constrictionFactorSchedule = constrictionFactorSchedule;
    }

    @Override
    public T run(Collection<T> initialParticles) {
        setInitialParticles(initialParticles);
        return run();
    }

    @Override
    public void setInitialParticles(Collection<T> initialParticles) {
        this.initialParticles = new ArrayList<>();
        for (T initialParticle : initialParticles) {
            this.initialParticles.add(new Particle<>(
                new Solution<>(initialParticle),
                new RealVector(initialParticle.getSize())
            ));
        }
    }

    @Override
    public T run() {
        long startTime = System.nanoTime();

        if (initialParticles == null) {
            throw new IllegalStateException("No initial particles");
        }

        Collection<Particle<T>> particles = initialParticles;

        double individualFactor;
        double socialFactor;
        double inertiaFactor;
        double constrictionFactor;

        int iteration;
        for (iteration = 1; iteration < maxIterations; iteration++) {
            evaluateParticles(particles);
            notifyObservers(bestSolution);

            individualFactor = individualFactorSchedule.getTemperature(iteration);
            socialFactor = socialFactorSchedule.getTemperature(iteration);
            inertiaFactor = inertiaSchedule.getTemperature(iteration);
            constrictionFactor = constrictionFactorSchedule.getTemperature(iteration);

            System.err.printf(
                "Iteration #%d (%s):\n" +
                "\tbestFitness        = %f\n" +
                "\tindividualFactor   = %f\n" +
                "\tsocialFactor       = %f\n" +
                "\tinertiaFactor      = %f\n" +
                "\tconstrictionFactor = %f\n\n",
                iteration,
                new Date(),
                bestSolution.getFitness(),
                individualFactor,
                socialFactor,
                inertiaFactor,
                constrictionFactor
            );

            if (Math.abs(bestSolution.getFitness() - desiredFitness) <= desiredPrecision) {
                System.err.println("Reached desired fitness.\n");
                break;
            }

            updateSpeedAndPosition(
                particles,
                individualFactor,
                socialFactor,
                inertiaFactor,
                constrictionFactor
            );

            if (iteration == maxIterations) {
                System.err.println("Max generations reached.\n");
                break;
            }
        }

        evaluateParticles(particles);
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

    private void evaluateParticles(Collection<Particle<T>> particles) {
        Solution<T> currentSolution;
        Solution<T> personalBestSolution;

        for (Particle<T> particle : particles) {
            currentSolution = particle.getCurrentSolution();
            personalBestSolution = particle.getPersonalBestSolution();

            currentSolution.evaluateFitness(problem, true);

            if (personalBestSolution == null || currentSolution.getFitness() >= personalBestSolution.getFitness()) {
                particle.setPersonalBestSolution(currentSolution);
            }

            if (bestSolution == null || currentSolution.getFitness() >= bestSolution.getFitness()) {
                bestSolution = currentSolution;
            }
        }
    }

    protected abstract void updateSpeedAndPosition(
        Collection<Particle<T>> particles,
        double individualFactor,
        double socialFactor,
        double inertiaFactor,
        double constrictionFactor
    );
}
