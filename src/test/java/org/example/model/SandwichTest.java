package org.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for the Sandwich class.
 * Tests the price() method with different configurations.
 * @author Aryaman Kumar
 */
public class SandwichTest {

    /**
     * Test case 1: Chicken sandwich on bagel with lettuce and tomatoes.
     * Expected price: $10.99 (chicken) + $0.30 (lettuce) + $0.30 (tomatoes) = $11.59
     */
    @Test
    public void testChickenSandwichPrice() {
        Sandwich sandwich = new Sandwich(Bread.BAGEL, Protein.CHICKEN, 1);
        sandwich.addAddOn(AddOns.LETTUCE);
        sandwich.addAddOn(AddOns.TOMATOES);

        double expectedPrice = 11.59;
        double actualPrice = sandwich.price();

        assertEquals(expectedPrice, actualPrice, 0.01,
                "Chicken sandwich with lettuce and tomatoes should cost $11.59");
    }

    /**
     * Test case 2: Beef sandwich on wheat bread with cheese, lettuce, tomatoes, and onions.
     * Expected price: $12.99 (beef) + $1.00 (cheese) + $0.30 (lettuce) +
     *                 $0.30 (tomatoes) + $0.30 (onions) = $14.89 per sandwich
     * Quantity: 2
     * Total: $14.89 * 2 = $29.78
     */
    @Test
    public void testBeefSandwichWithAllAddOns() {
        Sandwich sandwich = new Sandwich(Bread.WHEAT_BREAD, Protein.BEEF, 2);
        sandwich.addAddOn(AddOns.CHEESE);
        sandwich.addAddOn(AddOns.LETTUCE);
        sandwich.addAddOn(AddOns.TOMATOES);
        sandwich.addAddOn(AddOns.ONIONS);

        double expectedPrice = 29.78;
        double actualPrice = sandwich.price();

        assertEquals(expectedPrice, actualPrice, 0.01,
                "Beef sandwich with all add-ons (quantity 2) should cost $29.78");
    }
}
