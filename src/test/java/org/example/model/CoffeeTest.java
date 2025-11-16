package org.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for the Coffee class.
 * Tests the price() method with different configurations.
 * @author Aryaman Kumar
 */
public class CoffeeTest {

    /**
     * Test case 1: Short coffee with no add-ins.
     * Expected price: $2.39
     */
    @Test
    public void testShortCoffeeNoAddIns() {
        Coffee coffee = new Coffee(CupSize.SHORT, 1);

        double expectedPrice = 2.39;
        double actualPrice = coffee.price();

        assertEquals(expectedPrice, actualPrice, 0.01,
                "Short coffee with no add-ins should cost $2.39");
    }

    /**
     * Test case 2: Tall coffee with whipped cream and vanilla.
     * Expected price: $2.99 (Tall) + $0.25 (whipped cream) + $0.25 (vanilla) = $3.49
     */
    @Test
    public void testTallCoffeeWithTwoAddIns() {
        Coffee coffee = new Coffee(CupSize.TALL, 1);
        coffee.addAddIn(AddIns.WHIPPED_CREAM);
        coffee.addAddIn(AddIns.VANILLA);

        double expectedPrice = 3.49;
        double actualPrice = coffee.price();

        assertEquals(expectedPrice, actualPrice, 0.01,
                "Tall coffee with whipped cream and vanilla should cost $3.49");
    }

    /**
     * Test case 3: Venti coffee with all add-ins, quantity 3.
     * Expected price per coffee: $4.19 (Venti) + $0.25 * 5 (all add-ins) = $5.44
     * Quantity: 3
     * Total: $5.44 * 3 = $16.32
     */
    @Test
    public void testVentiCoffeeWithAllAddIns() {
        Coffee coffee = new Coffee(CupSize.VENTI, 3);
        coffee.addAddIn(AddIns.WHIPPED_CREAM);
        coffee.addAddIn(AddIns.VANILLA);
        coffee.addAddIn(AddIns.MILK);
        coffee.addAddIn(AddIns.CARAMEL);
        coffee.addAddIn(AddIns.MOCHA);

        double expectedPrice = 16.32;
        double actualPrice = coffee.price();

        assertEquals(expectedPrice, actualPrice, 0.01,
                "Venti coffee with all add-ins (quantity 3) should cost $16.32");
    }
}
