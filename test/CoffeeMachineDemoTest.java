import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parts.CoffeeMachine;
import state.*;

import static org.junit.jupiter.api.Assertions.*;

class CoffeeMachineDemoTest {

    private CoffeeMachine coffeeMachine;

    @BeforeEach
    void setUp() {
        coffeeMachine = new CoffeeMachine();
    }


    @Test
    void testTransitionsToCoinAcceptedStateOnValidCoin() {
        assertInstanceOf(IdleState.class, coffeeMachine.getCurrentState());

        coffeeMachine.insertCoin(2.00);

        assertInstanceOf(CoinAcceptedState.class, coffeeMachine.getCurrentState());
        assertEquals(2.00, coffeeMachine.getCurrentBalance());
    }


    @Test
    void testCanStartBrewingOnValidCoin() {
        coffeeMachine.insertCoin(2.00);
        assertInstanceOf(CoinAcceptedState.class, coffeeMachine.getCurrentState());
        coffeeMachine.startBrewing();
        assertInstanceOf(BrewingState.class, coffeeMachine.getCurrentState());
    }

    @Test
    void testTransitionsToIdleStateOnInvalidCoin() {
        assertInstanceOf(IdleState.class, coffeeMachine.getCurrentState());
        double initialBalance = coffeeMachine.getCurrentBalance();

        coffeeMachine.insertCoin(0.25);

        assertInstanceOf(IdleState.class, coffeeMachine.getCurrentState());
        assertEquals(initialBalance, coffeeMachine.getCurrentBalance());
    }


    @Test
    void testNormalOperationStateTransitions() throws InterruptedException {
        assertInstanceOf(IdleState.class, coffeeMachine.getCurrentState());
        assertEquals(0.0, coffeeMachine.getCurrentBalance());
        assertTrue(coffeeMachine.isOperational());

        coffeeMachine.insertCoin(2.00);
        assertInstanceOf(CoinAcceptedState.class, coffeeMachine.getCurrentState());
        assertEquals(2.00, coffeeMachine.getCurrentBalance());

        coffeeMachine.startBrewing();

        Thread.sleep(100);
        assertInstanceOf(BrewingState.class, coffeeMachine.getCurrentState());

        assertTrue(coffeeMachine.getIngredientStorage().getWaterLevel() < 100);
        assertTrue(coffeeMachine.getIngredientStorage().getCoffeeLevel() < 100);
        assertTrue(coffeeMachine.getIngredientStorage().getMilkLevel() < 100);
        assertTrue(coffeeMachine.getIngredientStorage().getSugarLevel() < 100);

        Thread.sleep(4000);
        assertInstanceOf(CoffeeReadyState.class, coffeeMachine.getCurrentState());

        coffeeMachine.removeCup();
        assertInstanceOf(IdleState.class, coffeeMachine.getCurrentState());
        assertEquals(0.0, coffeeMachine.getCurrentBalance());
    }

}