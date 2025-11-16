package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main application class for RU Donuts ordering system.
 * This class launches the JavaFX application.
 * @author Aryaman Kumar
 */
public class RUDonutsApp extends Application {

    /**
     * Starts the JavaFX application.
     * @param stage the primary stage
     * @throws IOException if the FXML file cannot be loaded
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RUDonutsApp.class.getResource("/view/MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("RU Donuts - Order Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main method to launch the application.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}
