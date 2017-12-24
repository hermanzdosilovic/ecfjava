package com.dosilovic.hermanzvonimir.ecfjava.numeric;

import org.apache.commons.math3.linear.RealVector;

public class SalomonFunction<T extends RealVector> implements IFunction<T> {

    @Override
    public double getValue(T point) {
        int    D = point.getDimension();
        double x;

        double sum = 0;
        for (int i = 0; i < point.getDimension(); i++) {
            x = point.getEntry(i);
            sum += Math.pow(x, 2);
        }

        return 1 - Math.cos(2 * Math.PI * Math.sqrt(sum)) + 0.1 * Math.sqrt(sum);
    }
}
