package controllers;

import java.io.IOException;
import java.util.List;

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
import models.Evento;

public class BillboardViewController {

    static AdminViewController adminViewController;
    static Evento evento;

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
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo abrir la ventana de cartelera.");
            return;
        }
        Scene scene = new Scene(root);
        Stage stage = (Stage) btn_Cartelera_Main.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        setCartaEvento(evento.getListaEventos());
        
    }

    @FXML
    void MostrarCompraEntradas(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/BuyTicketView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) btn_Entradas_Main.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
        mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo abrir la ventana de compra de entradas.");
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
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo abrir la ventana de login de administrador.");
        }
    }

    public void setEventoBillboard(Parent eventCardView) {

        double anchoScroll = sc_billboard_main.getViewportBounds().getWidth();
        if (anchoScroll == 0) {

            anchoScroll = sc_billboard_main.getPrefViewportWidth();
        }
        if (anchoScroll == 0) {
            anchoScroll = 600;
        }

        double anchoCard = 180;

        int numCols = Math.max(1, (int) (anchoScroll / anchoCard));

        int totalCards = gp_billboard_main.getChildren().size();
        int col = totalCards % numCols;
        int row = totalCards / numCols;

        gp_billboard_main.add(eventCardView, col, row);

        gp_billboard_main.setMaxWidth(anchoCard * numCols);
        gp_billboard_main.setPrefWidth(anchoCard * numCols);

        sc_billboard_main.setHbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.NEVER);
        sc_billboard_main.setVbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

    public void setCartaEvento(List<Evento> eventos) {
        try {

            gp_billboard_main.getChildren().clear();
            gp_billboard_main.getColumnConstraints().clear();
            gp_billboard_main.getRowConstraints().clear();

            int maxColumns = 3;
            double anchoScroll = sc_billboard_main.getWidth();
            if (anchoScroll > 420) { 
                maxColumns = (int) (anchoScroll / 210);
                if (maxColumns < 1) maxColumns = 1;
                if (maxColumns > 5) maxColumns = 5;
            }

            int col = 0;
            int row = 0;
            for (models.Evento evento : eventos) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EventCardView.fxml"));
                javafx.scene.Node eventCard = loader.load();

                controllers.EventCardController cardController = loader.getController();
                cardController.setEvento(evento);

                gp_billboard_main.add(eventCard, col, row);

                col++;
                if (col == maxColumns) {
                    col = 0;
                    row++;
                }
            }

            // Configurar el GridPane para que crezca solo en filas, no en columnas
            gp_billboard_main.setMinWidth(0);
            gp_billboard_main.setMaxWidth(Double.MAX_VALUE);

            sc_billboard_main.setFitToWidth(true);
            // El scroll pane solo scroll vertical
            sc_billboard_main.setHbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.NEVER);
            sc_billboard_main.setVbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.AS_NEEDED);

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(javafx.scene.control.Alert.AlertType.ERROR, "Error", "No se pudieron cargar los eventos.");
        }
    }

    // Llamar setCartaEvento al abrir la cartelera
    @FXML
    public void initialize() {
        // Aquí iría la lógica para obtener la lista de eventos, reemplazar por fuente real
        java.util.List<models.Evento> listaEventos = obtenerEventosParaCartelera();
        setCartaEvento(listaEventos);
    }

    // Método simulado para obtener eventos
    private java.util.List<models.Evento> obtenerEventosParaCartelera() {
        // TODO: Reemplazar con acceso real a los eventos disponibles
        return new java.util.ArrayList<>();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
