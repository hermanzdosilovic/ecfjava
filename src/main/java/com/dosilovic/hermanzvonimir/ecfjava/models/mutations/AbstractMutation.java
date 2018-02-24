package com.dosilovic.hermanzvonimir.ecfjava.models.mutations;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

import java.util.List;

public abstract class AbstractMutation<T extends ISolution> implements IMutation<T> {

    @Override
    public List<T> mutate(List<T> children) {
        for (int i = 0; i < children.size(); i++) {
            children.set(i, mutate(children.get(i)));
        }
        return children;
    }
}
