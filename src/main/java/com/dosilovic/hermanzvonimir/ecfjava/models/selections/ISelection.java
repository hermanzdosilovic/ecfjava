package com.dosilovic.hermanzvonimir.ecfjava.models.selections;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

import java.util.Collection;
import java.util.Random;

public interface ISelection<T extends ISolution> {

    T select(Collection<T> population);
}
