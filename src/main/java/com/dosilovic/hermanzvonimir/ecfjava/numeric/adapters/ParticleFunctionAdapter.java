package com.dosilovic.hermanzvonimir.ecfjava.numeric.adapters;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.particle.Particle;
import com.dosilovic.hermanzvonimir.ecfjava.numeric.IFunction;
import org.apache.commons.math3.linear.RealVector;

public class ParticleFunctionAdapter<T extends Particle> implements IFunction<T> {

    IFunction<RealVector> function;

    public ParticleFunctionAdapter(IFunction<RealVector> function) {
        this.function = function;
    }

    @Override
    public double getValue(T point) {
        return function.getValue(point.getSolution());
    }
}
