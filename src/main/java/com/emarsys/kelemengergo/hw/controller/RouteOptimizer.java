package com.emarsys.kelemengergo.hw.controller;

import com.emarsys.kelemengergo.hw.model.Destination;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by keli on 2017.09.28..
 */
public class RouteOptimizer {

    private List<Destination> destinations = new ArrayList<>();
    private List<Destination> sortedDestinations = new ArrayList<>();

    public RouteOptimizer(List<Destination> destinations) throws NullPointerException, IllegalArgumentException {
        if (isDestinationNull(destinations)) {
            throw new NullPointerException("Destinations must not be null!");
        } else if (isDestinationDuplicated(destinations)) {
            throw new IllegalArgumentException("Destination's name must be unique!");
        }
        this.destinations = destinations;
    }

    public void setDestinations(List<Destination> destinations) {
        this.destinations = destinations;
    }

    private boolean isDestinationNull(List<Destination> destinations) {
        return destinations == null;
    }

    private boolean isDestinationDuplicated(List<Destination> destinations) {
        Set<Destination> destinationSet = new HashSet<>(destinations);
        return destinations.size() != destinationSet.size();
    }

    public String optimizeRoute() throws NullPointerException, IllegalArgumentException {
        destinationsDependenciesAreValid();
        for (Destination destination : destinations) {
            if (!sortedDestinations.contains(destination)) {
                if (!destination.hasDependency()) {
                    sortedDestinations.add(destination);
                } else {
                    addDestinationToSortedList(destination);
                }
            }
        }
        return concatCharToResult();
    }

    private void addDestinationToSortedList(Destination destination) {
        if (!destination.hasDependency()) {
            sortedDestinations.add(destination);
            return;
        }
        Destination dependency = destination.getDependency();
        if (!sortedDestinations.contains(dependency)) {
            addDestinationToSortedList(dependency);
        }
        sortedDestinations.add(destination);
    }

    private void destinationsDependenciesAreValid() {
        for (Destination dest : destinations) {
            if (dest.hasDependency()) {
                Destination dependency = dest.getDependency();
                if (!destinations.contains(dependency)) {
                    throw new NullPointerException("Destinations dependencies has to be valid!");
                } else if (dependenciesNotReferenceToEachOther(dependency, dest)) {
                    throw new IllegalArgumentException("Destinations dependencies has to reference to each other!");
                }
            }
        }
    }

    private boolean dependenciesNotReferenceToEachOther(Destination dependency, Destination destination) {
        if (dependency.hasDependency() && dependency.getDependency().equals(destination)) {
            return true;
        }
        return false;
    }

    private String concatCharToResult() {
        return sortedDestinations.stream().map(Destination::toString).collect(Collectors.joining());
    }

}
