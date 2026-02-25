package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Evento;
import models.RepositorioEventos;

import java.io.IOException;

public class BillboardViewController {

    @FXML private Button btn_Admin_Main;
    @FXML private ImageView image_ucr_billboard;
    @FXML private Button btn_Cartelera_Main;
    @FXML private Button btn_Entradas_Main;
    @FXML private GridPane gp_billboard_main;
    @FXML private ScrollPane sc_billboard_main;

    @FXML
    public void initialize() {
        cargarEventos();
        sc_billboard_main.setFitToWidth(true);
    }

    @FXML
    void MostrarCartelera(ActionEvent event) {
        cargarEventos();
    }

    @FXML
    void MostrarCompraEntradas(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/BuyTicketView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btn_Entradas_Main.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo abrir la ventana de compra.");
        }
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
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo abrir el login.");
        }
    }

    private void cargarEventos() {
        gp_billboard_main.getChildren().clear();
        int col = 0, row = 0;
        for (Evento evento : RepositorioEventos.getEventosPublicados()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EventCardView.fxml"));
                AnchorPane card = loader.load();
                EventCardController controller = loader.getController();
                controller.setEvento(evento);
                controller.setOnEventoSeleccionado(e -> abrirBuyTicket(e));
                gp_billboard_main.add(card, col, row);
                col++;
                if (col == 3) { col = 0; row++; }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void abrirBuyTicket(Evento evento) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/BuyTicketView.fxml"));
            Parent root = loader.load();
            BuyTicketViewController controller = loader.getController();
            controller.setEvento(evento);
            Stage stage = (Stage) gp_billboard_main.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            ex.printStackTrace();
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