package com.dosilovic.hermanzvonimir.ecfjava.util.random;

import java.util.Random;

public class SimpleRandom implements IRandom {

    private Random random;

    public SimpleRandom() {
        random = new Random();
    }

    @Override
    public double nextDouble() {
        return random.nextDouble();
    }

    @Override
    public double nextDouble(double bound) {
        return nextDouble(0, bound);
    }

    @Override
    public double nextDouble(double min, double max) {
        return min + nextDouble() * (max - min);
    }

    @Override
    public float nextFloat() {
        return random.nextFloat();
    }

    @Override public float nextFloat(float bound) {
        return nextFloat(0, bound);
    }

    @Override
    public float nextFloat(float min, float max) {
        return min + nextFloat() * (max - min);
    }

    @Override
    public int nextInt() {
        return random.nextInt();
    }

    @Override
    public int nextInt(int bound) {
        return random.nextInt(bound);
    }

    @Override
    public int nextInt(int min, int max) {
        return min + Math.abs(nextInt()) % (max - min);
    }

    @Override
    public boolean nextBoolean() {
        return random.nextBoolean();
    }

    @Override
    public double nextGaussian() {
        return random.nextGaussian();
    }
}
