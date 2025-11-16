package org.example.model;

/**
 * Class representing a cake donut menu item.
 * Cake donuts cost $2.19 each.
 * @author Aryaman Kumar
 */
public class CakeDonut extends MenuItem {
    private static final double PRICE_PER_DONUT = 2.19;
    private String flavor;

    /**
     * Constructor for CakeDonut.
     * @param flavor the flavor of the cake donut
     * @param quantity the quantity of donuts
     */
    public CakeDonut(String flavor, int quantity) {
        super(quantity);
        this.flavor = flavor;
    }

    /**
     * Gets the flavor of this donut.
     * @return the flavor
     */
    public String getFlavor() {
        return flavor;
    }

    /**
     * Sets the flavor of this donut.
     * @param flavor the flavor to set
     */
    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    /**
     * Calculates the price of this cake donut order.
     * @return the total price (quantity * price per donut)
     */
    @Override
    public double price() {
        return quantity * PRICE_PER_DONUT;
    }

    /**
     * Returns a string representation of this cake donut.
     * @return string representation
     */
    @Override
    public String toString() {
        return "Cake Donut (" + flavor + ") x" + quantity + " $" + String.format("%.2f", price());
    }
}
