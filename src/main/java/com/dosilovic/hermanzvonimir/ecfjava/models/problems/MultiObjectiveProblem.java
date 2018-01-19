package com.dosilovic.hermanzvonimir.ecfjava.models.problems;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.RealVector;

import java.util.*;

public class MultiObjectiveProblem<T extends RealVector> implements IProblem<T> {

    private IProblem<T>[]                  problems;
    private double                         alpha;
    private double                         sigmaShare;
    private double                         epsilon;
    private boolean                        useSolutionSpace;
    private SimpleMultiObjectiveProblem<T> simpleMultiObjectiveProblem;

    private double[] fitnessValues;
    private double[] penaltyValues;

    private boolean  useFitness;
    private double[] values;

    public MultiObjectiveProblem(
        double alpha,
        double sigmaShare,
        double epsilon,
        boolean useSolutionSpace,
        IProblem<T>... problems
    ) {
        this.problems = problems;
        this.alpha = alpha;
        this.sigmaShare = sigmaShare;
        this.epsilon = epsilon;
        this.useSolutionSpace = useSolutionSpace;
        this.simpleMultiObjectiveProblem = new SimpleMultiObjectiveProblem<>(problems);
    }

    @Override
    public double fitness(ISolution<T> individual) {
        return simpleMultiObjectiveProblem.fitness(individual);
    }

    @Override
    public double penalty(ISolution<T> individual) {
        return simpleMultiObjectiveProblem.penalty(individual);
    }

    @Override
    public double[] fitness(Collection<? extends ISolution<T>> population) {
        if (fitnessValues == null || fitnessValues.length != population.size()) {
            fitnessValues = new double[population.size()];
        }

        useFitness = true;
        values = fitnessValues;
        evaluate(population);

        return fitnessValues;
    }

    @Override
    public double[] penalty(Collection<? extends ISolution<T>> population) {
        if (penaltyValues == null || penaltyValues.length != population.size()) {
            penaltyValues = new double[population.size()];
        }

        useFitness = false;
        values = penaltyValues;
        evaluate(population);

        return penaltyValues;
    }

    private void evaluate(Collection<? extends ISolution<T>> population) {
        Map<ISolution<T>, Double> resultMap = new HashMap<>(population.size());

        List<Collection<ISolution<T>>> sortedPopulation = notDominatedSorting(population);

        double fmin = population.size() + epsilon;

        for (Collection<? extends ISolution<T>> subPopulation : sortedPopulation) {
            double nextFmin = Double.MAX_VALUE;
            for (ISolution<T> individual : subPopulation) {
                double f  = fmin - epsilon;
                double nc = nicheDensity(individual, subPopulation);
                f /= nc;
                nextFmin = Math.min(nextFmin, f);
                resultMap.put(individual, f);
            }

            fmin = nextFmin;
        }

        int offset = 0;
        for (ISolution<T> individual : population) {
            values[offset++] = resultMap.get(individual);
        }
    }

    private boolean dominates(ISolution<T> a, ISolution<T> b) {
        RealVector x = getResult(a);
        RealVector y = getResult(b);

        for (int i = 0; i < x.getSize(); i++) {
            if (useFitness) {
                if (y.getValue(i) > x.getValue(i)) {
                    return false;
                }
            } else {
                if (y.getValue(i) < x.getValue(i)) {
                    return false;
                }
            }
        }

        for (int i = 0; i < x.getSize(); i++) {
            if (useFitness) {
                if (x.getValue(i) > y.getValue(i)) {
                    return true;
                }
            } else {
                if (x.getValue(i) < y.getValue(i)) {
                    return true;
                }
            }
        }

        return false;
    }

    private Collection<ISolution<T>> findNotDominatedIndividuals(Collection<ISolution<T>> population) {
        List<ISolution<T>> notDominated = new ArrayList<>();

        for (ISolution<T> a : population) {
            boolean hasDominator = false;
            for (ISolution<T> b : population) {
                if (dominates(b, a)) {
                    hasDominator = true;
                    break;
                }
            }

            if (!hasDominator) {
                notDominated.add(a);
            }
        }

        return notDominated;
    }

    private List<Collection<ISolution<T>>> notDominatedSorting(Collection<? extends ISolution<T>> population) {
        List<ISolution<T>>             populationList = new ArrayList<>(population);
        List<Collection<ISolution<T>>> sorted         = new ArrayList<>();

        while (!populationList.isEmpty()) {
            Collection<ISolution<T>> notDominated = findNotDominatedIndividuals(populationList);
            if (notDominated.isEmpty()) {
                sorted.add(populationList);
                break;
            }
            sorted.add(notDominated);
            populationList.removeAll(notDominated);
        }

        return sorted;
    }

    private double getDistance(ISolution<T> first, ISolution<T> second) {
        RealVector a = first.getRepresentative();
        RealVector b = second.getRepresentative();

        if (!useSolutionSpace) {
            a = getResult(first);
            b = getResult(second);
        }

        return a.getDistance(b);
    }

    private double nicheDensity(ISolution<T> individual, Collection<? extends ISolution<T>> population) {
        double nc = 0;
        for (ISolution<T> neighbor : population) {
            double d = getDistance(individual, neighbor);
            if (d < sigmaShare) {
                nc += 1 - Math.pow(d / sigmaShare, alpha);
            }
        }
        return nc;
    }

    private RealVector getResult(ISolution<T> solution) {
        RealVector result = new RealVector(problems.length);
        for (int i = 0; i < problems.length; i++) {
            result.setValue(i, useFitness ? problems[i].fitness(solution) : problems[i].penalty(solution));
        }
        return result;
    }
}
