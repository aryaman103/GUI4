package org.example.model;

/**
 * Abstract class representing a menu item at RU Donuts.
 * All menu items must extend this class.
 * @author Aryaman Kumar
 */
public abstract class MenuItem {
    protected int quantity;

    /**
     * Constructor for MenuItem.
     * @param quantity the quantity of this menu item
     */
    public MenuItem(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the quantity of this menu item.
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of this menu item.
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Calculates the price of this menu item.
     * Must be implemented by all subclasses.
     * @return the price of this menu item
     */
    public abstract double price();

    /**
     * Returns a string representation of this menu item.
     * @return string representation
     */
    @Override
    public abstract String toString();
}
