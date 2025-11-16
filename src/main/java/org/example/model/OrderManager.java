package org.example.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class to manage all orders in the system.
 * Maintains a list of all placed orders and generates unique order numbers.
 * @author Aryaman Kumar
 */
public class OrderManager {
    private static OrderManager instance;
    private ArrayList<Order> allOrders;
    private int nextOrderNumber;

    /**
     * Private constructor for singleton pattern.
     */
    private OrderManager() {
        allOrders = new ArrayList<>();
        nextOrderNumber = 1;
    }

    /**
     * Gets the singleton instance of OrderManager.
     * @return the OrderManager instance
     */
    public static OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }

    /**
     * Creates a new order with a unique order number.
     * @return the new order
     */
    public Order createNewOrder() {
        return new Order(nextOrderNumber++);
    }

    /**
     * Adds an order to the list of all orders.
     * @param order the order to add
     */
    public void addOrder(Order order) {
        allOrders.add(order);
    }

    /**
     * Removes an order from the list of all orders.
     * @param order the order to remove
     */
    public void removeOrder(Order order) {
        allOrders.remove(order);
    }

    /**
     * Gets the list of all orders.
     * @return the list of all orders
     */
    public ArrayList<Order> getAllOrders() {
        return allOrders;
    }

    /**
     * Exports all orders to a text file.
     * @param filePath the path to the file
     * @throws IOException if an error occurs while writing to the file
     */
    public void exportOrders(String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("RU Donuts - All Orders\n");
            writer.write("======================\n\n");

            for (Order order : allOrders) {
                writer.write(order.toString());
                writer.write("\n\n");
            }

            writer.write("Total Orders: " + allOrders.size() + "\n");
        }
    }
}
