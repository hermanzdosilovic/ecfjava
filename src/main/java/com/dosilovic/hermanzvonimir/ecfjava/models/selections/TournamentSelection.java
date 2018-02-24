package com.dosilovic.hermanzvonimir.ecfjava.models.selections;

import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.ISolution;
import com.dosilovic.hermanzvonimir.ecfjava.models.solutions.util.Solutions;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.IRandom;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.Random;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class TournamentSelection<T extends ISolution> implements ISelection<T> {

    private int     size;
    private boolean isUniqueTournament;
    private boolean useFitness;

    public TournamentSelection(int size, boolean isUniqueTournament, boolean useFitness) {
        this.size = size;
        this.isUniqueTournament = isUniqueTournament;
        this.useFitness = useFitness;
    }

    public TournamentSelection(int size, boolean isUniqueTournament) {
        this(size, isUniqueTournament, true);
    }

    public TournamentSelection(int size) {
        this(size, true);
    }

    public TournamentSelection() {
        this(3);
    }

    @Override
    public T select(Collection<T> population) {
        IRandom random = Random.getRandom();

        List<T> competitors = new ArrayList<>(population.size());
        if (isUniqueTournament) {
            competitors.addAll(new HashSet<>(population));
        } else {
            competitors.addAll(population);
        }

        T best = null;
        T competitor;
        for (int i = 0; i < size; i++) {
            competitor = competitors.get(random.nextInt(competitors.size()));
            if (best == null) {
                best = competitor;
            } else if (useFitness) {
                best = Solutions.betterByFitness(best, competitor);
            } else {
                best = Solutions.betterByPenalty(best, competitor);
            }
        }

        return best;
    }
}
