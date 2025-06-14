package state;

import event.CoffeeMachineEvent;
import parts.CoffeeMachine;

public abstract class CoffeeMachineState {
    protected final CoffeeMachine coffeeMachine;

    public CoffeeMachineState(CoffeeMachine coffeeMachine) {
        this.coffeeMachine = coffeeMachine;
    }

    public abstract void enter();
    public abstract void exit();
    public abstract CoffeeMachineState handleEvent(CoffeeMachineEvent event);
}
