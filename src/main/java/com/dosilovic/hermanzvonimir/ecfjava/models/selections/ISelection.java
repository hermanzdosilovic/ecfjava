package com.dosilovic.hermanzvonimir.ecfjava.models.selections;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

import java.util.Collection;
import java.util.Random;

public interface ISelection<T> {

    Random RAND = new Random();

    ISolution<T> select(Collection<ISolution<T>> population);
}
