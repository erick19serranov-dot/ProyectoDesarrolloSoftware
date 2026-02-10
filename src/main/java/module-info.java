module com.mycompany.proyectoprogramado2026 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.proyectoprogramado2026 to javafx.fxml;
    exports com.mycompany.proyectoprogramado2026;
}
