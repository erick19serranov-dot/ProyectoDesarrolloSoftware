package controllers;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Administrador;

public class LoginAdminController {

    static Administrador admin;
    @FXML
    private Button btn_login_admin;
    @FXML
    private PasswordField txt_password_admin;
    @FXML
    private TextField txt_username_admin;

    @FXML
    void MostrarAdmin(ActionEvent event) {
        if (validarAdmin()) {
            try {
                FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/views/AdminView.fxml"));
                Parent root = loader.load();
                Stage stage = new javafx.stage.Stage();
                stage.setScene(new javafx.scene.Scene(root));
                stage.setTitle("Panel Administrativo");
                stage.show();
                Stage adminStage = stage;
                for (Stage s : Stage.getWindows().stream().filter(window -> window instanceof Stage).map(window -> (Stage) window) .filter(s -> s != adminStage) .toList()) {
                    s.close();
                }
                AdminViewController admincontroller = loader.getController();
                admincontroller.setNombre(txt_username_admin.getText());
                Stage currentStage = (Stage) btn_login_admin.getScene().getWindow();
                currentStage.close();
            } catch (Exception e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo abrir la vista de administrador.");
                e.printStackTrace();
            }
        }

    }

    private boolean validarAdmin() {
        String nombreIngresado = txt_username_admin.getText();
        String passwordIngresado = txt_password_admin.getText();

        if (nombreIngresado == null || nombreIngresado.trim().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campo obligatorio", "El nombre de usuario no puede estar vacío.");
            return false;
        }
        if (passwordIngresado == null || passwordIngresado.trim().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campo obligatorio", "La contraseña no puede estar vacía.");
            return false;
        }

        try {
            if (admin == null) {
                admin = new Administrador("", "");
            }
            admin.cargarDesdeArchivo();
            ArrayList<Administrador> lista = admin.getLista();

            for (Administrador a : lista) {
                if (a.getName().equals(nombreIngresado) && a.getPassword().equals(passwordIngresado)) {
                    return true;
                }
            }
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Nombre de usuario o contraseña incorrectos.");
            return false;
        } catch (NullPointerException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ha ocurrido un error inesperado.");
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al validar datos");
            e.printStackTrace();
            return false;
        }
    }
    

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
