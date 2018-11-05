package com.dosilovic.hermanzvonimir.ecfjava.models.crossovers;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

import java.util.ArrayList;
import java.util.List;

public class CopyCrossover<T extends ISolution> implements ICrossover<T> {

    @SuppressWarnings("unchecked")
    @Override
    public List<T> cross(T mom, T dad) {
        List<T> children = new ArrayList<>();
        children.add((T) mom.copy());
        children.add((T) dad.copy());
        return children;
    }
}
