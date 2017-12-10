package com.dosilovic.hermanzvonimir.ecfjava.neural;

import com.dosilovic.hermanzvonimir.ecfjava.neural.activations.IActivation;

public interface INeuralNetwork {

    public int[] getArchitecture();

    public int getNumberOfLayers();

    public int getNumberOfWeights();

    public int getInputSize();

    public int getOutputSize();

    public IActivation[] getLayerActivations();

    public void setWeights(double[] weights);

    public double[] forward(double[]... input);
}
