package com.dosilovic.hermanzvonimir.ecfjava.numeric;

import org.apache.commons.math3.linear.RealVector;

public class RastriginFunction<T extends RealVector> implements IFunction<T> {

    @Override
    public double getValue(T point) {
        int    D = point.getDimension();
        double x;

        double value = 10 * D;
        for (int i = 0; i < D; i++) {
            x = point.getEntry(i);
            value += Math.pow(x, 2) - 10 * Math.cos(2 * Math.PI * x);
        }

        return value;
    }
}
