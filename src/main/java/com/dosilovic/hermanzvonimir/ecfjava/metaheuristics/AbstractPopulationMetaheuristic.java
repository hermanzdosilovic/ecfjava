package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractPopulationMetaheuristic<T> extends AbstractMetaheuristic<T> implements IPopulationMetaheuristic<T> {

    protected Collection<ISolution<T>> initialPopulation;
    protected List<ISolution<T>>       population;

    @Override
    public Collection<ISolution<T>> getInitialPopulation() {
        return initialPopulation;
    }

    @Override
    public void setInitialPopulation(Collection<ISolution<T>> initialPopulation) {
        if (initialPopulation == null || initialPopulation.isEmpty()) {
            throw new IllegalArgumentException("Initial population cannot be null or empty");
        }
        this.initialPopulation = initialPopulation;
    }

    @Override
    public Collection<ISolution<T>> getPopulation() {
        return population;
    }

    @Override
    public void setPopulation(Collection<ISolution<T>> population) {
        if (population == null || population.isEmpty()) {
            throw new IllegalArgumentException("Population cannot be null or empty");
        }
        this.population = new ArrayList<>(population);
    }

    @Override
    public ISolution<T> run(Collection<ISolution<T>> initialPopulation) {
        setInitialPopulation(initialPopulation);
        return run();
    }
}
