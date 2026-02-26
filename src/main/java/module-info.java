module com.proyectoprogramado2026 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires java.desktop;

    opens controllers to javafx.fxml;
    opens main to javafx.fxml;
    opens models to javafx.base;

    exports main;
}