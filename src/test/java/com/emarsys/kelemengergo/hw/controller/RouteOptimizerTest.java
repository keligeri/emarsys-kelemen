package com.emarsys.kelemengergo.hw.controller;

import com.emarsys.kelemengergo.hw.model.Destination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by keli on 2017.09.28..
 */
public class RouteOptimizerTest {

    private static final Logger logger = Logger.getLogger(RouteOptimizerTest.class.getName());

    private Destination dest1;
    private Destination dest2;
    private Destination dest3;
    private RouteOptimizer optimizer;

    @BeforeEach
    public void addDetailsAboutMethod(final TestInfo testInfo) {
        logger.info(() -> String.format("About to execute [%s]", testInfo.getDisplayName()));
    }

    public void setUpDestinations() {
        dest1 = new Destination('x');
        dest2 = new Destination('y');
        dest3 = new Destination('z');
        dest2.setDependency(dest3);
        dest3.setDependency(dest1);

        List<Destination> destinations = Arrays.asList(dest1, dest2, dest3);
        optimizer = new RouteOptimizer(destinations);
    }

    @Test
    public void constructor_ThrowsException_IfDestinationDuplicated() {
        List<Destination> destinations = Arrays.asList(new Destination('x'), new Destination('x'));
        assertThrows(IllegalArgumentException.class, () -> new RouteOptimizer(destinations));
    }

    @Test
    public void constructor_ThrowsException_IfDestinationsIsNull() {
        assertThrows(NullPointerException.class, () -> new RouteOptimizer(null));
    }

    @Test
    public void optimizeRoute_ThrowsException_IfDestinationsNotIncludeDependency() {
        setUpDestinations();
        dest2.setDependency(new Destination('e'));
        List<Destination> destinations = Arrays.asList(dest1, dest2);
        optimizer.setDestinations(destinations);

        assertThrows(NullPointerException.class, () -> optimizer.optimizeRoute());
    }

    @Test
    public void optimizeRoute_ThrowsException_IfDestinationsDependenciesNotReferenceToEachOther() {
        setUpDestinations();
        dest1.setDependency(dest2);
        dest2.setDependency(dest1);
        List<Destination> destinations = Arrays.asList(dest1, dest2);
        optimizer = new RouteOptimizer(destinations);

        assertThrows(IllegalArgumentException.class, () -> optimizer.optimizeRoute());
    }

    @Test
    public void optimizeRoute_Equals_IfDestinationsHasNoDependencies() {
        Destination currentDest1 = new Destination('x');
        Destination currentDest2 = new Destination('y');
        RouteOptimizer calculator = new RouteOptimizer(Arrays.asList(currentDest1, currentDest2));

        assertEquals("xy", calculator.optimizeRoute());
    }

    @Test
    public void optimizeRoute_Equals_IfDestinationsAreValid_Short() {
        setUpDestinations();
        assertEquals("xzy", optimizer.optimizeRoute());
    }

    @Test
    public void optimizeRoute_Equals_IfDestinationsAreValid_Long() {
        setUpDestinations();
        addExtraDestinations();

        assertEquals("uxzwvy", optimizer.optimizeRoute());
    }

    private void addExtraDestinations() {
        Destination dest4 = new Destination('u');
        Destination dest5 = new Destination('v');
        Destination dest6 = new Destination('w');

        dest1.setDependency(dest4);
        dest2.setDependency(dest5);
        dest5.setDependency(dest6);
        dest6.setDependency(dest3);
        List<Destination> destinations = Arrays.asList(dest1, dest2, dest3, dest4, dest5, dest6);

        optimizer.setDestinations(destinations);
    }

}