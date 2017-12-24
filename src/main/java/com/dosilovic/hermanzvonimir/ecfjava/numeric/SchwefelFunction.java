package com.dosilovic.hermanzvonimir.ecfjava.numeric;

import org.apache.commons.math3.linear.RealVector;

public class SchwefelFunction<T extends RealVector> implements IFunction<T> {

    @Override
    public double getValue(T point) {
        int    D = point.getDimension();
        double x;

        double sum = 0;
        for (int i = 0; i < D; i++) {
            x = point.getEntry(i);
            sum += x * Math.sin(Math.sqrt(Math.abs(x)));
        }

        return 418.9829 * D - sum;
    }
}
