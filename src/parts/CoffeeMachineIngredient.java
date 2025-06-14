package parts;

public enum CoffeeMachineIngredient {
    WATER("Water"),
    COFFEE("Coffee"),
    MILK("Milk"),
    SUGAR("Sugar");

    private final String name;

    CoffeeMachineIngredient(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
