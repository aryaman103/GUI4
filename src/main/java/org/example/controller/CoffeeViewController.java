package org.example.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.model.*;

/**
 * Controller for the coffee ordering view.
 * Allows users to select cup size, add-ins, and quantity.
 * @author Aryaman Kumar
 */
public class CoffeeViewController implements BaseOrderController {

    @FXML
    private ComboBox<String> cupSizeComboBox;

    @FXML
    private CheckBox whippedCreamCheckBox;

    @FXML
    private CheckBox vanillaCheckBox;

    @FXML
    private CheckBox milkCheckBox;

    @FXML
    private CheckBox caramelCheckBox;

    @FXML
    private CheckBox mochaCheckBox;

    @FXML
    private Spinner<Integer> quantitySpinner;

    @FXML
    private Label priceLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private ImageView coffeeImageView;

    private Order currentOrder;

    /**
     * Initializes the controller.
     */
    @FXML
    public void initialize() {
        // Set up cup size combo box
        cupSizeComboBox.setItems(FXCollections.observableArrayList(
                "Short", "Tall", "Grande", "Venti"
        ));

        // Set up quantity spinner
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        quantitySpinner.setValueFactory(valueFactory);

        // Add listeners
        quantitySpinner.valueProperty().addListener(
                (obs, oldVal, newVal) -> updatePrice()
        );

        // Select first item by default
        cupSizeComboBox.getSelectionModel().selectFirst();

        // Load coffee image
        loadImage();
        updatePrice();
    }

    /**
     * Loads the coffee image.
     */
    private void loadImage() {
        try {
            String imagePath = "/images/coffee.png";
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            coffeeImageView.setImage(image);
        } catch (Exception e) {
            // Image not found, use placeholder or leave empty
            coffeeImageView.setImage(null);
        }
    }

    /**
     * Updates the price label based on current selections.
     */
    @FXML
    private void updatePrice() {
        String selectedSize = cupSizeComboBox.getValue();
        if (selectedSize == null) return;

        int quantity = quantitySpinner.getValue();
        double basePrice = getCupSizePrice(selectedSize);
        int addInsCount = 0;

        if (whippedCreamCheckBox.isSelected()) addInsCount++;
        if (vanillaCheckBox.isSelected()) addInsCount++;
        if (milkCheckBox.isSelected()) addInsCount++;
        if (caramelCheckBox.isSelected()) addInsCount++;
        if (mochaCheckBox.isSelected()) addInsCount++;

        double addInsPrice = addInsCount * 0.25;
        double totalPrice = quantity * (basePrice + addInsPrice);

        priceLabel.setText(String.format("Price: $%.2f", totalPrice));
    }

    /**
     * Gets the price based on cup size.
     * @param size the cup size
     * @return the base price
     */
    private double getCupSizePrice(String size) {
        switch (size) {
            case "Short":
                return 2.39;
            case "Tall":
                return 2.99;
            case "Grande":
                return 3.59;
            case "Venti":
                return 4.19;
            default:
                return 2.39;
        }
    }

    /**
     * Handles the Add to Order button click.
     */
    @FXML
    private void onAddToOrder() {
        try {
            String selectedSize = cupSizeComboBox.getValue();
            int quantity = quantitySpinner.getValue();

            if (selectedSize == null) {
                showError("Please select a cup size");
                return;
            }

            CupSize cupSize = CupSize.valueOf(selectedSize.toUpperCase());
            Coffee coffee = new Coffee(cupSize, quantity);

            // Add selected add-ins
            if (whippedCreamCheckBox.isSelected()) coffee.addAddIn(AddIns.WHIPPED_CREAM);
            if (vanillaCheckBox.isSelected()) coffee.addAddIn(AddIns.VANILLA);
            if (milkCheckBox.isSelected()) coffee.addAddIn(AddIns.MILK);
            if (caramelCheckBox.isSelected()) coffee.addAddIn(AddIns.CARAMEL);
            if (mochaCheckBox.isSelected()) coffee.addAddIn(AddIns.MOCHA);

            currentOrder.addItem(coffee);
            showSuccess("Added to order: " + coffee.toString());
        } catch (Exception e) {
            showError("Error adding to order: " + e.getMessage());
        }
    }

    /**
     * Handles the Close button click.
     */
    @FXML
    private void onClose() {
        Stage stage = (Stage) cupSizeComboBox.getScene().getWindow();
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
