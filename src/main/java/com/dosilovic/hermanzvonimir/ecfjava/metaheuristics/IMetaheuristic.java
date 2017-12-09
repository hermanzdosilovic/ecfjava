package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.util.IObserver;
import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;

public interface IMetaheuristic<T> {

    public T run();

    public Solution<T> getBestSolution();

    public void attachObserver(IObserver observer);

    public void detachObserver(IObserver observer);

    public void notifyObservers(Solution<T> solution);
}
