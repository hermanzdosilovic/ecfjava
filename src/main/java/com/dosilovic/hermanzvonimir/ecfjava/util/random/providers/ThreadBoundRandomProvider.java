package com.dosilovic.hermanzvonimir.ecfjava.util.random.providers;

import com.dosilovic.hermanzvonimir.ecfjava.util.random.IRandom;

public class ThreadBoundRandomProvider implements IRandomProvider {

    @Override
    public IRandom getRandom() {
        IRandomProvider thread = (IRandomProvider) Thread.currentThread();
        return thread.getRandom();
    }
}
