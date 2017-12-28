package com.dosilovic.hermanzvonimir.ecfjava.neural;

import com.dosilovic.hermanzvonimir.ecfjava.neural.activations.IActivation;
import com.dosilovic.hermanzvonimir.ecfjava.neural.activations.IdentityActivation;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import java.util.Random;

public class ElmanNN implements INeuralNetwork {

    private int[]         architecture;
    private IActivation[] layerActivations;
    private RealMatrix[]  layerWeights;
    private RealVector    context;
    private int           numberOfWeights;
    private int           numberOfParameters;
    private double[][]    layerOutputs;
    private double[]      weights;
    private double[]      parameters;

    private static final Random RAND = new Random();

    public ElmanNN(int... architecture) {
        if (architecture.length < 3) {
            throw new IllegalArgumentException("Should have at least one hidden layer");
        }

        this.architecture = architecture.clone();
        layerActivations = new IActivation[architecture.length];
        layerOutputs = new double[architecture.length][];

        for (int i = 0; i < layerActivations.length; i++) {
            layerActivations[i] = IdentityActivation.getInstance();
        }

        setupLayerWeights();
    }

    public ElmanNN(int[] architecture, IActivation... layerActivations) {
        if (architecture.length < 3) {
            throw new IllegalArgumentException("Should have at least one hidden layer");
        } else if (
            layerActivations.length != architecture.length &&
            layerActivations.length != architecture.length - 1 &&
            layerActivations.length != architecture.length - 2) {
            throw new IllegalArgumentException("Invalid number of layer activations");
        }

        this.architecture = architecture.clone();
        this.layerActivations = new IActivation[architecture.length];
        layerOutputs = new double[architecture.length][];

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
    public int getNumberOfParameters() {
        return numberOfParameters;
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
        setWeights(weights, 0);
    }

    @Override
    public void setParameters(double[] parameters) {
        for (int i = 0; i < context.getDimension(); i++) {
            this.parameters[i] = parameters[i];
            context.setEntry(i, parameters[i]);
        }
        setWeights(parameters, context.getDimension());
    }

    @Override
    public double[] getWeights() {
        return weights;
    }

    @Override
    public double[] getParameters() {
        return parameters;
    }

    private void setWeights(double[] weights, int offset) {
        int initialOffset    = offset;
        int parametersOffset = 0;

        for (RealMatrix layerWeight : layerWeights) {
            for (int i = 0; i < layerWeight.getRowDimension(); i++) {
                for (int j = 0; j < layerWeight.getColumnDimension(); j++) {
                    this.weights[offset - initialOffset] = weights[offset];
                    parameters[context.getDimension() + parametersOffset] = weights[offset];
                    layerWeight.setEntry(i, j, weights[offset]);
                    offset++;
                    parametersOffset++;
                }
            }
        }
    }

    @Override
    public double[] forward(double[]... inputs) {
        int[] offset = new int[architecture.length];

        for (int i = 0; i < architecture.length; i++) {
            int desiredLength = inputs.length * (architecture[i] + (i == 0 ? context.getDimension() : 0));

            if (layerOutputs[i] == null || layerOutputs[i].length != desiredLength) {
                layerOutputs[i] = new double[desiredLength];
            }
        }

        for (double[] input : inputs) {
            RealVector vector = new ArrayRealVector(input);
            vector = vector.map(layerActivations[0].getValueFunction()).append(context).append(1.0);
            for (int i = 0; i < vector.getDimension() - 1; i++) {
                layerOutputs[0][offset[0]++] = vector.getEntry(i);
            }

            for (int i = 0; i < layerWeights.length; i++) {
                vector = layerWeights[i].preMultiply(vector);
                vector = vector.map(layerActivations[i + 1].getValueFunction());

                for (int j = 0; j < vector.getDimension(); j++) {
                    layerOutputs[i + 1][offset[i + 1]++] = vector.getEntry(j);
                }

                if (i == 0) {
                    context = vector;
                }

                if (i + 1 < layerWeights.length) {
                    vector = vector.append(1.0);
                }
            }
        }

        return layerOutputs[architecture.length - 1];
    }

    @Override
    public double[] getOutput(int layerIndex) {
        return layerOutputs[layerIndex];
    }

    @Override
    public double[][] getOutput() {
        return layerOutputs;
    }

    private void setupLayerWeights() {
        layerWeights = new RealMatrix[architecture.length - 1];
        numberOfWeights = 0;

        context = new ArrayRealVector(architecture[1]);
        for (int i = 0; i < context.getDimension(); i++) {
            context.setEntry(i, RAND.nextDouble());
        }

        for (int n = 1; n < architecture.length; n++) {
            int rows = architecture[n - 1] + 1;
            int cols = architecture[n];

            if (n == 1) {
                rows += architecture[n];
            }

            layerWeights[n - 1] = new Array2DRowRealMatrix(rows, cols);
            numberOfWeights += rows * cols;

            // Xavier initialization
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    layerWeights[n - 1].setEntry(i, j, RAND.nextGaussian() * (2.0 / (rows + cols - 1)));
                }
            }
        }

        numberOfParameters = numberOfWeights + context.getDimension();

        parameters = new double[numberOfParameters];
        weights = new double[numberOfWeights];
    }
}
