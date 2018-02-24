package com.dosilovic.hermanzvonimir.ecfjava.models.mutations;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

import java.util.List;
import java.util.Random;

public interface IMutation<T extends ISolution> {

    T mutate(T child);

    List<T> mutate(List<T> children);
}
