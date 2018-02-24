package com.dosilovic.hermanzvonimir.ecfjava.models.crossovers;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.RealVector;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.IRandom;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.Random;

import java.util.ArrayList;
import java.util.List;

public class BLXAlphaCrossover<T extends RealVector> implements ICrossover<T> {

    private double alpha;

    public BLXAlphaCrossover(double alpha) {
        this.alpha = alpha;
    }

    public BLXAlphaCrossover() {
        this(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> cross(T mom, T dad) {
        IRandom random = Random.getRandom();

        T child = (T) dad.copy();

        double cmin, cmax, Ialpha;
        for (int i = 0; i < child.getSize(); i++) {
            cmin = Math.min(mom.getValue(i), dad.getValue(i));
            cmax = Math.max(mom.getValue(i), dad.getValue(i));
            Ialpha = (cmax - cmin) * alpha;
            child.setValue(i, random.nextDouble(cmin - Ialpha, cmax + Ialpha));
        }

        List<T> children = new ArrayList<>();
        children.add(child);

        return children;
    }
}
