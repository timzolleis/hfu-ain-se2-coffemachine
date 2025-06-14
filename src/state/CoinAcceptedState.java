package state;

import event.CoffeeMachineEvent;
import parts.CoffeeMachine;

public class CoinAcceptedState extends CoffeeMachineState {
    public CoinAcceptedState(CoffeeMachine coffeeMachine) {
        super(coffeeMachine);
    }

    @Override
    public void enter() {
        System.out.println("Coin accepted. Checking ingredients...");
    }

    @Override
    public void exit() {
        System.out.println("Proceeding to next step...");
    }

    @Override
    public CoffeeMachineState handleEvent(CoffeeMachineEvent event) {
        if (event.equals(CoffeeMachineEvent.CHECK_INGREDIENTS)) {
            return new CheckIngredientsState(coffeeMachine);
        }
        return this;
    }
}
