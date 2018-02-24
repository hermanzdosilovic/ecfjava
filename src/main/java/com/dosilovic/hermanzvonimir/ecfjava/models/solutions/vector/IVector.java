package com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

public interface IVector<T> extends ISolution {

    T getValue(int index);

    void setValue(int index, T value);

    int getSize();

    void randomizeValues();
}
