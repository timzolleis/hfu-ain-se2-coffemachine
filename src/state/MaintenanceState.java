package state;

import event.CoffeeMachineEvent;
import parts.CoffeeMachine;

public class MaintenanceState extends CoffeeMachineState{
    public MaintenanceState(CoffeeMachine coffeeMachine) {
        super(coffeeMachine);
    }

    @Override
    public void enter() {
        System.out.println("Error: Insufficient ingredients. Please refill machine.");
    }

    @Override
    public void exit() {
        System.out.println("Machine refilled. Ready to operate.");
    }

    @Override
    public CoffeeMachineState handleEvent(CoffeeMachineEvent event) {
        if (event.equals(CoffeeMachineEvent.INGREDIENTS_REFILLED)) {
            this.coffeeMachine.setOperational(true);
            return new IdleState(this.coffeeMachine);
        }
        return this;
    }
}
