package org.example.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import org.example.model.AddOns;
import org.example.model.Bread;
import org.example.model.Order;
import org.example.model.Protein;
import org.example.model.Sandwich;

/**
 * Controller for the sandwich ordering view.
 * Allows users to select protein, bread, add-ons, and quantity.
 * @author Aryaman Kumar
 */
public class SandwichViewController implements BaseOrderController {

    @FXML
    private ComboBox<String> proteinComboBox;

    @FXML
    private ComboBox<String> breadComboBox;

    @FXML
    private CheckBox cheeseCheckBox;

    @FXML
    private CheckBox lettuceCheckBox;

    @FXML
    private CheckBox tomatoesCheckBox;

    @FXML
    private CheckBox onionsCheckBox;

    @FXML
    private Spinner<Integer> quantitySpinner;

    @FXML
    private Label priceLabel;

    @FXML
    private Label statusLabel;

    private Order currentOrder;

    /**
     * Initializes the controller.
     */
    @FXML
    public void initialize() {
        // Set up protein combo box
        proteinComboBox.setItems(FXCollections.observableArrayList(
                "Beef", "Chicken", "Salmon"
        ));

        // Set up bread combo box
        breadComboBox.setItems(FXCollections.observableArrayList(
                "Bagel", "Wheat Bread", "Sourdough"
        ));

        // Set up quantity spinner
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        quantitySpinner.setValueFactory(valueFactory);

        // Add listeners
        quantitySpinner.valueProperty().addListener(
                (obs, oldVal, newVal) -> updatePrice()
        );

        // Select first items by default
        proteinComboBox.getSelectionModel().selectFirst();
        breadComboBox.getSelectionModel().selectFirst();

        updatePrice();
    }

    /**
     * Updates the price label based on current selections.
     */
    @FXML
    private void updatePrice() {
        String selectedProtein = proteinComboBox.getValue();
        if (selectedProtein == null) return;

        int quantity = quantitySpinner.getValue();
        double basePrice = getProteinPrice(selectedProtein);
        double addOnsPrice = 0.0;

        if (cheeseCheckBox.isSelected()) addOnsPrice += 1.00;
        if (lettuceCheckBox.isSelected()) addOnsPrice += 0.30;
        if (tomatoesCheckBox.isSelected()) addOnsPrice += 0.30;
        if (onionsCheckBox.isSelected()) addOnsPrice += 0.30;

        double totalPrice = quantity * (basePrice + addOnsPrice);
        priceLabel.setText(String.format("Price: $%.2f", totalPrice));
    }

    /**
     * Gets the price based on protein type.
     * @param protein the protein type
     * @return the base price
     */
    private double getProteinPrice(String protein) {
        switch (protein) {
            case "Beef":
                return 12.99;
            case "Chicken":
                return 10.99;
            case "Salmon":
                return 14.99;
            default:
                return 0.0;
        }
    }

    /**
     * Handles the Add to Order button click.
     */
    @FXML
    private void onAddToOrder() {
        try {
            String selectedProtein = proteinComboBox.getValue();
            String selectedBread = breadComboBox.getValue();
            int quantity = quantitySpinner.getValue();

            if (selectedProtein == null || selectedBread == null) {
                showError("Please select protein and bread");
                return;
            }

            Protein protein = Protein.valueOf(selectedProtein.toUpperCase());
            Bread bread = getBreadEnum(selectedBread);

            Sandwich sandwich = new Sandwich(bread, protein, quantity);

            // Add selected add-ons
            if (cheeseCheckBox.isSelected()) sandwich.addAddOn(AddOns.CHEESE);
            if (lettuceCheckBox.isSelected()) sandwich.addAddOn(AddOns.LETTUCE);
            if (tomatoesCheckBox.isSelected()) sandwich.addAddOn(AddOns.TOMATOES);
            if (onionsCheckBox.isSelected()) sandwich.addAddOn(AddOns.ONIONS);

            currentOrder.addItem(sandwich);
            showSuccess("Added to order: " + sandwich.toString());
        } catch (Exception e) {
            showError("Error adding to order: " + e.getMessage());
        }
    }

    /**
     * Converts bread string to enum.
     * @param bread the bread string
     * @return the Bread enum
     */
    private Bread getBreadEnum(String bread) {
        switch (bread) {
            case "Bagel":
                return Bread.BAGEL;
            case "Wheat Bread":
                return Bread.WHEAT_BREAD;
            case "Sourdough":
                return Bread.SOURDOUGH;
            default:
                return Bread.BAGEL;
        }
    }

    /**
     * Handles the Close button click.
     */
    @FXML
    private void onClose() {
        Stage stage = (Stage) proteinComboBox.getScene().getWindow();
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
     * Sets the current order.
     * @param order the current order
     */
    @Override
    public void setCurrentOrder(Order order) {
        this.currentOrder = order;
    }
}
