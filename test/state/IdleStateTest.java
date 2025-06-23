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
class IdleStateTest {

    @Mock
    private CoffeeMachine coffeeMachine;

    private IdleState idleState;

    @BeforeEach
    void setUp() {
        idleState = new IdleState(coffeeMachine);
    }

    @Test
    void testConstructorSetsCorrectCoffeeMachine() {
        assertNotNull(idleState);
        assertEquals(coffeeMachine, idleState.coffeeMachine);
    }

    @Test
    void testEnterResetsCurrentBalanceToZero() {
        idleState.enter();
        
        verify(coffeeMachine).setCurrentBalance(0.0);
    }

    @Test
    void testHandleEventWithInsertCoinReturnsNewCoinAcceptedState() {
        CoffeeMachineState newState = idleState.handleEvent(CoffeeMachineEvent.INSERT_COIN);
        
        assertNotNull(newState);
        assertInstanceOf(CoinAcceptedState.class, newState);
        assertEquals(coffeeMachine, newState.coffeeMachine);
    }

    @Test
    void testHandleEventWithOtherEventsReturnsSameInstance() {
        CoffeeMachineState result1 = idleState.handleEvent(CoffeeMachineEvent.CHECK_INGREDIENTS);
        CoffeeMachineState result2 = idleState.handleEvent(CoffeeMachineEvent.INGREDIENTS_OK);
        CoffeeMachineState result3 = idleState.handleEvent(CoffeeMachineEvent.INGREDIENTS_FAIL);
        CoffeeMachineState result4 = idleState.handleEvent(CoffeeMachineEvent.BREWING_COMPLETE);
        CoffeeMachineState result5 = idleState.handleEvent(CoffeeMachineEvent.CUP_REMOVED);
        CoffeeMachineState result6 = idleState.handleEvent(CoffeeMachineEvent.INGREDIENTS_REFILLED);

        assertSame(idleState, result1);
        assertSame(idleState, result2);
        assertSame(idleState, result3);
        assertSame(idleState, result4);
        assertSame(idleState, result5);
        assertSame(idleState, result6);
    }

    @Test
    void testStateTransitionFromIdleToCoinAccepted() {
        CoffeeMachineState newState = idleState.handleEvent(CoffeeMachineEvent.INSERT_COIN);
        
        assertNotSame(idleState, newState);
        assertInstanceOf(CoinAcceptedState.class, newState);
    }
}