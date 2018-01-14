package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.util.IObserver;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public abstract class AbstractMetaheuristic<T> implements IMetaheuristic<T> {

    protected ISolution<T> bestSolution;

    private Set<IObserver<T>> observers;

    protected static final Random RAND = new Random();

    public AbstractMetaheuristic() {
        observers = new HashSet<>();
    }

    @Override
    public ISolution<T> getBestSolution() {
        return bestSolution;
    }

    @Override
    public void setBestSolution(ISolution<T> bestSolution) {
        if (bestSolution == null) {
            throw new IllegalArgumentException("Best solution cannot be null");
        }
        this.bestSolution = bestSolution;
        notifyObservers();
    }

    @Override
    public void attachObserver(IObserver<T> observer) {
        observers.add(observer);
    }

    @Override
    public void detachObserver(IObserver<T> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (IObserver<T> observer : observers) {
            observer.update(this);
        }
    }
}
