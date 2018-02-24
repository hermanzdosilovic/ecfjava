package com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector;

import com.dosilovic.hermanzvonimir.ecfjava.util.random.IRandom;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.Random;
import org.apache.commons.math3.linear.ArrayRealVector;

public class RealVector extends ArrayRealVector implements IVector<Double> {

    private double fitness = -1.0 * Double.MAX_VALUE;
    private double penalty = Double.MAX_VALUE;

    public RealVector(int size) {
        super(size);
    }

    public RealVector(int size, double value) {
        super(size);
        for (int i = 0; i < size; i++) {
            setValue(i, value);
        }
    }

    public RealVector(double[] data) {
        super(data);
    }

    public RealVector(RealVector vector) {
        super(vector.getDataRef());
        setFitness(vector.getFitness());
        setPenalty(vector.getPenalty());
    }

    @Override
    public Double getValue(int index) {
        return getEntry(index);
    }

    @Override
    public void setValue(int index, Double value) {
        setEntry(index, value);
    }

    @Override
    public int getSize() {
        return getDimension();
    }

    @Override
    public void randomizeValues() {
        IRandom random = Random.getRandom();
        for (int i = 0, size = getSize(); i < size; i++) {
            setEntry(i, random.nextDouble());
        }
    }

    @Override
    public double getFitness() {
        return fitness;
    }

    @Override
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    @Override
    public double getPenalty() {
        return penalty;
    }

    @Override
    public void setPenalty(double penalty) {
        this.penalty = penalty;
    }

    @Override
    public RealVector copy() {
        return new RealVector(this);
    }
}
