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
class MaintenanceStateTest {

    @Mock
    private CoffeeMachine coffeeMachine;

    private MaintenanceState maintenanceState;

    @BeforeEach
    void setUp() {
        maintenanceState = new MaintenanceState(coffeeMachine);
    }

    @Test
    void testConstructorSetsCorrectCoffeeMachine() {
        assertNotNull(maintenanceState);
        assertEquals(coffeeMachine, maintenanceState.coffeeMachine);
    }

    @Test
    void testHandleEventWithIngredientsRefilledReturnsIdleStateAndSetsOperationalTrue() {
        CoffeeMachineState newState = maintenanceState.handleEvent(CoffeeMachineEvent.INGREDIENTS_REFILLED);
        
        assertNotNull(newState);
        assertInstanceOf(IdleState.class, newState);
        assertEquals(coffeeMachine, newState.coffeeMachine);
        verify(coffeeMachine).setOperational(true);
    }

    @Test
    void testHandleEventWithOtherEventsReturnsSameInstance() {
        CoffeeMachineState result1 = maintenanceState.handleEvent(CoffeeMachineEvent.INSERT_COIN);
        CoffeeMachineState result2 = maintenanceState.handleEvent(CoffeeMachineEvent.CHECK_INGREDIENTS);
        CoffeeMachineState result3 = maintenanceState.handleEvent(CoffeeMachineEvent.INGREDIENTS_OK);
        CoffeeMachineState result4 = maintenanceState.handleEvent(CoffeeMachineEvent.INGREDIENTS_FAIL);
        CoffeeMachineState result5 = maintenanceState.handleEvent(CoffeeMachineEvent.BREWING_COMPLETE);
        CoffeeMachineState result6 = maintenanceState.handleEvent(CoffeeMachineEvent.CUP_REMOVED);

        assertSame(maintenanceState, result1);
        assertSame(maintenanceState, result2);
        assertSame(maintenanceState, result3);
        assertSame(maintenanceState, result4);
        assertSame(maintenanceState, result5);
        assertSame(maintenanceState, result6);
    }

    @Test
    void testStateTransitionFromMaintenanceToIdle() {
        CoffeeMachineState newState = maintenanceState.handleEvent(CoffeeMachineEvent.INGREDIENTS_REFILLED);
        
        assertNotSame(maintenanceState, newState);
        assertInstanceOf(IdleState.class, newState);
    }
}