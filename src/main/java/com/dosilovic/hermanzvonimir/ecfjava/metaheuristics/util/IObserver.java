package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.util;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.IMetaheuristic;

public interface IObserver<T> {

    public void update(IMetaheuristic<T> metaheuristic);
}
