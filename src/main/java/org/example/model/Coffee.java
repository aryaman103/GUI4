package org.example.model;

import java.util.ArrayList;

/**
 * Class representing a coffee menu item.
 * Coffee has different cup sizes (Short $2.39, Tall $2.99, Grande $3.59, Venti $4.19)
 * and optional add-ins ($0.25 each).
 * @author Aryaman Kumar
 */
public class Coffee extends MenuItem {
    private static final double SHORT_PRICE = 2.39;
    private static final double SIZE_INCREMENT = 0.60;
    private static final double ADDIN_PRICE = 0.25;

    private CupSize size;
    private ArrayList<AddIns> addIns;

    /**
     * Constructor for Coffee.
     * @param size the cup size
     * @param quantity the quantity of coffees
     */
    public Coffee(CupSize size, int quantity) {
        super(quantity);
        this.size = size;
        this.addIns = new ArrayList<>();
    }

    /**
     * Gets the cup size.
     * @return the cup size
     */
    public CupSize getSize() {
        return size;
    }

    /**
     * Sets the cup size.
     * @param size the cup size to set
     */
    public void setSize(CupSize size) {
        this.size = size;
    }

    /**
     * Gets the list of add-ins.
     * @return the add-ins list
     */
    public ArrayList<AddIns> getAddIns() {
        return addIns;
    }

    /**
     * Adds an add-in to the coffee.
     * @param addIn the add-in to add
     */
    public void addAddIn(AddIns addIn) {
        if (!addIns.contains(addIn)) {
            addIns.add(addIn);
        }
    }

    /**
     * Removes an add-in from the coffee.
     * @param addIn the add-in to remove
     */
    public void removeAddIn(AddIns addIn) {
        addIns.remove(addIn);
    }

    /**
     * Calculates the base price based on cup size.
     * @return the base price
     */
    private double getBasePrice() {
        switch (size) {
            case SHORT:
                return SHORT_PRICE;
            case TALL:
                return SHORT_PRICE + SIZE_INCREMENT;
            case GRANDE:
                return SHORT_PRICE + (SIZE_INCREMENT * 2);
            case VENTI:
                return SHORT_PRICE + (SIZE_INCREMENT * 3);
            default:
                return SHORT_PRICE;
        }
    }

    /**
     * Calculates the price of this coffee order.
     * @return the total price including quantity, size, and add-ins
     */
    @Override
    public double price() {
        double basePrice = getBasePrice();
        double addInsPrice = addIns.size() * ADDIN_PRICE;
        return quantity * (basePrice + addInsPrice);
    }

    /**
     * Returns a string representation of this coffee.
     * @return string representation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(size.toString().charAt(0) + size.toString().substring(1).toLowerCase());
        sb.append(" Coffee");

        if (!addIns.isEmpty()) {
            sb.append(" with ");
            for (int i = 0; i < addIns.size(); i++) {
                String addInName = addIns.get(i).toString().replace("_", " ").toLowerCase();
                sb.append(addInName);
                if (i < addIns.size() - 1) {
                    sb.append(", ");
                }
            }
        }

        sb.append(" x").append(quantity);
        sb.append(" $").append(String.format("%.2f", price()));

        return sb.toString();
    }
}
