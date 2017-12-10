package com.dosilovic.hermanzvonimir.ecfjava.neural.activations;

import org.apache.commons.math3.analysis.UnivariateFunction;

public class TanHActivation implements IActivation {

    private static TanHActivation     instance;
    private static UnivariateFunction valueFunction;
    private static UnivariateFunction derivativeFunction;

    private TanHActivation() {}

    @Override
    public double getValue(double x) {
        return 2.0 / (1.0 + Math.exp(-2.0 * x)) - 1.0;
    }

    @Override
    public double getDerivative(double x) {
        return 1.0 - Math.pow(getValue(x), 2);
    }

    public UnivariateFunction getValueFunction() {
        if (valueFunction == null) {
            valueFunction = (x) -> getInstance().getValue(x);
        }
        return valueFunction;
    }

    public UnivariateFunction getDerivativeFunction() {
        if (derivativeFunction == null) {
            derivativeFunction = (x) -> getInstance().getDerivative(x);
        }
        return derivativeFunction;
    }

    public static TanHActivation getInstance() {
        if (instance == null) {
            instance = new TanHActivation();
        }
        return instance;
    }
}
