package com.dosilovic.hermanzvonimir.ecfjava.metaheuristics;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractPopulationMetaheuristic<T extends ISolution> extends AbstractMetaheuristic<T> implements IPopulationMetaheuristic<T> {

    protected List<T> initialPopulation;
    protected List<T> population;

    @Override
    public Collection<T> getInitialPopulation() {
        return initialPopulation;
    }

    @Override
    public void setInitialPopulation(Collection<T> initialPopulation) {
        if (initialPopulation == null || initialPopulation.isEmpty()) {
            throw new IllegalArgumentException("Initial population cannot be null or empty");
        }
        this.initialPopulation = new ArrayList<>(initialPopulation);
    }

    @Override
    public Collection<T> getPopulation() {
        return population;
    }

    @Override
    public void setPopulation(Collection<T> population) {
        if (population == null || population.isEmpty()) {
            throw new IllegalArgumentException("Population cannot be null or empty");
        }
        this.population = new ArrayList<>(population);
    }

    @Override
    public T run(Collection<T> initialPopulation) {
        setInitialPopulation(initialPopulation);
        return run();
    }
}
