package com.dosilovic.hermanzvonimir.ecfjava.neural.activations;

import org.apache.commons.math3.analysis.UnivariateFunction;

public class ReLUActivation implements IActivation {

    private static ReLUActivation     instance;
    private static UnivariateFunction valueFunction;
    private static UnivariateFunction derivativeFunction;

    private ReLUActivation() {}

    @Override
    public double getValue(double x) {
        return Math.max(0, x);
    }

    @Override
    public double getDerivative(double x) {
        return x >= 0 ? 1 : 0;
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

    public static ReLUActivation getInstance() {
        if (instance == null) {
            instance = new ReLUActivation();
        }
        return instance;
    }
}
