module main.proyectoprogramado2026 {
    
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens main.Main to javafx.fxml;
    opens controllers to javafx.fxml;
    opens models to javafx.fxml;
    
    exports controllers;
    exports models;
    
}