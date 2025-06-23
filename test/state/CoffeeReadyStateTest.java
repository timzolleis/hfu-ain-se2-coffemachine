package state;

import event.CoffeeMachineEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import parts.CoffeeMachine;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CoffeeReadyStateTest {

    @Mock
    private CoffeeMachine coffeeMachine;

    private CoffeeReadyState coffeeReadyState;

    @BeforeEach
    void setUp() {
        coffeeReadyState = new CoffeeReadyState(coffeeMachine);
    }

    @Test
    void testConstructorSetsCorrectCoffeeMachine() {
        assertNotNull(coffeeReadyState);
        assertEquals(coffeeMachine, coffeeReadyState.coffeeMachine);
    }

    @Test
    void testHandleEventWithCupRemovedReturnsNewIdleState() {
        CoffeeMachineState newState = coffeeReadyState.handleEvent(CoffeeMachineEvent.CUP_REMOVED);
        
        assertNotNull(newState);
        assertInstanceOf(IdleState.class, newState);
        assertEquals(coffeeMachine, newState.coffeeMachine);
    }

    @Test
    void testHandleEventWithOtherEventsReturnsSameInstance() {
        CoffeeMachineState result1 = coffeeReadyState.handleEvent(CoffeeMachineEvent.INSERT_COIN);
        CoffeeMachineState result2 = coffeeReadyState.handleEvent(CoffeeMachineEvent.CHECK_INGREDIENTS);
        CoffeeMachineState result3 = coffeeReadyState.handleEvent(CoffeeMachineEvent.INGREDIENTS_OK);
        CoffeeMachineState result4 = coffeeReadyState.handleEvent(CoffeeMachineEvent.INGREDIENTS_FAIL);
        CoffeeMachineState result5 = coffeeReadyState.handleEvent(CoffeeMachineEvent.BREWING_COMPLETE);
        CoffeeMachineState result6 = coffeeReadyState.handleEvent(CoffeeMachineEvent.INGREDIENTS_REFILLED);

        assertSame(coffeeReadyState, result1);
        assertSame(coffeeReadyState, result2);
        assertSame(coffeeReadyState, result3);
        assertSame(coffeeReadyState, result4);
        assertSame(coffeeReadyState, result5);
        assertSame(coffeeReadyState, result6);
    }

    @Test
    void testStateTransitionFromCoffeeReadyToIdle() {
        CoffeeMachineState newState = coffeeReadyState.handleEvent(CoffeeMachineEvent.CUP_REMOVED);
        
        assertNotSame(coffeeReadyState, newState);
        assertInstanceOf(IdleState.class, newState);
    }
}