package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.util;

import com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.IMetaheuristic;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

public interface IObserver<T extends ISolution> {

    void update(IMetaheuristic<T> metaheuristic);
}
