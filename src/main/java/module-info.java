module com.mycompany.proyectoprogramado2026 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.mycompany.proyectoprogramado2026 to javafx.fxml;
    opens controllers to javafx.fxml;
    opens models to javafx.fxml;

    exports controllers;
    exports models;
    
}