package com.dosilovic.hermanzvonimir.ecfjava.neural.activations;

import org.apache.commons.math3.analysis.UnivariateFunction;

public class SigmoidActivation implements IActivation {

    private static SigmoidActivation  instance;
    private static UnivariateFunction valueFunction;
    private static UnivariateFunction derivativeFunction;

    private SigmoidActivation() {}

    @Override
    public double getValue(double x) {
        return 1.0 / (1 + Math.exp(-x));
    }

    @Override
    public double getDerivative(double x) {
        return getValue(x) * (1 - getValue(x));
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

    public static SigmoidActivation getInstance() {
        if (instance == null) {
            instance = new SigmoidActivation();
        }
        return instance;
    }
}
