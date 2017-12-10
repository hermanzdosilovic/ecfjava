package com.dosilovic.hermanzvonimir.ecfjava.neural;

import com.dosilovic.hermanzvonimir.ecfjava.neural.activations.IActivation;
import com.dosilovic.hermanzvonimir.ecfjava.neural.activations.IdentityActivation;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import java.util.Random;

public class FeedForwardANN implements INeuralNetwork {

    private int[]         architecture;
    private IActivation[] layerActivations;
    private RealMatrix[]  layerWeights;
    private int           numberOfWeights;

    private static final Random RAND = new Random();

    public FeedForwardANN(int... architecture) {
        this.architecture = architecture.clone();
        layerActivations = new IActivation[architecture.length];
        for (int i = 0; i < layerActivations.length; i++) {
            layerActivations[i] = IdentityActivation.getInstance();
        }
        setupLayerWeights();
    }

    public FeedForwardANN(int[] architecture, IActivation... layerActivations) {
        if (
            layerActivations.length != architecture.length &&
            layerActivations.length != architecture.length - 1 &&
            layerActivations.length != architecture.length - 2) {
            throw new IllegalArgumentException("Invalid number of layer activations");
        }

        this.architecture = architecture.clone();
        this.layerActivations = new IActivation[architecture.length];

        int start = 0;
        int end   = architecture.length;
        if (layerActivations.length < architecture.length) {
            this.layerActivations[0] = IdentityActivation.getInstance();
            start = 1;
        }
        if (layerActivations.length < architecture.length - 1) {
            this.layerActivations[architecture.length - 1] = IdentityActivation.getInstance();
            end = architecture.length - 1;
        }

        for (int i = start; i < end; i++) {
            this.layerActivations[i] = layerActivations[i - start];
        }

        setupLayerWeights();
    }

    @Override
    public int[] getArchitecture() {
        return architecture.clone();
    }

    @Override
    public int getNumberOfLayers() {
        return architecture.length;
    }

    @Override
    public int getNumberOfWeights() {
        return numberOfWeights;
    }

    @Override
    public int getInputSize() {
        return architecture[0];
    }

    @Override
    public int getOutputSize() {
        return architecture[architecture.length - 1];
    }

    @Override
    public IActivation[] getLayerActivations() {
        return layerActivations.clone();
    }

    @Override
    public void setWeights(double[] weights) {
        int offset = 0;
        for (RealMatrix layerWeight : layerWeights) {
            for (int i = 0; i < layerWeight.getRowDimension(); i++) {
                for (int j = 0; j < layerWeight.getColumnDimension(); j++) {
                    layerWeight.setEntry(i, j, weights[offset++]);
                }
            }
        }
    }

    @Override
    public double[] forward(double[]... inputs) {
        double[] output = new double[inputs.length * getOutputSize()];
        int      offset = 0;

        for (double[] input : inputs) {
            RealVector vector = new ArrayRealVector(input);
            vector = vector.map(layerActivations[0].getValueFunction()).append(1.0);

            for (int i = 0; i < layerWeights.length; i++) {
                vector = layerWeights[i].preMultiply(vector);
                vector = vector.map(layerActivations[i + 1].getValueFunction());

                if (i + 1 < layerWeights.length) {
                    vector = vector.append(1.0);
                }
            }

            for (int i = 0; i < vector.getDimension(); i++) {
                output[offset++] = vector.getEntry(i);
            }
        }

        return output;
    }

    private void setupLayerWeights() {
        layerWeights = new RealMatrix[architecture.length - 1];
        numberOfWeights = 0;

        for (int n = 1; n < architecture.length; n++) {
            int rows = architecture[n - 1] + 1;
            int cols = architecture[n];

            layerWeights[n - 1] = new Array2DRowRealMatrix(rows, cols);
            numberOfWeights += rows * cols;

            // Xavier initialization
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    layerWeights[n - 1].setEntry(i, j, RAND.nextGaussian() * (2.0 / (rows + cols - 1)));
                }
            }
        }
    }
}
