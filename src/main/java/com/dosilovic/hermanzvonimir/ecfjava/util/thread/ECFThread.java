package com.dosilovic.hermanzvonimir.ecfjava.util.thread;

import com.dosilovic.hermanzvonimir.ecfjava.util.random.IRandom;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.SimpleRandom;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.providers.IRandomProvider;

public class ECFThread extends Thread implements IRandomProvider {

    private IRandom random = new SimpleRandom();

    public ECFThread() {}

    public ECFThread(Runnable target) {
        super(target);
    }

    public ECFThread(String name) {
        super(name);
    }

    public ECFThread(ThreadGroup group, Runnable target) {
        super(group, target);
    }

    public ECFThread(ThreadGroup group, String name) {
        super(group, name);
    }

    public ECFThread(Runnable target, String name) {
        super(target, name);
    }

    public ECFThread(ThreadGroup group, Runnable target, String name) {
        super(group, target, name);
    }

    public ECFThread(ThreadGroup group, Runnable target, String name, long stackSize) {
        super(group, target, name, stackSize);
    }

    @Override
    public IRandom getRandom() {
        return random;
    }
}
