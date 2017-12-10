package com.dosilovic.hermanzvonimir.ecfjava.numeric;

public final class ErrorUtil {

    public static double meanSquaredError(double[] actual, double[] forecast) {
        if (actual.length != forecast.length) {
            throw new IllegalArgumentException("Actual and forecast data are not the same length");
        }

        double error = 0;
        for (int i = 0; i < actual.length; i++) {
            error += Math.pow(actual[i] - forecast[i], 2);
        }

        return error / actual.length;
    }

    public static double absoluteError(double[] actual, double[] forecast) {
        if (actual.length != forecast.length) {
            throw new IllegalArgumentException("Actual and expected data are not the same length");
        }

        double error = 0;
        for (int i = 0; i < actual.length; i++) {
            error += Math.abs((actual[i] - forecast[i]) / actual[i]);
        }

        return error;
    }

    public static double meanAbsoluteError(double[] actual, double[] forecast) {
        return 1.0 / actual.length * absoluteError(actual, forecast);
    }

    public static double meanAbsolutePercentageError(double[] actual, double[] forecast) {
        return 100.0 * meanAbsoluteError(actual, forecast);
    }

    public static double meanPercentageError(double[] actual, double[] forecast) {
        if (actual.length != forecast.length) {
            throw new IllegalArgumentException("Actual and expected data are not the same length");
        }

        double error = 0;
        for (int i = 0; i < actual.length; i++) {
            error += (actual[i] - forecast[i]) / actual[i];
        }

        return 100.0 / actual.length * error;
    }
}
