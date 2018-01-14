package com.dosilovic.hermanzvonimir.ecfjava.models.mutations;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

import java.util.Random;

public interface IMutation<T> {

    Random RAND = new Random();

    ISolution<T> mutate(ISolution<T> child);
}
