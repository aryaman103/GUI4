package org.example.model;

/**
 * Class representing a seasonal donut menu item.
 * Seasonal donuts cost $2.49 each.
 * @author Aryaman Kumar
 */
public class SeasonalDonut extends MenuItem {
    private static final double PRICE_PER_DONUT = 2.49;
    private String flavor;

    /**
     * Constructor for SeasonalDonut.
     * @param flavor the flavor of the seasonal donut (e.g., Spooky, Pumpkin Spice)
     * @param quantity the quantity of donuts
     */
    public SeasonalDonut(String flavor, int quantity) {
        super(quantity);
        this.flavor = flavor;
    }

    /**
     * Gets the flavor of this seasonal donut.
     * @return the flavor
     */
    public String getFlavor() {
        return flavor;
    }

    /**
     * Sets the flavor of this seasonal donut.
     * @param flavor the flavor to set
     */
    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    /**
     * Calculates the price of this seasonal donut order.
     * @return the total price (quantity * price per donut)
     */
    @Override
    public double price() {
        return quantity * PRICE_PER_DONUT;
    }

    /**
     * Returns a string representation of this seasonal donut.
     * @return string representation
     */
    @Override
    public String toString() {
        return "Seasonal Donut (" + flavor + ") x" + quantity + " $" + String.format("%.2f", price());
    }
}
