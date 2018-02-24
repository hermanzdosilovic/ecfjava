package com.dosilovic.hermanzvonimir.ecfjava.util.random;

public interface IRandom {

    double nextDouble();

    double nextDouble(double bound);

    double nextDouble(double min, double max);

    float nextFloat();

    float nextFloat(float bound);

    float nextFloat(float min, float max);

    int nextInt();

    int nextInt(int bound);

    int nextInt(int min, int max);

    boolean nextBoolean();

    double nextGaussian();
}
