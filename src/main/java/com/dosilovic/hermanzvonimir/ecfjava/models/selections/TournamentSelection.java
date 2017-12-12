package com.dosilovic.hermanzvonimir.ecfjava.models.selections;

import com.dosilovic.hermanzvonimir.ecfjava.util.Solution;

import java.util.*;

public class TournamentSelection<T> implements ISelection<T> {

    private int     size;
    private boolean allowRepeat;
    private static final Random RAND = new Random();

    public TournamentSelection(int size, boolean allowRepeat) {
        this.size = size;
        this.allowRepeat = allowRepeat;
    }

    @Override
    public Solution<T> select(Collection<Solution<T>> population) {
        List<Solution<T>> tournamentCandidates = new LinkedList<>(population);
        List<Solution<T>> competitors          = new ArrayList<>(size);

        while (competitors.size() < size && tournamentCandidates.size() != 0) {
            int index = RAND.nextInt(tournamentCandidates.size());
            competitors.add(tournamentCandidates.get(index));

            if (!allowRepeat) {
                tournamentCandidates.remove(index);
            }
        }

        return Solution.findBestByFitness(competitors);
    }
}
