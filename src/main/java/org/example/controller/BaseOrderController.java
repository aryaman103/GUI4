package org.example.controller;

import org.example.model.Order;

/**
 * Base interface for controllers that need access to the current order.
 * @author Aryaman Kumar
 */
public interface BaseOrderController {
    /**
     * Sets the current order for this controller.
     * @param order the current order
     */
    void setCurrentOrder(Order order);
}
