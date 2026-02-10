package controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;

public class MainController {

   private static EventoController eventoController = new EventoController();


    @FXML
    private void abrirVistaAdmin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Módulo Administrativo");
            stage.show();
        } catch (IOException e) {
            mostrarError("Error al cargar la vista de administración");
        }
    }

    @FXML
    private void abrirVistaCompra() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BuyTicketView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Compra de Entradas");
            stage.show();
        } catch (IOException e) {
            mostrarError("Error al cargar la vista de compra");
        }
    }

    @FXML
    private void abrirVistaLoginAdmin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginAdminView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Inicio de Sesión Administrador");
            stage.show();
        } catch (IOException e) {
            mostrarError("Error al cargar la vista de login");
        }
    }

    @FXML
    private void salir() {

        try {
            PersistenciaEventosController.guardarEventos(eventoController.getEventos());
            System.exit(0);
        } catch (IOException e) {
            mostrarError("Error al guardar los datos");
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static EventoController getEventoController() {
        return eventoController;
    }
}
