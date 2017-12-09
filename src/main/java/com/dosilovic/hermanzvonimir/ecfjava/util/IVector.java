package com.dosilovic.hermanzvonimir.ecfjava.util;

public interface IVector<T> extends Cloneable {

    public T getValue(int index);

    public void setValue(int index, T value);

    public int getSize();

    public Object clone();
}
