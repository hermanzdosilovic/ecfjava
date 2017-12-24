package com.dosilovic.hermanzvonimir.ecfjava.numeric;

import org.apache.commons.math3.linear.RealVector;

public class GriewankFunction<T extends RealVector> implements IFunction<T> {

    @Override
    public double getValue(T point) {
        int    D = point.getDimension();
        double x;

        double sum     = 0;
        double product = 1;
        for (int i = 0; i < D; i++) {
            x = point.getEntry(i);
            sum += Math.pow(x, 2);
            product *= Math.cos(x / Math.sqrt(i + 1));
        }

        return 1.0 + sum / 4000.0 - product;
    }
}
