package parts;

import lombok.Getter;

public class IngredientStorage {
    @Getter
    private int waterLevel;
    @Getter
    private int coffeeLevel;
    @Getter
    private int milkLevel;
    @Getter
    private int sugarLevel;

    private static final int MIN_LEVEL = 10;

    public IngredientStorage() {
        this.waterLevel = 100;
        this.coffeeLevel = 100;
        this.milkLevel = 100;
        this.sugarLevel = 100;
    }

    public final boolean canOperate() {
        return waterLevel >= MIN_LEVEL && coffeeLevel >= MIN_LEVEL &&
                milkLevel >= MIN_LEVEL && sugarLevel >= MIN_LEVEL;
    }

    public void consumeIngredients() {
        if (this.canOperate()) {
            waterLevel -= 10;
            coffeeLevel -= 10;
            milkLevel -= 10;
            sugarLevel -= 10;
        } else {
            System.out.println("Not enough ingredients to operate.");
        }
    }

    public void refill(final CoffeeMachineIngredient ingredient, final int amount) {
        switch (ingredient) {
            case WATER:
                waterLevel = Math.min(100, waterLevel + amount);
                break;
            case COFFEE:
                coffeeLevel = Math.min(100, coffeeLevel + amount);
                break;
            case MILK:
                milkLevel = Math.min(100, milkLevel + amount);
                break;
            case SUGAR:
                sugarLevel = Math.min(100, sugarLevel + amount);
                break;
        }
    }

    public void refillAll() {
        waterLevel = 100;
        coffeeLevel = 100;
        milkLevel = 100;
        sugarLevel = 100;
    }


}
