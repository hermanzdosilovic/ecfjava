package com.dosilovic.hermanzvonimir.ecfjava.neural.errors;

import com.dosilovic.hermanzvonimir.ecfjava.neural.INeuralNetwork;
import com.dosilovic.hermanzvonimir.ecfjava.numeric.ErrorUtil;
import com.dosilovic.hermanzvonimir.ecfjava.numeric.IFunction;
import com.dosilovic.hermanzvonimir.ecfjava.util.DatasetEntry;
import com.dosilovic.hermanzvonimir.ecfjava.util.RealVector;

public class MAPEFunction<T extends RealVector> implements IFunction<T> {

    private INeuralNetwork neuralNetwork;
    private DatasetEntry[] dataset;
    private double[][]     inputs;
    private double[]       outputs;

    public MAPEFunction(INeuralNetwork neuralNetwork, DatasetEntry[] dataset) {
        this.neuralNetwork = neuralNetwork;
        this.dataset = dataset;
        inputs = DatasetEntry.asArrayOfInputs(dataset);
        outputs = DatasetEntry.connectOutputs(dataset);
    }

    @Override
    public double getValue(T weights) {
        neuralNetwork.setWeights(weights.toArray());
        return ErrorUtil.meanAbsolutePercentageError(outputs, neuralNetwork.forward(inputs));
    }
}
