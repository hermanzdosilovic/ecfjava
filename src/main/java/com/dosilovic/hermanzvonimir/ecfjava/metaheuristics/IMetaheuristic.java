package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.util.IObserver;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

public interface IMetaheuristic<T extends ISolution> {

    T run();

    void stop();

    T getBestSolution();

    void setBestSolution(T bestSolution);

    void attachObserver(IObserver<T> observer);

    void detachObserver(IObserver<T> observer);

    void notifyObservers();
}
