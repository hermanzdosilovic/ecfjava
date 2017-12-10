package com.dosilovic.hermanzvonimir.ecfjava.neural.activations;

import org.apache.commons.math3.analysis.UnivariateFunction;

public class IdentityActivation implements IActivation {

    private static IdentityActivation instance;
    private static UnivariateFunction valueFunction;
    private static UnivariateFunction derivativeFunction;

    private IdentityActivation() {}

    @Override
    public double getValue(double x) {
        return x;
    }

    @Override
    public double getDerivative(double x) {
        return 1.0;
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

    public static IdentityActivation getInstance() {
        if (instance == null) {
            instance = new IdentityActivation();
        }
        return instance;
    }
}
