package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics.util;

import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;

public interface IObserver<T> {

    public void update(Solution<T> solution);
}
