package com.emarsys.kelemengergo.hw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by keli on 2017.09.28..
 */
public class DestinationTest {

    private static final Logger logger = Logger.getLogger(DestinationTest.class.getName());

    @BeforeEach
    public void addDetailsAboutMethod(final TestInfo testInfo) {
        logger.info(() -> String.format("About to execute [%s]", testInfo.getDisplayName()));
    }

    @Test
    public void constructor_ThrowsException_IfGetNumber() {
        assertThrows(IllegalArgumentException.class, () -> new Destination('9'));
    }

    @Test
    public void constructor_ThrowsException_IfGetUppercase() {
        assertThrows(IllegalArgumentException.class, () -> new Destination('D'));
    }

    @Test
    public void constructor_ThrowException_IfGetInvalidCharacter() {
        assertThrows(IllegalArgumentException.class, () -> new Destination('%'));
    }

    @Test
    public void constructor_ThrowException_IfPointNameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Destination(null));
    }

    @Test
    public void setDependency_ThrowException_IfDestinationReferenceItself() {
        Destination dest1 = new Destination('e');
        assertThrows(IllegalArgumentException.class, () -> dest1.setDependency(dest1));
    }

    @Test
    public void hasDependency_False_IfNoDependency() {
        Destination dest1 = new Destination('e');
        assertFalse(dest1.hasDependency());
    }

    @Test
    public void hasDependency_True_IfHasDependency() {
        Destination dest1 = new Destination('e');
        Destination dest2 = new Destination('x');
        dest1.setDependency(dest2);
        assertTrue(dest1.hasDependency());
    }

    @Test
    public void equals_Equals_IfDestinationsNameIsSame() {
        Destination dest1 = new Destination('c');
        Destination dest2 = new Destination('c');
        assertEquals(dest1, dest2);
    }

    @Test
    public void equals_NotEquals_IfDestinationsNameIsNotTheSame() {
        Destination dest1 = new Destination('c');
        Destination dest2 = new Destination('x');
        assertNotEquals(dest1, dest2);
    }

    @Test
    public void hashCode_Equals_WhenHashIsTheSame() {
        int expectedHash = (new Character('c').hashCode() * 17) + 31;
        assertEquals(expectedHash, new Destination('c').hashCode());
    }

    @Test
    public void hashCode_NotEquals_WhenHashIsNotTheSame() {
        int hash = (new Character('x').hashCode() * 17) + 31;
        assertNotEquals(hash, new Destination('c').hashCode());
    }

}