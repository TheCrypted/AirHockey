module com.example.bounce {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.bounce to javafx.fxml;
    exports com.example.bounce;
}