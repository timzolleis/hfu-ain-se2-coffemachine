package state;

import event.CoffeeMachineEvent;
import parts.CoffeeMachine;

public class CheckIngredientsState extends CoffeeMachineState{
    public CheckIngredientsState(CoffeeMachine coffeeMachine) {
        super(coffeeMachine);
    }

    @Override
    public void enter() {
        System.out.println("Checking ingredient availability...");
    }

    @Override
    public void exit() {
        System.out.println("Ingredient check complete.");
    }

    @Override
    public CoffeeMachineState handleEvent(CoffeeMachineEvent event) {
        if (event.equals(CoffeeMachineEvent.INGREDIENTS_OK)) {
            return new BrewingState(this.coffeeMachine);
        }
        if (event.equals(CoffeeMachineEvent.INGREDIENTS_FAIL)) {
            this.coffeeMachine.setOperational(false);
            return new MaintenanceState(this.coffeeMachine);
        }
        return this;
    }
}
