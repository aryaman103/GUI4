package org.example.model;

/**
 * Class representing a yeast donut menu item.
 * Yeast donuts cost $1.99 each.
 * @author Aryaman Kumar
 */
public class YeastDonut extends MenuItem {
    private static final double PRICE_PER_DONUT = 1.99;
    private String flavor;

    /**
     * Constructor for YeastDonut.
     * @param flavor the flavor of the yeast donut
     * @param quantity the quantity of donuts
     */
    public YeastDonut(String flavor, int quantity) {
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
     * Calculates the price of this yeast donut order.
     * @return the total price (quantity * price per donut)
     */
    @Override
    public double price() {
        return quantity * PRICE_PER_DONUT;
    }

    /**
     * Returns a string representation of this yeast donut.
     * @return string representation
     */
    @Override
    public String toString() {
        return "Yeast Donut (" + flavor + ") x" + quantity + " $" + String.format("%.2f", price());
    }
}
