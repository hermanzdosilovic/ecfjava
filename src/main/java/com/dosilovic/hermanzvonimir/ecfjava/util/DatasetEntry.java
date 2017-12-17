package com.dosilovic.hermanzvonimir.ecfjava.util;

import java.util.Arrays;

public class DatasetEntry {

    private double[] input;
    private double[] output;

    public DatasetEntry(double[] input, double[] output) {
        this.input = input;
        this.output = output;
    }

    public double[] getInput() {
        return input;
    }

    public double[] getOutput() {
        return output;
    }

    public static double[] connectInputs(DatasetEntry... dataset) {
        int totalLength = 0;
        for (DatasetEntry datasetEntry : dataset) {
            totalLength += datasetEntry.input.length;
        }

        double[] inputs = new double[totalLength];
        int      offset = 0;
        for (DatasetEntry datasetEntry : dataset) {
            for (int i = 0; i < datasetEntry.input.length; i++) {
                inputs[offset++] = datasetEntry.input[i];
            }
        }

        return inputs;
    }

    public static double[] connectOutputs(DatasetEntry... dataset) {
        int totalLength = 0;
        for (DatasetEntry datasetEntry : dataset) {
            totalLength += datasetEntry.output.length;
        }

        double[] outputs = new double[totalLength];
        int      offset  = 0;
        for (DatasetEntry datasetEntry : dataset) {
            for (int i = 0; i < datasetEntry.output.length; i++) {
                outputs[offset++] = datasetEntry.output[i];
            }
        }

        return outputs;
    }

    public static double[][] asArrayOfInputs(DatasetEntry... dataset) {
        double[][] arrayOfInputs = new double[dataset.length][];
        for (int i = 0; i < dataset.length; i++) {
            arrayOfInputs[i] = dataset[i].input;
        }
        return arrayOfInputs;
    }

    public static double[][] asArrayOfOutputs(DatasetEntry... dataset) {
        double[][] arrayOfOutputs = new double[dataset.length][];
        for (int i = 0; i < dataset.length; i++) {
            arrayOfOutputs[i] = dataset[i].output;
        }
        return arrayOfOutputs;
    }

    public static Pair<DatasetEntry[], DatasetEntry[]> split(DatasetEntry[] dataset, double ratio) {
        int firstSize = (int) (dataset.length * ratio);
        return new Pair<>(
            Arrays.copyOfRange(dataset, 0, firstSize),
            Arrays.copyOfRange(dataset, firstSize, dataset.length)
        );
    }
}
