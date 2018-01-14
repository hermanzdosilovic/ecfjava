package com.dosilovic.hermanzvonimir.ecfjava.util;

import java.util.Arrays;

public final class DataUtil {

    public static Pair<double[], double[]> split(double[] data, double ratio) {
        int firstSize = (int) (data.length * ratio);
        return new Pair<>(
            Arrays.copyOfRange(data, 0, firstSize),
            Arrays.copyOfRange(data, firstSize, data.length)
        );
    }

    public static double[] normalize(double[] data, double lowerBound, double upperBound) {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;

        for (int i = 0; i < data.length; i++) {
            min = Math.min(min, data[i]);
            max = Math.max(max, data[i]);
        }

        for (int i = 0; i < data.length; i++) {
            data[i] = lowerBound + (upperBound - lowerBound) * (data[i] - min) / (max - min);
        }

        return data;
    }

    public static double[] normalize(double... data) {
        return normalize(data, -1, 1);
    }
}
