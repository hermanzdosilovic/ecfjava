package com.dosilovic.hermanzvonimir.ecfjava.models.mutations;

import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;

public interface IMutation<T> {

    public Solution<T> mutate(Solution<T> individual);
}
