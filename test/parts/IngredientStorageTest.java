package parts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IngredientStorageTest {

    private IngredientStorage ingredientStorage;

    @BeforeEach
    void setUp() {
        ingredientStorage = new IngredientStorage();
    }

    @Test
    void testConstructorInitializesIngredientsToFullLevel() {
        assertEquals(100, ingredientStorage.getWaterLevel());
        assertEquals(100, ingredientStorage.getCoffeeLevel());
        assertEquals(100, ingredientStorage.getMilkLevel());
        assertEquals(100, ingredientStorage.getSugarLevel());
    }

    @Test
    void testCanOperateReturnsTrueWhenAllIngredientsAboveMinLevel() {
        assertTrue(ingredientStorage.canOperate());
    }

    @Test
    void testCanOperateReturnsFalseWhenWaterBelowMinLevel() {
        for (int i = 0; i < 10; i++) {
            ingredientStorage.consumeIngredients();
        }
        assertFalse(ingredientStorage.canOperate());
        assertEquals(0, ingredientStorage.getWaterLevel());
    }

    @Test
    void testCanOperateReturnsFalseWhenCoffeeBelowMinLevel() {
        ingredientStorage.refill(CoffeeMachineIngredient.WATER, 100);
        ingredientStorage.refill(CoffeeMachineIngredient.MILK, 100);
        ingredientStorage.refill(CoffeeMachineIngredient.SUGAR, 100);
        
        for (int i = 0; i < 10; i++) {
            ingredientStorage.consumeIngredients();
        }
        assertFalse(ingredientStorage.canOperate());
        assertEquals(0, ingredientStorage.getCoffeeLevel());
    }

    @Test
    void testConsumeIngredientsReducesAllLevelsByTen() {
        ingredientStorage.consumeIngredients();
        
        assertEquals(90, ingredientStorage.getWaterLevel());
        assertEquals(90, ingredientStorage.getCoffeeLevel());
        assertEquals(90, ingredientStorage.getMilkLevel());
        assertEquals(90, ingredientStorage.getSugarLevel());
    }

    @Test
    void testConsumeIngredientsWhenCannotOperateDoesNotChangeLevel() {
        for (int i = 0; i < 10; i++) {
            ingredientStorage.consumeIngredients();
        }
        
        int waterLevel = ingredientStorage.getWaterLevel();
        int coffeeLevel = ingredientStorage.getCoffeeLevel();
        int milkLevel = ingredientStorage.getMilkLevel();
        int sugarLevel = ingredientStorage.getSugarLevel();
        
        ingredientStorage.consumeIngredients();
        
        assertEquals(waterLevel, ingredientStorage.getWaterLevel());
        assertEquals(coffeeLevel, ingredientStorage.getCoffeeLevel());
        assertEquals(milkLevel, ingredientStorage.getMilkLevel());
        assertEquals(sugarLevel, ingredientStorage.getSugarLevel());
    }

    @Test
    void testRefillWaterIncreasesWaterLevel() {
        ingredientStorage.consumeIngredients();
        ingredientStorage.refill(CoffeeMachineIngredient.WATER, 5);
        
        assertEquals(95, ingredientStorage.getWaterLevel());
    }

    @Test
    void testRefillCoffeeIncreasesCoffeeLevel() {
        ingredientStorage.consumeIngredients();
        ingredientStorage.refill(CoffeeMachineIngredient.COFFEE, 5);
        
        assertEquals(95, ingredientStorage.getCoffeeLevel());
    }

    @Test
    void testRefillMilkIncreasesMilkLevel() {
        ingredientStorage.consumeIngredients();
        ingredientStorage.refill(CoffeeMachineIngredient.MILK, 5);
        
        assertEquals(95, ingredientStorage.getMilkLevel());
    }

    @Test
    void testRefillSugarIncreasesSugarLevel() {
        ingredientStorage.consumeIngredients();
        ingredientStorage.refill(CoffeeMachineIngredient.SUGAR, 5);
        
        assertEquals(95, ingredientStorage.getSugarLevel());
    }

    @Test
    void testRefillCannotExceedMaximumLevel() {
        ingredientStorage.refill(CoffeeMachineIngredient.WATER, 50);
        ingredientStorage.refill(CoffeeMachineIngredient.COFFEE, 50);
        ingredientStorage.refill(CoffeeMachineIngredient.MILK, 50);
        ingredientStorage.refill(CoffeeMachineIngredient.SUGAR, 50);
        
        assertEquals(100, ingredientStorage.getWaterLevel());
        assertEquals(100, ingredientStorage.getCoffeeLevel());
        assertEquals(100, ingredientStorage.getMilkLevel());
        assertEquals(100, ingredientStorage.getSugarLevel());
    }

    @Test
    void testRefillAllResetsAllIngredientsToMaxLevel() {
        for (int i = 0; i < 5; i++) {
            ingredientStorage.consumeIngredients();
        }
        
        ingredientStorage.refillAll();
        
        assertEquals(100, ingredientStorage.getWaterLevel());
        assertEquals(100, ingredientStorage.getCoffeeLevel());
        assertEquals(100, ingredientStorage.getMilkLevel());
        assertEquals(100, ingredientStorage.getSugarLevel());
    }

    @Test
    void testMultipleConsumeOperationsUntilEmpty() {
        for (int i = 0; i < 9; i++) {
            assertTrue(ingredientStorage.canOperate());
            ingredientStorage.consumeIngredients();
        }
        
        assertTrue(ingredientStorage.canOperate());
        ingredientStorage.consumeIngredients();
        assertFalse(ingredientStorage.canOperate());
    }

    @Test
    void testMinLevelConstant() {
        for (int i = 0; i < 9; i++) {
            ingredientStorage.consumeIngredients();
        }
        
        assertTrue(ingredientStorage.canOperate());
        assertEquals(10, ingredientStorage.getWaterLevel());
        assertEquals(10, ingredientStorage.getCoffeeLevel());
        assertEquals(10, ingredientStorage.getMilkLevel());
        assertEquals(10, ingredientStorage.getSugarLevel());
    }
}