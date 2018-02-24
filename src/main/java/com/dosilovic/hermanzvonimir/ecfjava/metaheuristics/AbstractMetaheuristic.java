package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.util.IObserver;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractMetaheuristic<T extends ISolution> implements IMetaheuristic<T> {

    protected T bestSolution;

    protected AtomicBoolean isStopped = new AtomicBoolean();

    private Set<IObserver<T>> observers;

    public AbstractMetaheuristic() {
        observers = new HashSet<>();
    }

    @Override
    public void stop() {
        isStopped.set(true);
    }

    @Override
    public T getBestSolution() {
        return bestSolution;
    }

    @Override
    public void setBestSolution(T bestSolution) {
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
