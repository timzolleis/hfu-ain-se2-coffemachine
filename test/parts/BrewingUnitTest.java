package parts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BrewingUnitTest {

    private BrewingUnit brewingUnit;

    @BeforeEach
    void setUp() {
        brewingUnit = new BrewingUnit();
    }

    @Test
    void testInitialStateIsNotBrewing() {
        assertTrue(brewingUnit.isBrewingComplete());
    }

    @Test
    void testStartBrewingWhenNotAlreadyBrewing() {
        brewingUnit.startBrewing();
        assertFalse(brewingUnit.isBrewingComplete());
    }

    @Test
    void testStartBrewingWhenAlreadyBrewingDoesNotStartNewBrewing() throws InterruptedException {
        brewingUnit.startBrewing();
        assertFalse(brewingUnit.isBrewingComplete());
        
        Thread.sleep(100);
        brewingUnit.startBrewing();
        
        assertFalse(brewingUnit.isBrewingComplete());
    }

    @Test
    void testBrewingCompletesAfterThreeSeconds() throws InterruptedException {
        brewingUnit.startBrewing();
        assertFalse(brewingUnit.isBrewingComplete());
        
        Thread.sleep(3100);
        
        assertTrue(brewingUnit.isBrewingComplete());
    }

    @Test
    void testStopBrewingStopsOngoingBrewing() throws InterruptedException {
        brewingUnit.startBrewing();
        assertFalse(brewingUnit.isBrewingComplete());
        
        Thread.sleep(100);
        brewingUnit.stopBrewing();
        
        Thread.sleep(100);
        assertTrue(brewingUnit.isBrewingComplete());
    }

    @Test
    void testStopBrewingWhenNotBrewingDoesNothing() {
        assertTrue(brewingUnit.isBrewingComplete());
        
        brewingUnit.stopBrewing();
        
        assertTrue(brewingUnit.isBrewingComplete());
    }

    @Test
    void testMultipleStartBrewingCallsWhileBrewingIgnored() throws InterruptedException {
        brewingUnit.startBrewing();
        assertFalse(brewingUnit.isBrewingComplete());
        
        Thread.sleep(100);
        brewingUnit.startBrewing();
        brewingUnit.startBrewing();
        
        assertFalse(brewingUnit.isBrewingComplete());
        
        Thread.sleep(3100);
        assertTrue(brewingUnit.isBrewingComplete());
    }

    @Test
    void testStartBrewingAfterBrewingCompletes() throws InterruptedException {
        brewingUnit.startBrewing();
        assertFalse(brewingUnit.isBrewingComplete());
        
        Thread.sleep(3100);
        assertTrue(brewingUnit.isBrewingComplete());
        
        brewingUnit.startBrewing();
        assertFalse(brewingUnit.isBrewingComplete());
        
        Thread.sleep(3100);
        assertTrue(brewingUnit.isBrewingComplete());
    }

    @Test
    void testConcurrentBrewingOperations() throws InterruptedException {
        Thread thread1 = new Thread(() -> brewingUnit.startBrewing());
        Thread thread2 = new Thread(() -> brewingUnit.startBrewing());
        Thread thread3 = new Thread(() -> brewingUnit.startBrewing());
        
        thread1.start();
        thread2.start();
        thread3.start();
        
        thread1.join();
        thread2.join();
        thread3.join();
        
        assertFalse(brewingUnit.isBrewingComplete());
        
        Thread.sleep(3100);
        assertTrue(brewingUnit.isBrewingComplete());
    }

    @Test
    void testStopBrewingDuringBrewingProcess() throws InterruptedException {
        brewingUnit.startBrewing();
        assertFalse(brewingUnit.isBrewingComplete());
        
        Thread.sleep(1500);
        assertFalse(brewingUnit.isBrewingComplete());
        
        brewingUnit.stopBrewing();
        Thread.sleep(100);
        
        assertTrue(brewingUnit.isBrewingComplete());
    }
}