package com.dosilovic.hermanzvonimir.ecfjava.models.crossovers;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

import java.util.List;

public interface ICrossover<T extends ISolution> {

    List<T> cross(T mom, T dad);
}
