package com.dosilovic.hermanzvonimir.ecfjava.neural.errors;

import com.dosilovic.hermanzvonimir.ecfjava.neural.INeuralNetwork;
import com.dosilovic.hermanzvonimir.ecfjava.numeric.IFunction;
import com.dosilovic.hermanzvonimir.ecfjava.util.DatasetEntry;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector.RealVector;

public class MSEFunction<T extends RealVector> implements IFunction<T> {

    private INeuralNetwork neuralNetwork;
    private DatasetEntry[] dataset;

    public MSEFunction(INeuralNetwork neuralNetwork, DatasetEntry[] dataset) {
        this.neuralNetwork = neuralNetwork;
        this.dataset = dataset;
    }

    @Override
    public double getValue(T parameters) {
        neuralNetwork.setParameters(parameters.toArray());
        return NNErrorUtil.meanSquaredError(neuralNetwork, dataset);
    }
}
