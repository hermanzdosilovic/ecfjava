package com.dosilovic.hermanzvonimir.ecfjava.neural.activations;

import org.apache.commons.math3.analysis.UnivariateFunction;

public class BinaryStepActivation implements IActivation {

    private static BinaryStepActivation instance;
    private static UnivariateFunction   valueFunction;
    private static UnivariateFunction   derivativeFunction;

    private BinaryStepActivation() {}

    @Override
    public double getValue(double x) {
        return x < 0 ? 0 : 1;
    }

    @Override
    public double getDerivative(double x) {
        return x != 0 ? 0 : Integer.MAX_VALUE;
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

    public static BinaryStepActivation getInstance() {
        if (instance == null) {
            instance = new BinaryStepActivation();
        }
        return instance;
    }
}
