package com.dosilovic.hermanzvonimir.ecfjava.numeric;

import org.apache.commons.math3.linear.RealVector;

public class RosenbrockFunction<T extends RealVector> implements IFunction<T> {

    private static final double A = 1.0;
    private static final double B = 100.0;

    @Override
    public double getValue(T point) {
        int    D = point.getDimension();
        double x;
        double z;

        double sum = 0;
        for (int i = 0; i < D - 1; i++) {
            x = point.getEntry(i);
            z = point.getEntry(i + 1);
            sum += B * Math.pow(z - Math.pow(x, 2), 2) + Math.pow(A - x, 2);
        }

        return sum;
    }
}
