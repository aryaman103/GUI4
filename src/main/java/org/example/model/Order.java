package org.example.model;

import java.util.ArrayList;

/**
 * Class representing a customer order.
 * Each order has a unique order number and a list of menu items.
 * @author Aryaman Kumar
 */
public class Order {
    private static final double TAX_RATE = 0.06625;
    private int orderNumber;
    private ArrayList<MenuItem> menuItems;

    /**
     * Constructor for Order.
     * @param orderNumber the unique order number
     */
    public Order(int orderNumber) {
        this.orderNumber = orderNumber;
        this.menuItems = new ArrayList<>();
    }

    /**
     * Gets the order number.
     * @return the order number
     */
    public int getOrderNumber() {
        return orderNumber;
    }

    /**
     * Gets the list of menu items in this order.
     * @return the menu items list
     */
    public ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }

    /**
     * Adds a menu item to the order.
     * @param item the menu item to add
     */
    public void addItem(MenuItem item) {
        menuItems.add(item);
    }

    /**
     * Removes a menu item from the order.
     * @param item the menu item to remove
     */
    public void removeItem(MenuItem item) {
        menuItems.remove(item);
    }

    /**
     * Removes all menu items from the order.
     */
    public void clearItems() {
        menuItems.clear();
    }

    /**
     * Calculates the subtotal (before tax) of this order.
     * @return the subtotal
     */
    public double getSubtotal() {
        double total = 0.0;
        for (MenuItem item : menuItems) {
            total += item.price();
        }
        return total;
    }

    /**
     * Calculates the sales tax for this order.
     * @return the sales tax amount
     */
    public double getSalesTax() {
        return getSubtotal() * TAX_RATE;
    }

    /**
     * Calculates the total (including tax) of this order.
     * @return the total amount
     */
    public double getTotal() {
        return getSubtotal() + getSalesTax();
    }

    /**
     * Returns a string representation of this order.
     * @return string representation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order #").append(orderNumber).append("\n");
        for (MenuItem item : menuItems) {
            sb.append("  ").append(item.toString()).append("\n");
        }
        sb.append("Subtotal: $").append(String.format("%.2f", getSubtotal())).append("\n");
        sb.append("Sales Tax: $").append(String.format("%.2f", getSalesTax())).append("\n");
        sb.append("Total: $").append(String.format("%.2f", getTotal()));
        return sb.toString();
    }
}
