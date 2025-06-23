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
class CoinAcceptedStateTest {

    @Mock
    private CoffeeMachine coffeeMachine;

    private CoinAcceptedState coinAcceptedState;

    @BeforeEach
    void setUp() {
        coinAcceptedState = new CoinAcceptedState(coffeeMachine);
    }

    @Test
    void testConstructorSetsCorrectCoffeeMachine() {
        assertNotNull(coinAcceptedState);
        assertEquals(coffeeMachine, coinAcceptedState.coffeeMachine);
    }


    @Test
    void testHandleEventWithCheckIngredientsReturnsNewCheckIngredientsState() {
        CoffeeMachineState newState = coinAcceptedState.handleEvent(CoffeeMachineEvent.CHECK_INGREDIENTS);
        
        assertNotNull(newState);
        assertInstanceOf(CheckIngredientsState.class, newState);
        assertEquals(coffeeMachine, newState.coffeeMachine);
    }

    @Test
    void testHandleEventWithOtherEventsReturnsSameInstance() {
        CoffeeMachineState result1 = coinAcceptedState.handleEvent(CoffeeMachineEvent.INSERT_COIN);
        CoffeeMachineState result2 = coinAcceptedState.handleEvent(CoffeeMachineEvent.INGREDIENTS_OK);
        CoffeeMachineState result3 = coinAcceptedState.handleEvent(CoffeeMachineEvent.INGREDIENTS_FAIL);
        CoffeeMachineState result4 = coinAcceptedState.handleEvent(CoffeeMachineEvent.BREWING_COMPLETE);
        CoffeeMachineState result5 = coinAcceptedState.handleEvent(CoffeeMachineEvent.CUP_REMOVED);
        CoffeeMachineState result6 = coinAcceptedState.handleEvent(CoffeeMachineEvent.INGREDIENTS_REFILLED);

        assertSame(coinAcceptedState, result1);
        assertSame(coinAcceptedState, result2);
        assertSame(coinAcceptedState, result3);
        assertSame(coinAcceptedState, result4);
        assertSame(coinAcceptedState, result5);
        assertSame(coinAcceptedState, result6);
    }

    @Test
    void testStateTransitionFromCoinAcceptedToCheckIngredients() {
        CoffeeMachineState newState = coinAcceptedState.handleEvent(CoffeeMachineEvent.CHECK_INGREDIENTS);
        
        assertNotSame(coinAcceptedState, newState);
        assertInstanceOf(CheckIngredientsState.class, newState);
    }
}