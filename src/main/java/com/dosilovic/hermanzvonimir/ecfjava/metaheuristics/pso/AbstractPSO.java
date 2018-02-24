package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.AbstractPopulationMetaheuristic;
import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.pso.topologies.ITopology;
import com.dosilovic.hermanzvonimir.ecfjava.models.problems.IProblem;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.particle.Particle;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.particle.util.Particles;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.util.Solutions;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.RealVector;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.IRandom;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.Random;

import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public abstract class AbstractPSO<T extends Particle> extends AbstractPopulationMetaheuristic<T> implements IParticleSwarmOptimization<T> {

    protected int          maxIterations;
    protected double       desiredFitness;
    protected double       desiredPrecision;
    protected boolean      isFullyInformed;
    protected IProblem<T>  problem;
    protected ITopology<T> topology;

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

    @SuppressWarnings("unchecked")
    @Override
    public T run() {
        long startTime = System.nanoTime();

        setPopulation(initialPopulation);
        isStopped.set(false);
        bestSolution = null;

        topology.updateTopology(population);

        for (iteration = 1; iteration <= maxIterations; iteration++) {
            problem.updateFitness(population);
            Particles.updateBestSolutionByFitness(population);
            setBestSolution(Particles.findBestByBestFitness(population));

            System.err.printf(
                "Iteration #%d (%s)\n\tbestFitness: %f\n\n",
                iteration, new Date(), bestSolution.getBestSolution().getFitness()
            );

            if (Math.abs(bestSolution.getBestSolution().getFitness() - desiredFitness) <= desiredPrecision) {
                System.err.println("Reached desired fitness.\n");
                break;
            }

            if (isStopped.get()) {
                System.err.println("Algorithm stopped.\n");
                break;
            }

            for (T particle : population) {
                RealVector speed = particle.getSpeed();

                updateSpeed(iteration, speed, calculateNeighboursContribution(iteration, particle));
                limitSpeed(iteration, speed);

                RealVector solution = particle.getSolution();
                for (int i = 0, size = speed.getSize(); i < size; i++) {
                    solution.setValue(
                        i, solution.getValue(i) + speed.getValue(i)
                    );
                }
            }

            if (iteration == maxIterations) {
                System.err.println("Max iterations reached.\n");
                break;
            }
        }

        problem.updateFitness(population);
        Particles.updateBestSolutionByFitness(population);
        setBestSolution(Particles.findBestByBestFitness(population));

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

    private RealVector calculateNeighboursContribution(int iteration, T particle) {
        double individualFactor = getIndividualFactor(iteration);
        double socialFactor     = getSocialFactor(iteration);

        RealVector personalSolution       = particle.getSolution();
        RealVector neighboursContribution = new RealVector(personalSolution.getSize());

        Collection<T> neighbours = topology.getNeighbours(particle);

        IRandom random = Random.getRandom();

        if (isFullyInformed) {
            double factor = (individualFactor + socialFactor) / neighbours.size();

            for (int i = 0, size = neighboursContribution.getSize(); i < size; i++) {
                double ci = 0;
                double xi = personalSolution.getValue(i);

                for (T neighbour : neighbours) {
                    double pbi = neighbour.getBestSolution().getValue(i);
                    ci += factor * random.nextDouble() * (pbi - xi);
                }

                neighboursContribution.setValue(i, ci);
            }
        } else {
            RealVector personalBestSolution = particle.getBestSolution();
            RealVector localBestSolution    = Particles.findBestByBestFitness(neighbours).getBestSolution();

            for (int i = 0, size = neighboursContribution.getSize(); i < size; i++) {
                double pbi = personalBestSolution.getValue(i);
                double xi  = personalSolution.getValue(i);
                double lbi = localBestSolution.getValue(i);

                double ci = individualFactor * random.nextDouble() * (pbi - xi) +
                            socialFactor * random.nextDouble() * (lbi - xi);

                neighboursContribution.setValue(i, ci);
            }
        }

        return neighboursContribution;
    }
}
