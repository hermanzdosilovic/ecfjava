package com.dosilovic.hermanzvonimir.ecfjava.util;

import java.util.Objects;

public class Particle<T> implements Comparable<Particle<T>> {

    private Solution<T> currentSolution;
    private Solution<T> personalBestSolution;
    private RealVector  currentSpeed;

    public Particle(Solution<T> initialSolution, RealVector initialSpeed) {
        this.currentSolution = initialSolution;
        this.currentSpeed = initialSpeed;
    }

    public Solution<T> getCurrentSolution() {
        return currentSolution;
    }

    public void setCurrentSolution(Solution<T> currentSolution) {
        this.currentSolution = currentSolution;
    }

    public Solution<T> getPersonalBestSolution() {
        return personalBestSolution;
    }

    public void setPersonalBestSolution(Solution<T> personalBestSolution) {
        this.personalBestSolution = personalBestSolution;
    }

    public RealVector getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(RealVector currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Particle<?> particle = (Particle<?>) o;
        return Objects.equals(currentSolution, particle.currentSolution) &&
               Objects.equals(personalBestSolution, particle.personalBestSolution);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentSolution, personalBestSolution);
    }

    @Override
    public int compareTo(Particle<T> o) {
        return personalBestSolution.compareTo(o.personalBestSolution);
    }
}
