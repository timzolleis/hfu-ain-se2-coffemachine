package state;

import event.CoffeeMachineEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import parts.CoffeeMachine;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckIngredientsStateTest {

    @Mock
    private CoffeeMachine coffeeMachine;

    private CheckIngredientsState checkIngredientsState;

    @BeforeEach
    void setUp() {
        checkIngredientsState = new CheckIngredientsState(coffeeMachine);
    }

    @Test
    void testConstructorSetsCorrectCoffeeMachine() {
        assertNotNull(checkIngredientsState);
        assertEquals(coffeeMachine, checkIngredientsState.coffeeMachine);
    }

    @Test
    void testHandleEventWithIngredientsOkReturnsNewBrewingState() {
        CoffeeMachineState newState = checkIngredientsState.handleEvent(CoffeeMachineEvent.INGREDIENTS_OK);
        
        assertNotNull(newState);
        assertInstanceOf(BrewingState.class, newState);
        assertEquals(coffeeMachine, newState.coffeeMachine);
    }

    @Test
    void testHandleEventWithIngredientsFailReturnsMaintenanceStateAndSetsOperationalFalse() {
        CoffeeMachineState newState = checkIngredientsState.handleEvent(CoffeeMachineEvent.INGREDIENTS_FAIL);
        
        assertNotNull(newState);
        assertInstanceOf(MaintenanceState.class, newState);
        assertEquals(coffeeMachine, newState.coffeeMachine);
        verify(coffeeMachine).setOperational(false);
    }

    @Test
    void testHandleEventWithOtherEventsReturnsSameInstance() {
        CoffeeMachineState result1 = checkIngredientsState.handleEvent(CoffeeMachineEvent.INSERT_COIN);
        CoffeeMachineState result2 = checkIngredientsState.handleEvent(CoffeeMachineEvent.CHECK_INGREDIENTS);
        CoffeeMachineState result3 = checkIngredientsState.handleEvent(CoffeeMachineEvent.BREWING_COMPLETE);
        CoffeeMachineState result4 = checkIngredientsState.handleEvent(CoffeeMachineEvent.CUP_REMOVED);
        CoffeeMachineState result5 = checkIngredientsState.handleEvent(CoffeeMachineEvent.INGREDIENTS_REFILLED);

        assertSame(checkIngredientsState, result1);
        assertSame(checkIngredientsState, result2);
        assertSame(checkIngredientsState, result3);
        assertSame(checkIngredientsState, result4);
        assertSame(checkIngredientsState, result5);
    }

    @Test
    void testStateTransitionFromCheckIngredientsToBrewingOnSuccess() {
        CoffeeMachineState newState = checkIngredientsState.handleEvent(CoffeeMachineEvent.INGREDIENTS_OK);
        
        assertNotSame(checkIngredientsState, newState);
        assertInstanceOf(BrewingState.class, newState);
    }

    @Test
    void testStateTransitionFromCheckIngredientsToMaintenanceOnFailure() {
        CoffeeMachineState newState = checkIngredientsState.handleEvent(CoffeeMachineEvent.INGREDIENTS_FAIL);
        
        assertNotSame(checkIngredientsState, newState);
        assertInstanceOf(MaintenanceState.class, newState);
    }
}