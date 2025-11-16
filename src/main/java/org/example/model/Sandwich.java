package org.example.model;

import java.util.ArrayList;

/**
 * Class representing a sandwich menu item.
 * Sandwiches have a protein (beef $12.99, chicken $10.99, salmon $14.99),
 * bread type, and optional add-ons (cheese $1.00, veggies $0.30 each).
 * @author Aryaman Kumar
 */
public class Sandwich extends MenuItem {
    private static final double BEEF_PRICE = 12.99;
    private static final double CHICKEN_PRICE = 10.99;
    private static final double SALMON_PRICE = 14.99;
    private static final double CHEESE_PRICE = 1.00;
    private static final double VEGGIE_PRICE = 0.30;

    protected Bread bread;
    protected Protein protein;
    protected ArrayList<AddOns> addOns;

    /**
     * Constructor for Sandwich.
     * @param bread the type of bread
     * @param protein the type of protein
     * @param quantity the quantity of sandwiches
     */
    public Sandwich(Bread bread, Protein protein, int quantity) {
        super(quantity);
        this.bread = bread;
        this.protein = protein;
        this.addOns = new ArrayList<>();
    }

    /**
     * Gets the bread type.
     * @return the bread type
     */
    public Bread getBread() {
        return bread;
    }

    /**
     * Sets the bread type.
     * @param bread the bread type to set
     */
    public void setBread(Bread bread) {
        this.bread = bread;
    }

    /**
     * Gets the protein type.
     * @return the protein type
     */
    public Protein getProtein() {
        return protein;
    }

    /**
     * Sets the protein type.
     * @param protein the protein type to set
     */
    public void setProtein(Protein protein) {
        this.protein = protein;
    }

    /**
     * Gets the list of add-ons.
     * @return the add-ons list
     */
    public ArrayList<AddOns> getAddOns() {
        return addOns;
    }

    /**
     * Adds an add-on to the sandwich.
     * @param addOn the add-on to add
     */
    public void addAddOn(AddOns addOn) {
        if (!addOns.contains(addOn)) {
            addOns.add(addOn);
        }
    }

    /**
     * Removes an add-on from the sandwich.
     * @param addOn the add-on to remove
     */
    public void removeAddOn(AddOns addOn) {
        addOns.remove(addOn);
    }

    /**
     * Calculates the base price based on protein type.
     * @return the base price
     */
    private double getBasePrice() {
        switch (protein) {
            case BEEF:
                return BEEF_PRICE;
            case CHICKEN:
                return CHICKEN_PRICE;
            case SALMON:
                return SALMON_PRICE;
            default:
                return 0.0;
        }
    }

    /**
     * Calculates the price of this sandwich order.
     * @return the total price including quantity, protein, and add-ons
     */
    @Override
    public double price() {
        double basePrice = getBasePrice();
        double addOnsPrice = 0.0;

        for (AddOns addOn : addOns) {
            if (addOn == AddOns.CHEESE) {
                addOnsPrice += CHEESE_PRICE;
            } else {
                addOnsPrice += VEGGIE_PRICE;
            }
        }

        return quantity * (basePrice + addOnsPrice);
    }

    /**
     * Returns a string representation of this sandwich.
     * @return string representation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(protein.toString().charAt(0) + protein.toString().substring(1).toLowerCase());
        sb.append(" Sandwich on ");
        sb.append(bread.toString().replace("_", " ").toLowerCase());

        if (!addOns.isEmpty()) {
            sb.append(" with ");
            for (int i = 0; i < addOns.size(); i++) {
                sb.append(addOns.get(i).toString().toLowerCase());
                if (i < addOns.size() - 1) {
                    sb.append(", ");
                }
            }
        }

        sb.append(" x").append(quantity);
        sb.append(" $").append(String.format("%.2f", price()));

        return sb.toString();
    }
}
