package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.AbstractMetaheuristic;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.topologies.ITopology;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.util.RealVector;
import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;

import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

public abstract class AbstractPSO<T extends RealVector> extends AbstractMetaheuristic<T> implements IParticleSwarmOptimization<T> {

    protected int          maxIterations;
    protected double       desiredFitness;
    protected double       desiredPrecision;
    protected boolean      isFullyInformed;
    protected double       individualFactor;
    protected double       socialFactor;
    protected RealVector   minValue;
    protected RealVector   maxValue;
    protected RealVector   minSpeed;
    protected RealVector   maxSpeed;
    protected IProblem<T>  problem;
    protected ITopology<T> topology;

    protected Solution<T> bestSolution;

    protected Collection<Particle<T>> initialParticles;

    private static final Random RAND = new Random();

    public AbstractPSO(
        int maxIterations,
        double desiredFitness,
        double desiredPrecision,
        boolean isFullyInformed,
        double individualFactor,
        double socialFactor,
        RealVector minValue,
        RealVector maxValue,
        RealVector minSpeed,
        RealVector maxSpeed,
        IProblem<T> problem,
        ITopology<T> topology
    ) {
        this.maxIterations = maxIterations;
        this.desiredFitness = desiredFitness;
        this.desiredPrecision = desiredPrecision;
        this.isFullyInformed = isFullyInformed;
        this.individualFactor = individualFactor;
        this.socialFactor = socialFactor;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.problem = problem;
        this.topology = topology;
    }

    @Override
    public T run(Collection<Particle<T>> initialParticles) {
        setInitialParticles(initialParticles);
        return run();
    }

    @Override
    public void setInitialParticles(Collection<Particle<T>> initialParticles) {
        this.initialParticles = initialParticles;
        topology.updateTopology(initialParticles);
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
                RealVector speed                 = particle.getCurrentSpeed();
                T          currentRepresentative = particle.getCurrentSolution().getRepresentative();
                T          nextRepresentative    = (T) currentRepresentative.clone();

                updateSpeed(iteration, speed, calculateNeighboursContribution(iteration, particle));

                for (int i = 0; i < speed.getSize(); i++) {
                    double v = speed.getValue(i);
                    v = Math.max(v, minSpeed.getValue(i));
                    v = Math.min(v, maxSpeed.getValue(i));
                    speed.setValue(i, v);

                    double x = currentRepresentative.getValue(i) + v;
                    x = Math.max(x, minValue.getValue(i));
                    x = Math.min(x, maxValue.getValue(i));
                    nextRepresentative.setValue(i, x);
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

    protected double getIndividualFactor(int iteration) {
        return individualFactor;
    }

    protected double getSocialFactor(int iteration) {
        return socialFactor;
    }

    protected abstract void updateSpeed(int iteration, RealVector speed, RealVector neighboursContribution);

    private RealVector calculateNeighboursContribution(int iteration, Particle<T> particle) {
        double individualFactor = getIndividualFactor(iteration);
        double socialFactor     = getSocialFactor(iteration);

        T          currentRepresentative  = particle.getCurrentSolution().getRepresentative();
        RealVector neighboursContribution = (RealVector) currentRepresentative.clone();

        Collection<Particle<T>> neighbours = topology.getNeighbours(particle);

        if (isFullyInformed) {
            double factor = (individualFactor + socialFactor) / neighbours.size();

            double pbi, xi, ci;
            for (int i = 0; i < neighboursContribution.getSize(); i++) {
                ci = 0;
                xi = currentRepresentative.getValue(i);

                for (Particle<T> neighbour : neighbours) {
                    pbi = neighbour.getPersonalBestSolution().getRepresentative().getValue(i);
                    ci += factor * RAND.nextDouble() * (pbi - xi);
                }

                neighboursContribution.setValue(i, ci);
            }
        } else {
            T personalBestRepresentative = particle.getPersonalBestSolution().getRepresentative();
            T localBestRepresentative =
                Particle.findBestByPersonalBestFitness(neighbours).getPersonalBestSolution().getRepresentative();

            double pbi, xi, lbi, ci;
            for (int i = 0; i < neighboursContribution.getSize(); i++) {
                pbi = personalBestRepresentative.getValue(i);
                xi = currentRepresentative.getValue(i);
                lbi = localBestRepresentative.getValue(i);

                ci = individualFactor * RAND.nextDouble() * (pbi - xi) +
                     socialFactor * RAND.nextDouble() * (lbi - xi);

                neighboursContribution.setValue(i, ci);
            }
        }

        return neighboursContribution;
    }
}
