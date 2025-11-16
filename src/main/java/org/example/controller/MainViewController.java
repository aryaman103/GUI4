package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.model.Order;
import org.example.model.OrderManager;

import java.io.IOException;

/**
 * Controller for the main view of the application.
 * Handles navigation to different ordering screens and views.
 * @author Aryaman Kumar
 */
public class MainViewController {

    @FXML
    private Label statusLabel;

    private Order currentOrder;
    private OrderManager orderManager;

    /**
     * Initializes the controller.
     */
    @FXML
    public void initialize() {
        orderManager = OrderManager.getInstance();
        currentOrder = orderManager.createNewOrder();
        updateStatus("Current Order #" + currentOrder.getOrderNumber());
    }

    /**
     * Handles the Order Donuts button click.
     */
    @FXML
    private void onOrderDonuts() {
        try {
            openWindow("/view/DonutView.fxml", "Order Donuts");
        } catch (IOException e) {
            showError("Error opening donut order window");
        }
    }

    /**
     * Handles the Order Sandwich button click.
     */
    @FXML
    private void onOrderSandwich() {
        try {
            openWindow("/view/SandwichView.fxml", "Order Sandwich");
        } catch (IOException e) {
            showError("Error opening sandwich order window");
        }
    }

    /**
     * Handles the Order Coffee button click.
     */
    @FXML
    private void onOrderCoffee() {
        try {
            openWindow("/view/CoffeeView.fxml", "Order Coffee");
        } catch (IOException e) {
            showError("Error opening coffee order window");
        }
    }

    /**
     * Handles the View Current Order button click.
     */
    @FXML
    private void onViewCurrentOrder() {
        try {
            openWindow("/view/CurrentOrderView.fxml", "Current Order");
        } catch (IOException e) {
            showError("Error opening current order window");
        }
    }

    /**
     * Handles the View All Orders button click.
     */
    @FXML
    private void onViewAllOrders() {
        try {
            openWindow("/view/AllOrdersView.fxml", "All Orders");
        } catch (IOException e) {
            showError("Error opening all orders window");
        }
    }

    /**
     * Opens a new window with the specified FXML file and title.
     * @param fxmlPath the path to the FXML file
     * @param title the window title
     * @throws IOException if the FXML file cannot be loaded
     */
    private void openWindow(String fxmlPath, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);

        // Pass current order to the controller
        Object controller = loader.getController();
        if (controller instanceof BaseOrderController) {
            ((BaseOrderController) controller).setCurrentOrder(currentOrder);
        }

        stage.show();
    }

    /**
     * Updates the status label.
     * @param message the status message
     */
    private void updateStatus(String message) {
        statusLabel.setText(message);
        statusLabel.setStyle("-fx-text-fill: green;");
    }

    /**
     * Shows an error message in the status label.
     * @param message the error message
     */
    private void showError(String message) {
        statusLabel.setText(message);
        statusLabel.setStyle("-fx-text-fill: red;");
    }

    /**
     * Gets the current order.
     * @return the current order
     */
    public Order getCurrentOrder() {
        return currentOrder;
    }

    /**
     * Sets the current order.
     * @param order the order to set
     */
    public void setCurrentOrder(Order order) {
        this.currentOrder = order;
        updateStatus("Current Order #" + currentOrder.getOrderNumber());
    }
}
