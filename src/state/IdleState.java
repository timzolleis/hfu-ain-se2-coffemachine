package state;

import event.CoffeeMachineEvent;
import parts.CoffeeMachine;

public class IdleState extends CoffeeMachineState{

    public IdleState(final CoffeeMachine coffeeMachine) {
        super(coffeeMachine);
    }

    @Override
    public void enter() {
        System.out.println("Coffee machine is now in idle state.");
        this.coffeeMachine.setCurrentBalance(0.0);
    }

    @Override
    public void exit() {
        System.out.println("Exiting idle state.");
        // Additional cleanup or state transition logic can be added here
    }

    @Override
    public CoffeeMachineState handleEvent(CoffeeMachineEvent event) {
        if (event.equals(CoffeeMachineEvent.INSERT_COIN)) {
            return new CoinAcceptedState(coffeeMachine);
        }
        return this;
    }
}
