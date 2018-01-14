package com.dosilovic.hermanzvonimir.ecfjava.models.solutions.vector;

public interface IVector<T> {

    T getValue(int index);

    void setValue(int index, T value);

    int getSize();

    IVector<T> copy();
}
