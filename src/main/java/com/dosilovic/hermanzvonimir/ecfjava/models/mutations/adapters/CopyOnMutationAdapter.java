package com.dosilovic.hermanzvonimir.ecfjava.models.mutations.adapters;

import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.AbstractMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.mutations.IMutation;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

public class CopyOnMutationAdapter<T extends ISolution> extends AbstractMutation<T> {

    IMutation<T> mutation;

    public CopyOnMutationAdapter(IMutation<T> mutation) {
        this.mutation = mutation;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T mutate(T child) {
        return mutation.mutate((T) child.copy());
    }
}
