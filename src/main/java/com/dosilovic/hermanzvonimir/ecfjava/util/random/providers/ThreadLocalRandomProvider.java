package com.dosilovic.hermanzvonimir.ecfjava.util.random.providers;

import com.dosilovic.hermanzvonimir.ecfjava.util.random.IRandom;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.SimpleRandom;

public class ThreadLocalRandomProvider implements IRandomProvider {

    private static final ThreadLocal<IRandom> threadLocal = ThreadLocal.withInitial(SimpleRandom::new);

    @Override
    public IRandom getRandom() {
        return threadLocal.get();
    }
}
