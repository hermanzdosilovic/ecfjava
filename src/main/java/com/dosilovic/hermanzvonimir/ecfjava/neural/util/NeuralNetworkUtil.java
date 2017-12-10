package com.dosilovic.hermanzvonimir.ecfjava.neural.util;

import com.dosilovic.hermanzvonimir.ecfjava.neural.INeuralNetwork;
import com.dosilovic.hermanzvonimir.ecfjava.util.DatasetEntry;

public final class NeuralNetworkUtil {

    public static double[] forward(INeuralNetwork neuralNetwork, DatasetEntry... datasetEntry) {
        return neuralNetwork.forward(DatasetEntry.asArrayOfInputs(datasetEntry));
    }
}
