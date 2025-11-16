package org.example.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.model.*;

/**
 * Controller for the current order view.
 * Displays the current order details and allows modification.
 * @author Aryaman Kumar
 */
public class CurrentOrderViewController implements BaseOrderController {

    @FXML
    private Label orderNumberLabel;

    @FXML
    private ListView<String> orderItemsListView;

    @FXML
    private Label subtotalLabel;

    @FXML
    private Label taxLabel;

    @FXML
    private Label totalLabel;

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
    }

    /**
     * Sets the current order and updates the display.
     * @param order the current order
     */
    @Override
    public void setCurrentOrder(Order order) {
        this.currentOrder = order;
        updateDisplay();
    }

    /**
     * Updates the display with current order information.
     */
    private void updateDisplay() {
        if (currentOrder == null) return;

        orderNumberLabel.setText("Current Order #" + currentOrder.getOrderNumber());

        // Update items list
        orderItemsListView.setItems(FXCollections.observableArrayList(
                currentOrder.getMenuItems().stream()
                        .map(MenuItem::toString)
                        .toList()
        ));

        // Update totals
        subtotalLabel.setText(String.format("$%.2f", currentOrder.getSubtotal()));
        taxLabel.setText(String.format("$%.2f", currentOrder.getSalesTax()));
        totalLabel.setText(String.format("$%.2f", currentOrder.getTotal()));
    }

    /**
     * Handles the Remove Selected Item button click.
     */
    @FXML
    private void onRemoveItem() {
        try {
            int selectedIndex = orderItemsListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex < 0) {
                showError("Please select an item to remove");
                return;
            }

            MenuItem removedItem = currentOrder.getMenuItems().get(selectedIndex);
            currentOrder.removeItem(removedItem);
            updateDisplay();
            showSuccess("Item removed from order");
        } catch (Exception e) {
            showError("Error removing item: " + e.getMessage());
        }
    }

    /**
     * Handles the Clear All Items button click.
     */
    @FXML
    private void onClearAll() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Clear");
            alert.setHeaderText("Clear all items from current order?");
            alert.setContentText("This action cannot be undone.");

            if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                currentOrder.clearItems();
                updateDisplay();
                showSuccess("All items removed from order");
            }
        } catch (Exception e) {
            showError("Error clearing order: " + e.getMessage());
        }
    }

    /**
     * Handles the Place Order button click.
     */
    @FXML
    private void onPlaceOrder() {
        try {
            if (currentOrder.getMenuItems().isEmpty()) {
                showError("Cannot place an empty order");
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Order");
            alert.setHeaderText("Place this order?");
            alert.setContentText(String.format("Total: $%.2f", currentOrder.getTotal()));

            if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                orderManager.addOrder(currentOrder);
                showSuccess("Order #" + currentOrder.getOrderNumber() + " placed successfully!");

                // Create a new current order
                Order newOrder = orderManager.createNewOrder();
                setCurrentOrder(newOrder);

                // Update main view (if needed)
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Order Placed");
                successAlert.setHeaderText("Order placed successfully!");
                successAlert.setContentText("Order #" + (newOrder.getOrderNumber() - 1) +
                        " has been placed.\nNew current order: #" + newOrder.getOrderNumber());
                successAlert.showAndWait();
            }
        } catch (Exception e) {
            showError("Error placing order: " + e.getMessage());
        }
    }

    /**
     * Handles the Close button click.
     */
    @FXML
    private void onClose() {
        Stage stage = (Stage) orderItemsListView.getScene().getWindow();
        stage.close();
    }

    /**
     * Shows a success message.
     * @param message the message to show
     */
    private void showSuccess(String message) {
        statusLabel.setText(message);
        statusLabel.setStyle("-fx-text-fill: green;");
    }

    /**
     * Shows an error message.
     * @param message the message to show
     */
    private void showError(String message) {
        statusLabel.setText(message);
        statusLabel.setStyle("-fx-text-fill: red;");
    }
}
