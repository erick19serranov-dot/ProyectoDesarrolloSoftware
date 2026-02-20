package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BillboardViewController {

    static AdminViewController adminViewController;

    @FXML
    private Button btn_Admin_Main;
    @FXML
    private ImageView image_ucr_billboard;
    @FXML
    private Button btn_Cartelera_Main;
    @FXML
    private Button btn_Entradas_Main;
    @FXML
    private GridPane gp_billboard_main;
    @FXML
    private ScrollPane sc_billboard_main;

    @FXML
    void MostrarCartelera(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/BillboardView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage stage = (Stage) btn_Cartelera_Main.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        adminViewController.publicarEventoTabla(event);
    }

    @FXML
    void MostrarCompraEntradas(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/BuyTicketView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage stage = (Stage) btn_Entradas_Main.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void MostrarLoginAdmin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/LoginAdminView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Login Administrador");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo abrir la ventana de login de administrador.");
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
