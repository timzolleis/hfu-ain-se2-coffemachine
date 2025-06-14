package parts;

import event.CoffeeMachineEvent;
import lombok.Getter;
import lombok.Setter;
import state.CoffeeMachineState;
import state.IdleState;

public class CoffeeMachine {
    @Getter
    private CoffeeMachineState currentState;
    @Setter
    private double currentBalance;
    @Setter
    @Getter
    private boolean isOperational;

    @Getter
    private final IngredientStorage ingredientStorage;
    private final CoinValidator coinValidator;
    @Getter
    private final BrewingUnit brewingUnit;

    public CoffeeMachine() {
        this.ingredientStorage = new IngredientStorage();
        this.coinValidator = new CoinValidator();
        this.brewingUnit = new BrewingUnit();
        this.currentState = new IdleState(this);
        this.currentBalance = 0.0;
        this.isOperational = true;
    }

    public void insertCoin(double amount) {
        if (coinValidator.validateCoin(amount)) {
            this.currentBalance += amount;
            System.out.println("Coin accepted: €" + amount + ". Total: €" + this.currentBalance);
            transitionTo(currentState.handleEvent(CoffeeMachineEvent.INSERT_COIN));
        } else {
            System.out.println("Invalid coin rejected: €" + amount);
        }
    }

    public void startBrewing() {
        transitionTo(currentState.handleEvent(CoffeeMachineEvent.CHECK_INGREDIENTS));
        if (this.ingredientStorage.canOperate()) {
            this.transitionTo(currentState.handleEvent(CoffeeMachineEvent.INGREDIENTS_OK));
            this.ingredientStorage.consumeIngredients();
            new Thread(() -> {
                try {
                    Thread.sleep(3500); // Wait for brewing to complete
                    transitionTo(currentState.handleEvent(CoffeeMachineEvent.BREWING_COMPLETE));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        } else {
            transitionTo(currentState.handleEvent(CoffeeMachineEvent.INGREDIENTS_FAIL));
        }
    }

    public void removeCup() {
        transitionTo(currentState.handleEvent(CoffeeMachineEvent.CUP_REMOVED));
    }

    private void transitionTo(CoffeeMachineState newState) {
        if (newState != currentState) {
            currentState.exit();
            currentState = newState;
            currentState.enter();
        }
    }

    public void refillIngredients() {
        ingredientStorage.refillAll();
        transitionTo(currentState.handleEvent(CoffeeMachineEvent.INGREDIENTS_REFILLED));
    }


}
