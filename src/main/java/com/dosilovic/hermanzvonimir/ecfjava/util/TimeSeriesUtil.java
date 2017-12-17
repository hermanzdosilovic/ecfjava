package com.dosilovic.hermanzvonimir.ecfjava.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class TimeSeriesUtil {

    public static double[] read(String filePath) throws IOException {
        BufferedReader reader     = new BufferedReader(new FileReader(filePath));
        List<Double>   timeSeries = new ArrayList<>();
        String         line;
        while ((line = reader.readLine()) != null) {
            timeSeries.add(Double.parseDouble(line.trim()));
        }

        double[] timeSeriesArray = new double[timeSeries.size()];
        for (int i = 0; i < timeSeries.size(); i++) {
            timeSeriesArray[i] = timeSeries.get(i);
        }

        return timeSeriesArray;
    }

    public static DatasetEntry[] createDataset(double[] dataset, int inputSize, int outputSize) {
        DatasetEntry[] timeSeriesDataset = new DatasetEntry[dataset.length - inputSize - outputSize + 1];

        int k = 0;
        for (int i = inputSize; i <= dataset.length - outputSize; i++) {
            timeSeriesDataset[k++] = new DatasetEntry(
                Arrays.copyOfRange(dataset, i - inputSize, i),
                Arrays.copyOfRange(dataset, i, i + outputSize)
            );
        }

        return timeSeriesDataset;
    }
}
