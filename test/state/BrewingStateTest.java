package state;

import event.CoffeeMachineEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import parts.BrewingUnit;
import parts.CoffeeMachine;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BrewingStateTest {

    @Mock
    private CoffeeMachine coffeeMachine;

    @Mock
    private BrewingUnit brewingUnit;

    private BrewingState brewingState;

    @BeforeEach
    void setUp() {
        lenient().when(coffeeMachine.getBrewingUnit()).thenReturn(brewingUnit);
        brewingState = new BrewingState(coffeeMachine);
    }

    @Test
    void testConstructorSetsCorrectCoffeeMachine() {
        assertNotNull(brewingState);
        assertEquals(coffeeMachine, brewingState.coffeeMachine);
    }

    @Test
    void testEnterStartsBrewingUnit() {
        brewingState.enter();
        
        verify(coffeeMachine).getBrewingUnit();
        verify(brewingUnit).startBrewing();
    }


    @Test
    void testHandleEventWithBrewingCompleteReturnsNewCoffeeReadyState() {
        CoffeeMachineState newState = brewingState.handleEvent(CoffeeMachineEvent.BREWING_COMPLETE);
        assertNotNull(newState);
        assertInstanceOf(CoffeeReadyState.class, newState);
    }

    @Test
    void testHandleEventWithOtherEventsReturnsSameInstance() {
        CoffeeMachineState result1 = brewingState.handleEvent(CoffeeMachineEvent.INSERT_COIN);
        CoffeeMachineState result2 = brewingState.handleEvent(CoffeeMachineEvent.CHECK_INGREDIENTS);
        CoffeeMachineState result3 = brewingState.handleEvent(CoffeeMachineEvent.INGREDIENTS_OK);
        CoffeeMachineState result4 = brewingState.handleEvent(CoffeeMachineEvent.INGREDIENTS_FAIL);
        CoffeeMachineState result5 = brewingState.handleEvent(CoffeeMachineEvent.CUP_REMOVED);
        CoffeeMachineState result6 = brewingState.handleEvent(CoffeeMachineEvent.INGREDIENTS_REFILLED);

        assertSame(brewingState, result1);
        assertSame(brewingState, result2);
        assertSame(brewingState, result3);
        assertSame(brewingState, result4);
        assertSame(brewingState, result5);
        assertSame(brewingState, result6);
    }

    @Test
    void testStateTransitionFromBrewingToCoffeeReady() {
        CoffeeMachineState newState = brewingState.handleEvent(CoffeeMachineEvent.BREWING_COMPLETE);
        
        assertNotSame(brewingState, newState);
        assertInstanceOf(CoffeeReadyState.class, newState);
    }
}