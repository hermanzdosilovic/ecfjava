package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.AbstractPopulationMetaheuristic;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.topologies.ITopology;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.Solutions;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.particle.Particle;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.particle.Particles;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.RealVector;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public abstract class AbstractPSO<T extends RealVector> extends AbstractPopulationMetaheuristic<T> implements IParticleSwarmOptimization<T> {

    protected int          maxIterations;
    protected double       desiredFitness;
    protected double       desiredPrecision;
    protected boolean      isFullyInformed;
    protected IProblem<T>  problem;
    protected ITopology<T> topology;

    private List<Particle<T>> particles;

    protected int iteration;

    public AbstractPSO(
        int maxIterations,
        double desiredFitness,
        double desiredPrecision,
        boolean isFullyInformed,
        IProblem<T> problem,
        ITopology<T> topology
    ) {
        this.maxIterations = maxIterations;
        this.desiredFitness = desiredFitness;
        this.desiredPrecision = desiredPrecision;
        this.isFullyInformed = isFullyInformed;
        this.problem = problem;
        this.topology = topology;
    }

    @Override
    public void setPopulation(Collection<ISolution<T>> population) {
        if (population == null || population.isEmpty()) {
            throw new IllegalArgumentException("Population cannot be null or empty");
        }

        particles = new ArrayList<>();
        for (ISolution<T> individual : population) {
            particles.add((Particle<T>) individual);
        }
        topology.updateTopology(particles);

        this.population = new ArrayList<>(population);
    }

    @Override
    public void setBestSolution(ISolution<T> bestSolution) {
        if (bestSolution == null) {
            throw new IllegalArgumentException("Best solution cannot be null");
        } else if (this.bestSolution != bestSolution) {
            this.bestSolution = bestSolution.copy();
        }
        notifyObservers();
    }

    @SuppressWarnings("unchecked")
    @Override
    public ISolution<T> run() {
        long startTime = System.nanoTime();

        setPopulation(initialPopulation);
        isStopped.set(false);
        bestSolution = null;

        for (iteration = 1; iteration <= maxIterations; iteration++) {
            Solutions.updateFitness(particles, problem);
            Particles.updateBestSolutionByFitness(particles);
            setBestSolution(Solutions.betterByFitness(bestSolution, Solutions.findBestByFitness(particles)));

            System.err.printf(
                "Iteration #%d (%s)\n\tbestFitness: %f\n\n",
                iteration, new Date(), bestSolution.getFitness()
            );

            if (Math.abs(bestSolution.getFitness() - desiredFitness) <= desiredPrecision) {
                System.err.println("Reached desired fitness.\n");
                break;
            }

            if (isStopped.get()) {
                System.err.println("Algorithm stopped.\n");
                break;
            }

            for (Particle<T> particle : particles) {
                T          representative     = particle.getSolution().getRepresentative();
                RealVector speed              = particle.getSpeed();
                T          nextRepresentative = (T) representative.copy();

                updateSpeed(iteration, speed, calculateNeighboursContribution(iteration, particle));
                limitSpeed(iteration, speed);

                for (int i = 0; i < speed.getSize(); i++) {
                    nextRepresentative.setValue(i, representative.getValue(i) + speed.getValue(i));
                }

                ISolution<T> nextSolution = particle.getSolution().copy();
                nextSolution.setRepresentative(nextRepresentative);
                particle.setSolution(nextSolution);
            }

            if (iteration == maxIterations) {
                System.err.println("Max iterations reached.\n");
                break;
            }
        }

        Solutions.updateFitness(particles, problem);
        Particles.updateBestSolutionByFitness(particles);
        setBestSolution(Solutions.betterByFitness(bestSolution, Solutions.findBestByFitness(particles)));

        Duration duration = Duration.ofNanos(System.nanoTime() - startTime);
        System.err.printf(
            "Time: %02d:%02d:%02d.%03d\n\n",
            duration.toHoursPart(), duration.toMinutesPart(), duration.toSecondsPart(), duration.toMillisPart()
        );
        System.err.println(bestSolution);
        System.err.println();
        System.err.flush();

        return bestSolution;
    }

    protected abstract double getIndividualFactor(int iteration);

    protected abstract double getSocialFactor(int iteration);

    protected abstract void updateSpeed(int iteration, RealVector speed, RealVector neighboursContribution);

    protected abstract void limitSpeed(int iteration, RealVector speed);

    private RealVector calculateNeighboursContribution(int iteration, Particle<T> particle) {
        double individualFactor = getIndividualFactor(iteration);
        double socialFactor     = getSocialFactor(iteration);

        T          representative         = particle.getSolution().getRepresentative();
        RealVector neighboursContribution = new RealVector(representative.getSize());

        Collection<Particle<T>> neighbours = topology.getNeighbours(particle);

        if (isFullyInformed) {
            double factor = (individualFactor + socialFactor) / neighbours.size();
            for (int i = 0; i < neighboursContribution.getSize(); i++) {
                double ci = 0;
                double xi = representative.getValue(i);

                for (Particle<T> neighbour : neighbours) {
                    double pbi = neighbour.getBestSolution().getRepresentative().getValue(i);
                    ci += factor * RAND.nextDouble() * (pbi - xi);
                }

                neighboursContribution.setValue(i, ci);
            }
        } else {
            T personalBestRepresentative = particle.getBestSolution().getRepresentative();
            T localBestRepresentative =
                Particles.findBestByBestFitness(neighbours).getBestSolution().getRepresentative();

            for (int i = 0; i < neighboursContribution.getSize(); i++) {
                double pbi = personalBestRepresentative.getValue(i);
                double xi  = representative.getValue(i);
                double lbi = localBestRepresentative.getValue(i);

                double ci = individualFactor * RAND.nextDouble() * (pbi - xi) +
                            socialFactor * RAND.nextDouble() * (lbi - xi);

                neighboursContribution.setValue(i, ci);
            }
        }

        return neighboursContribution;
    }
}
