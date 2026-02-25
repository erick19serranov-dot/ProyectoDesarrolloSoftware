package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Administrador;

import java.util.ArrayList;

public class LoginAdminController {

    @FXML private Button btn_login_admin;
    @FXML private PasswordField txt_password_admin;
    @FXML private TextField txt_username_admin;

    @FXML
    void MostrarAdmin(ActionEvent event) {
        if (validarAdmin()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AdminView.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Panel Administrativo");
                stage.show();

                AdminViewController adminController = loader.getController();
                adminController.setNombre(txt_username_admin.getText());

                Stage currentStage = (Stage) btn_login_admin.getScene().getWindow();
                currentStage.close();
            } catch (Exception e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo abrir la vista de administrador.");
                e.printStackTrace();
            }
        }
    }

    private boolean validarAdmin() {
        String nombre = txt_username_admin.getText();
        String password = txt_password_admin.getText();

        if (nombre == null || nombre.trim().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campo obligatorio", "Ingrese el nombre de usuario.");
            return false;
        }
        if (password == null || password.trim().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campo obligatorio", "Ingrese la contraseña.");
            return false;
        }

        Administrador admin = new Administrador("", "");
        admin.cargarDesdeArchivo();
        for (Administrador a : admin.getLista()) {
            if (a.getName().equals(nombre) && a.getPassword().equals(password)) {
                return true;
            }
        }
        mostrarAlerta(Alert.AlertType.ERROR, "Error", "Credenciales incorrectas.");
        return false;
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}