package state;

import event.CoffeeMachineEvent;
import parts.CoffeeMachine;

public class CoffeeReadyState extends CoffeeMachineState{
    public CoffeeReadyState(CoffeeMachine coffeeMachine) {
        super(coffeeMachine);
    }

    @Override
    public void enter() {
        System.out.println("Coffee is ready! Please remove your cup.");
    }

    @Override
    public void exit() {
        System.out.println("Cup removed. Thank you!");
    }

    @Override
    public CoffeeMachineState handleEvent(CoffeeMachineEvent event) {
        if (event.equals(CoffeeMachineEvent.CUP_REMOVED)) {
            return new IdleState(this.coffeeMachine);
        }
        return this;
    }
}
