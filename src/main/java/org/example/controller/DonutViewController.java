package org.example.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.model.*;

import java.util.Arrays;
import java.util.List;

/**
 * Controller for the donut ordering view.
 * Allows users to select donut type, flavor, and quantity.
 * @author Aryaman Kumar
 */
public class DonutViewController implements BaseOrderController {

    @FXML
    private ComboBox<String> donutTypeComboBox;

    @FXML
    private ListView<String> flavorListView;

    @FXML
    private Spinner<Integer> quantitySpinner;

    @FXML
    private Label priceLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private ImageView donutImageView;

    private Order currentOrder;

    // Flavor options for each donut type
    private final List<String> yeastFlavors = Arrays.asList(
            "Glazed", "Jelly", "Chocolate", "Boston Cream", "Maple", "Strawberry"
    );
    private final List<String> cakeFlavors = Arrays.asList(
            "Vanilla", "Chocolate", "Blueberry"
    );
    private final List<String> donutHoleFlavors = Arrays.asList(
            "Plain", "Cinnamon Sugar", "Powdered"
    );
    private final List<String> seasonalFlavors = Arrays.asList(
            "Spooky", "Pumpkin Spice"
    );

    /**
     * Initializes the controller.
     */
    @FXML
    public void initialize() {
        // Set up donut type combo box
        donutTypeComboBox.setItems(FXCollections.observableArrayList(
                "Yeast Donut", "Cake Donut", "Donut Holes", "Seasonal Donut"
        ));

        // Set up quantity spinner
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        quantitySpinner.setValueFactory(valueFactory);

        // Add listeners
        donutTypeComboBox.setOnAction(e -> onDonutTypeChanged());
        flavorListView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> updatePrice()
        );
        quantitySpinner.valueProperty().addListener(
                (obs, oldVal, newVal) -> updatePrice()
        );

        // Select first item by default
        donutTypeComboBox.getSelectionModel().selectFirst();
        onDonutTypeChanged();
    }

    /**
     * Handles donut type selection change.
     */
    @FXML
    private void onDonutTypeChanged() {
        String selectedType = donutTypeComboBox.getValue();
        if (selectedType == null) return;

        switch (selectedType) {
            case "Yeast Donut":
                flavorListView.setItems(FXCollections.observableArrayList(yeastFlavors));
                loadImage("yeast");
                break;
            case "Cake Donut":
                flavorListView.setItems(FXCollections.observableArrayList(cakeFlavors));
                loadImage("cake");
                break;
            case "Donut Holes":
                flavorListView.setItems(FXCollections.observableArrayList(donutHoleFlavors));
                loadImage("holes");
                break;
            case "Seasonal Donut":
                flavorListView.setItems(FXCollections.observableArrayList(seasonalFlavors));
                loadImage("seasonal");
                break;
        }

        flavorListView.getSelectionModel().selectFirst();
        updatePrice();
    }

    /**
     * Loads the donut image based on type.
     * @param type the donut type
     */
    private void loadImage(String type) {
        try {
            String imagePath = "/images/donut_" + type + ".png";
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            donutImageView.setImage(image);
        } catch (Exception e) {
            // Image not found, use placeholder or leave empty
            donutImageView.setImage(null);
        }
    }

    /**
     * Updates the price label based on current selections.
     */
    private void updatePrice() {
        String selectedType = donutTypeComboBox.getValue();
        if (selectedType == null) return;

        int quantity = quantitySpinner.getValue();
        double pricePerDonut = getPricePerDonut(selectedType);
        double totalPrice = quantity * pricePerDonut;

        priceLabel.setText(String.format("Price: $%.2f", totalPrice));
    }

    /**
     * Gets the price per donut based on type.
     * @param type the donut type
     * @return the price per donut
     */
    private double getPricePerDonut(String type) {
        switch (type) {
            case "Yeast Donut":
                return 1.99;
            case "Cake Donut":
                return 2.19;
            case "Donut Holes":
                return 0.39;
            case "Seasonal Donut":
                return 2.49;
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
            String selectedType = donutTypeComboBox.getValue();
            String selectedFlavor = flavorListView.getSelectionModel().getSelectedItem();
            int quantity = quantitySpinner.getValue();

            if (selectedFlavor == null) {
                showError("Please select a flavor");
                return;
            }

            MenuItem donut = createDonut(selectedType, selectedFlavor, quantity);
            currentOrder.addItem(donut);

            showSuccess("Added to order: " + donut.toString());
        } catch (Exception e) {
            showError("Error adding to order: " + e.getMessage());
        }
    }

    /**
     * Creates a donut menu item based on type.
     * @param type the donut type
     * @param flavor the flavor
     * @param quantity the quantity
     * @return the created MenuItem
     */
    private MenuItem createDonut(String type, String flavor, int quantity) {
        switch (type) {
            case "Yeast Donut":
                return new YeastDonut(flavor, quantity);
            case "Cake Donut":
                return new CakeDonut(flavor, quantity);
            case "Donut Holes":
                return new DonutHole(flavor, quantity);
            case "Seasonal Donut":
                return new SeasonalDonut(flavor, quantity);
            default:
                throw new IllegalArgumentException("Unknown donut type: " + type);
        }
    }

    /**
     * Handles the Close button click.
     */
    @FXML
    private void onClose() {
        Stage stage = (Stage) donutTypeComboBox.getScene().getWindow();
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
