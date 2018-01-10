package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.util.IObserver;
import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractMetaheuristic<T> implements IMetaheuristic<T> {

    protected Solution<T> bestSolution;

    private Set<IObserver<T>> observers;

    public AbstractMetaheuristic() {
        observers = new HashSet<>();
    }

    @Override
    public Solution<T> getBestSolution() {
        return bestSolution;
    }

    @Override
    public void attachObserver(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detachObserver(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Solution<T> solution) {
        for (IObserver observer : observers) {
            observer.update(solution);
        }
    }
}
