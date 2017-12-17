package com.dosilovic.hermanzvonimir.ecfjava.neural.errors;

import com.dosilovic.hermanzvonimir.ecfjava.neural.INeuralNetwork;
import com.dosilovic.hermanzvonimir.ecfjava.util.DatasetEntry;

public final class NNErrorUtil {

    public static double meanSquaredError(INeuralNetwork neuralNetwork, DatasetEntry... dataset) {
        double   error = 0;
        double[] networkOutput;
        double[] actualOutput;

        for (DatasetEntry datasetEntry : dataset) {
            networkOutput = neuralNetwork.forward(datasetEntry.getInput());
            actualOutput = datasetEntry.getOutput();

            for (int i = 0; i < actualOutput.length; i++) {
                error += Math.pow(networkOutput[i] - actualOutput[i], 2);
                if (Double.isNaN(error)) {
                    return Double.MAX_VALUE;
                }
            }
        }

        return error / dataset.length;
    }
}
