package state;

import event.CoffeeMachineEvent;
import parts.CoffeeMachine;

public class BrewingState extends CoffeeMachineState{
    public BrewingState(CoffeeMachine coffeeMachine) {
        super(coffeeMachine);
    }

    @Override
    public void enter() {
        System.out.println("Brewing coffee... Please wait.");
        this.coffeeMachine.getBrewingUnit().startBrewing();
    }

    @Override
    public void exit() {
        System.out.println("Brewing complete.");
    }

    @Override
    public CoffeeMachineState handleEvent(CoffeeMachineEvent event) {
        if (event.equals(CoffeeMachineEvent.BREWING_COMPLETE)) {
            return new CoffeeReadyState(this.coffeeMachine);
        }
        return this;
    }
}
