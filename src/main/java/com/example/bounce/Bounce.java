package com.example.bounce;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Bounce {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}