package com.dosilovic.hermanzvonimir.ecfjava.neural.activations;

import org.apache.commons.math3.analysis.UnivariateFunction;

public interface IActivation {

    public double getValue(double x);

    public double getDerivative(double x);

    public UnivariateFunction getValueFunction();

    public UnivariateFunction getDerivativeFunction();
}
