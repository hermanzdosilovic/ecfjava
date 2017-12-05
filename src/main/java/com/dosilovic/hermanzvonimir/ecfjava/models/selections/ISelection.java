package com.dosilovic.hermanzvonimir.ecfjava.models.selections;

import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;

import java.util.Collection;

public interface ISelection<T> {

    public Solution<T> select(Collection<Solution<T>> population);
}
