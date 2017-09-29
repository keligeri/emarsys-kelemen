package com.emarsys.kelemengergo.hw.model;

/**
 * Created by keli on 2017.09.28..
 */
public class Destination {

    private Character pointName;
    private Destination dependency;

    public Destination(Character pointName) throws IllegalArgumentException {
        if (!isValidPointName(pointName)) {
            throw new IllegalArgumentException("Character must be be between a - z!");
        }
        this.pointName = pointName;
    }

    private boolean isValidPointName(Character pointName) {
        if (pointName == null) {
            return false;
        }
        return pointName.toString().matches("[a-z]");
    }

    public Character getPointName() {
        return pointName;
    }

    public void setPointName(Character pointName) {
        this.pointName = pointName;
    }

    public Destination getDependency() {
        return dependency;
    }

    public void setDependency(Destination dependency) throws IllegalArgumentException {
        destinationDependencyReferenceItself(this, dependency);
        this.dependency = dependency;
    }

    private void destinationDependencyReferenceItself(Destination destination, Destination dependency) throws IllegalArgumentException {
        if (destination.equals(dependency)) {
            throw new IllegalArgumentException("Destination reference itself.");
        }
    }

    public boolean hasDependency() {
        return getDependency() != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        Destination that = (Destination) o;
        if (pointName.equals(that.pointName)) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 31 + (17 * pointName.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return pointName.toString();
    }
}
