module com.proyectoprogramado2026 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox;

    opens controllers to javafx.fxml;
    opens main to javafx.fxml;
    opens models to javafx.base;

    exports main;
}