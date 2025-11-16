package org.example.model;

/**
 * Class representing a donut hole menu item.
 * Donut holes cost $0.39 each.
 * @author Aryaman Kumar
 */
public class DonutHole extends MenuItem {
    private static final double PRICE_PER_DONUT = 0.39;
    private String flavor;

    /**
     * Constructor for DonutHole.
     * @param flavor the flavor of the donut hole
     * @param quantity the quantity of donut holes
     */
    public DonutHole(String flavor, int quantity) {
        super(quantity);
        this.flavor = flavor;
    }

    /**
     * Gets the flavor of this donut hole.
     * @return the flavor
     */
    public String getFlavor() {
        return flavor;
    }

    /**
     * Sets the flavor of this donut hole.
     * @param flavor the flavor to set
     */
    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    /**
     * Calculates the price of this donut hole order.
     * @return the total price (quantity * price per donut)
     */
    @Override
    public double price() {
        return quantity * PRICE_PER_DONUT;
    }

    /**
     * Returns a string representation of this donut hole.
     * @return string representation
     */
    @Override
    public String toString() {
        return "Donut Hole (" + flavor + ") x" + quantity + " $" + String.format("%.2f", price());
    }
}
