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

import java.io.IOException;
import java.util.List;

public class LoginAdminController {

    @FXML
    private Button btn_login_admin;
    @FXML
    private PasswordField txt_password_admin;
    @FXML
    private TextField txt_username_admin;

    @FXML
    void MostrarAdmin(ActionEvent event) {
        String usuario = txt_username_admin.getText() != null ? txt_username_admin.getText().trim() : "";
        String password = txt_password_admin.getText() != null ? txt_password_admin.getText() : "";

        if (usuario.isEmpty() || password.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor ingrese usuario y contraseña.");
            return;
        }

        /*
        try {
            List<models.Administrador> admins = PersistenciaAdministradores.cargarAdministradores();
            boolean valido = admins.stream()
                    .anyMatch(a -> usuario.equals(a.getName()) && password.equals(a.getPassword()));

            if (valido) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AdminView.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Módulo Administrativo");
                stage.show();

                Stage loginStage = (Stage) btn_login_admin.getScene().getWindow();
                loginStage.close();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Acceso denegado", "Usuario o contraseña incorrectos.");
            }
        } catch (IOException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo cargar la vista de administración.");
        }*/
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
