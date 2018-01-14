package com.dosilovic.hermanzvonimir.ecfjava.models.problems;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.RealVector;

public class MultiObjectiveProblem<T extends RealVector> { //} implements IProblem<T> {

//    private IProblem<T>[]                  problems;
//    private double                         alpha;
//    private double                         sigmaShare;
//    private double                         epsilon;
//    private boolean                        useSolutionSpace;
//    private SimpleMultiObjectiveProblem<T> simpleMultiObjectiveProblem;
//
//    private double[] fitnessValues;
//    private double[] penaltyValues;
//
//    public MultiObjectiveProblem(
//        double alpha,
//        double sigmaShare,
//        double epsilon,
//        boolean useSolutionSpace,
//        IProblem<T>... problems
//    ) {
//        this.problems = problems;
//        this.alpha = alpha;
//        this.sigmaShare = sigmaShare;
//        this.epsilon = epsilon;
//        this.useSolutionSpace = useSolutionSpace;
//        this.simpleMultiObjectiveProblem = new SimpleMultiObjectiveProblem<>(problems);
//    }
//
//    @Override
//    public double fitness(ISolution<T> individual) {
//        return simpleMultiObjectiveProblem.fitness(individual);
//    }
//
//    @Override
//    public double penalty(ISolution<T> individual) {
//        return simpleMultiObjectiveProblem.penalty(individual);
//    }
//
//    @Override
//    public double[] fitness(Collection<ISolution<T>> population) {
//        if (fitnessValues == null || fitnessValues.length != population.size()) {
//            fitnessValues = new double[population.size()];
//        }
//
//        evaluate(population, true, fitnessValues);
//
//        return fitnessValues;
//    }
//
//    @Override
//    public double[] penalty(Collection<ISolution<T>> population) {
//        if (penaltyValues == null || penaltyValues.length != population.size()) {
//            penaltyValues = new double[population.size()];
//        }
//
//        evaluate(population, false, penaltyValues);
//
//        return penaltyValues;
//    }
//
//    private void evaluate(Collection<ISolution<T>> population, boolean useFitness, double[] result) {
//        Map<T, Double> resultMap = new HashMap<>(population.size());
//
//        List<Collection<T>> sortedPopulation = notDominatedSorting(population, useFitness);
//        double              fmin             = population.size() + epsilon;
//
//        for (Collection<T> subPopulation : sortedPopulation) {
//
//            double newFmin = Double.MAX_VALUE;
//            for (T individual : subPopulation) {
//                double f  = fmin - epsilon;
//                double nc = nicheDensity(individual, subPopulation, useFitness);
//
//                f /= nc;
//
//                newFmin = Math.min(newFmin, f);
//
//                resultMap.put(individual, f);
//            }
//
//            fmin = newFmin;
//        }
//
//        int i = 0;
//        for (T individual : population) {
//            result[i++] = resultMap.get(individual);
//        }
//
//        return;
//    }
//
//    private boolean dominates(T a, T b, boolean useFitness) {
//        RealVector x = getResult(a, useFitness);
//        RealVector y = getResult(b, useFitness);
//
//        boolean dominatesForEachComponent = true;
//        for (int i = 0; i < x.getSize(); i++) {
//            if (useFitness) {
//                if (!(x.getValue(i) >= y.getValue(i))) {
//                    dominatesForEachComponent = false;
//                    break;
//                }
//            } else {
//                if (!(x.getValue(i) <= y.getValue(i))) {
//                    dominatesForEachComponent = false;
//                    break;
//                }
//            }
//        }
//
//        boolean dominatesForSingleComponent = false;
//        for (int i = 0; i < x.getSize(); i++) {
//            if (useFitness) {
//                if (y.getValue(i) > x.getValue(i)) {
//                    dominatesForSingleComponent = false;
//                    break;
//                } else if (x.getValue(i) > y.getValue(i)) {
//                    dominatesForSingleComponent = true;
//                }
//            } else {
//                if (y.getValue(i) < x.getValue(i)) {
//                    dominatesForSingleComponent = false;
//                    break;
//                } else if (x.getValue(i) < y.getValue(i)) {
//                    dominatesForSingleComponent = true;
//                }
//            }
//        }
//
//        return dominatesForEachComponent | dominatesForSingleComponent;
//    }
//
//    private Collection<T> findNotDominatedIndividuals(Collection<T> population, boolean useFitness) {
//        List<T> notDominated = new ArrayList<>();
//
//        for (T a : population) {
//            boolean hasDominator = false;
//
//            for (T b : population) {
//                if (dominates(b, a, useFitness)) {
//                    hasDominator = true;
//                    break;
//                }
//            }
//
//            if (!hasDominator) {
//                notDominated.add(a);
//            }
//        }
//
//        return notDominated;
//    }
//
//    private List<Collection<T>> notDominatedSorting(Collection<T> population, boolean useFitness) {
//        List<T>             populationList = new ArrayList<>(population);
//        List<Collection<T>> sorted         = new ArrayList<>();
//
//        while (!populationList.isEmpty()) {
//            Collection<T> notDominated = findNotDominatedIndividuals(populationList, useFitness);
//            if (notDominated.isEmpty()) {
//                sorted.add(populationList);
//                break;
//            }
//            sorted.add(notDominated);
//            populationList.removeAll(notDominated);
//        }
//
//        return sorted;
//    }
//
//    private double getDistance(T first, T second, boolean useFitness) {
//        RealVector a = first;
//        RealVector b = second;
//
//        if (!useSolutionSpace) {
//            a = getResult(first, useFitness);
//            b = getResult(second, useFitness);
//        }
//
//        return a.getDistance(b);
//    }
//
//    private double nicheDensity(T individual, Collection<T> population, boolean useFitness) {
//        double nc = 0;
//        double d;
//
//        for (T neighbor : population) {
//            d = getDistance(individual, neighbor, useFitness);
//            if (d < sigmaShare) {
//                nc += 1 - Math.pow(d / sigmaShare, alpha);
//            }
//        }
//
//        return nc;
//    }

//    private RealVector getResult(T solution, boolean useFitness) {
//        RealVector result = new RealVector(problems.length);
//        for (int i = 0; i < problems.length; i++) {
//            result.setValue(i, useFitness ? problems[i].fitness(solution) : problems[i].penalty(solution));
//        }
//        return result;
//    }
}
