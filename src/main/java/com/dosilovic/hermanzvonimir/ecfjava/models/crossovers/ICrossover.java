package com.dosilovic.hermanzvonimir.ecfjava.models.crossovers;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

import java.util.Collection;
import java.util.Random;

public interface ICrossover<T> {

    Random RAND = new Random();

    Collection<ISolution<T>> cross(ISolution<T> mom, ISolution<T> dad);
}
