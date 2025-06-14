import parts.CoffeeMachine;

public class CoffeeMachineDemo {
    public static void main(String[] args) {
        CoffeeMachine machine = new CoffeeMachine();

        // Test normal operation
        System.out.println("\n--- Testing Normal Operation ---");

        machine.insertCoin(2.00);
        machine.startBrewing();

        // Wait for brewing to complete
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        machine.removeCup();

        // Test error condition
        System.out.println("\n--- Testing Error Condition ---");

        // Consume ingredients to create shortage
        for (int i = 0; i < 20; i++) {
            machine.getIngredientStorage().consumeIngredients();
        }

        machine.insertCoin(2.00);
        machine.startBrewing();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        machine.refillIngredients();
    }
}