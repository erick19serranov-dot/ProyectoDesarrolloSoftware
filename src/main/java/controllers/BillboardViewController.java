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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.Evento;

public class BillboardViewController {

    static AdminViewController adminViewController;

    @FXML
    private Button btn_Admin_Main;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/LoginAdminView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage stage = (Stage) btn_Admin_Main.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

  
    
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
