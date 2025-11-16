package org.example.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.model.*;

import java.io.File;
import java.io.IOException;

/**
 * Controller for the all orders view.
 * Displays all placed orders and allows browsing and canceling orders.
 * @author Aryaman Kumar
 */
public class AllOrdersViewController implements BaseOrderController {

    @FXML
    private ListView<String> ordersListView;

    @FXML
    private TextArea orderDetailsTextArea;

    @FXML
    private Label subtotalLabel;

    @FXML
    private Label taxLabel;

    @FXML
    private Label totalLabel;

    @FXML
    private Label statusLabel;

    private OrderManager orderManager;

    /**
     * Initializes the controller.
     */
    @FXML
    public void initialize() {
        orderManager = OrderManager.getInstance();
        updateDisplay();

        // Add listener for order selection
        ordersListView.getSelectionModel().selectedIndexProperty().addListener(
                (obs, oldVal, newVal) -> onOrderSelected()
        );
    }

    /**
     * Updates the display with all orders.
     */
    private void updateDisplay() {
        ordersListView.setItems(FXCollections.observableArrayList(
                orderManager.getAllOrders().stream()
                        .map(order -> String.format("Order #%d - $%.2f",
                                order.getOrderNumber(), order.getTotal()))
                        .toList()
        ));

        if (orderManager.getAllOrders().isEmpty()) {
            orderDetailsTextArea.setText("No orders placed yet.");
            clearTotals();
        }
    }

    /**
     * Handles order selection from the list.
     */
    private void onOrderSelected() {
        int selectedIndex = ordersListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= orderManager.getAllOrders().size()) {
            return;
        }

        Order selectedOrder = orderManager.getAllOrders().get(selectedIndex);
        displayOrderDetails(selectedOrder);
    }

    /**
     * Displays the details of an order.
     * @param order the order to display
     */
    private void displayOrderDetails(Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append("Order #").append(order.getOrderNumber()).append("\n");
        sb.append("=" .repeat(40)).append("\n\n");

        for (MenuItem item : order.getMenuItems()) {
            sb.append(item.toString()).append("\n");
        }

        orderDetailsTextArea.setText(sb.toString());

        // Update totals
        subtotalLabel.setText(String.format("$%.2f", order.getSubtotal()));
        taxLabel.setText(String.format("$%.2f", order.getSalesTax()));
        totalLabel.setText(String.format("$%.2f", order.getTotal()));
    }

    /**
     * Clears the totals display.
     */
    private void clearTotals() {
        subtotalLabel.setText("$0.00");
        taxLabel.setText("$0.00");
        totalLabel.setText("$0.00");
    }

    /**
     * Handles the Cancel Selected Order button click.
     */
    @FXML
    private void onCancelOrder() {
        try {
            int selectedIndex = ordersListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex < 0) {
                showError("Please select an order to cancel");
                return;
            }

            Order selectedOrder = orderManager.getAllOrders().get(selectedIndex);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Cancellation");
            alert.setHeaderText("Cancel Order #" + selectedOrder.getOrderNumber() + "?");
            alert.setContentText("This action cannot be undone.");

            if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                orderManager.removeOrder(selectedOrder);
                updateDisplay();
                orderDetailsTextArea.clear();
                clearTotals();
                showSuccess("Order #" + selectedOrder.getOrderNumber() + " cancelled");
            }
        } catch (Exception e) {
            showError("Error cancelling order: " + e.getMessage());
        }
    }

    /**
     * Handles the Export Orders button click.
     */
    @FXML
    private void onExportOrders() {
        try {
            if (orderManager.getAllOrders().isEmpty()) {
                showError("No orders to export");
                return;
            }

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Export Orders");
            fileChooser.setInitialFileName("orders.txt");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt")
            );

            Stage stage = (Stage) ordersListView.getScene().getWindow();
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                orderManager.exportOrders(file.getAbsolutePath());
                showSuccess("Orders exported successfully to: " + file.getName());
            }
        } catch (IOException e) {
            showError("Error exporting orders: " + e.getMessage());
        } catch (Exception e) {
            showError("Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Handles the Close button click.
     */
    @FXML
    private void onClose() {
        Stage stage = (Stage) ordersListView.getScene().getWindow();
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

    /**
     * Sets the current order (not used in this view).
     * @param order the current order
     */
    @Override
    public void setCurrentOrder(Order order) {
        // Not needed for this view
    }
}
