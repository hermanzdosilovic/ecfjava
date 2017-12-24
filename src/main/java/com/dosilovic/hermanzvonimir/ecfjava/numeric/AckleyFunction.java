package com.dosilovic.hermanzvonimir.ecfjava.numeric;

import org.apache.commons.math3.linear.RealVector;

public class AckleyFunction<T extends RealVector> implements IFunction<T> {

    private static final double A = 20;
    private static final double B = 0.2;
    private static final double C = 2 * Math.PI;

    @Override
    public double getValue(T point) {
        int    D = point.getDimension();
        double x;

        double sum1 = 0;
        double sum2 = 0;
        for (int i = 0; i < D; i++) {
            x = point.getEntry(i);
            sum1 += Math.pow(x, 2);
            sum2 += Math.cos(C * x);
        }

        return -A * Math.exp(-B * Math.sqrt(sum1 / D)) - Math.exp(sum2 / D) + A + Math.exp(1);
    }
}
