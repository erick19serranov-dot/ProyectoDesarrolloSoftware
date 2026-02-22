package controllers;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.Evento;
import models.RepositorioEventos;

public class BuyTicketViewController {

    @FXML
    private Button btn_Cartelera_Entradas;

    @FXML
    private Button btn_Entradas_Entradas;

    @FXML
    private Button btn_add_buy_event;

    @FXML
    private Button btn_admin_Entradas;

    @FXML
    private Button btn_buy_table_buy_event;

    @FXML
    private Button btn_cancel_buy_event;

    @FXML
    private Button btn_delete_table_buy_event;

    @FXML
    private ComboBox<?> cmb_category_event_buy;

    @FXML
    private GridPane gp_billboard_buy_event;

    @FXML
    private GridPane gp_stage_seats;

    @FXML
    private AnchorPane pane_stage_seats;

    @FXML
    private ScrollPane sc_shopping_list_buy;

    @FXML
    private ScrollPane sp_billboard_buy_event;

    @FXML
    private TableView<?> table_shopping_list;

    @FXML
    private TableColumn<?, String> tbl_col_category_buy_event;

    @FXML
    private TableColumn<?, LocalDateTime> tbl_col_date_time_buy_event;

    @FXML
    private TableColumn<?, String> tbl_col_name_event_buy_event;

    @FXML
    private TableColumn<?, String> tbl_col_number_seat_buy_event;

    @FXML
    private TableColumn<?, Double> tbl_col_price_buy_event;

    @FXML
    private TableColumn<?, Double> tbl_col_subtotal_buy_event;

    @FXML
    private TableColumn<?, Double> tbl_col_total_buy_event;

    @FXML
    private TextField txt_amount_event_buy;

    @FXML
    private TextField txt_date_event_buy;

    @FXML
    private TextArea txt_description_event_buy;

    @FXML
    private TextField txt_name_customer;

    @FXML
    private TextField txt_name_event_buy;

    @FXML
    private TextField txt_price_event_buy;

    @FXML
    private TextField txt_search_event_buy;

    @FXML
    private TextField txt_seats_available_event_buy;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        cargarEventos();
        sp_billboard_buy_event.setFitToWidth(true);
    }

    @FXML
    void agregarCompra(ActionEvent event) {
    }

    @FXML
    void cancelarCampos(ActionEvent event) {

    }

    @FXML
    void comprarEntrada(ActionEvent event) {
        try {
            FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/views/BillView.fxml"));
            Parent root = loader.load();
            Stage stage = new javafx.stage.Stage();
            stage.setScene(new javafx.scene.Scene(root));
            stage.setTitle("Factura");
            stage.show();

            //Pasar datos
            BillController billController = loader.getController();
            billController.setNombre(txt_name_customer.getText());

        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo abrir la vista de factura.");
            e.printStackTrace();

        }
    }

    @FXML
    void eliminarCompra(ActionEvent event) {

    }

    @FXML
    void mostrarAdmin(ActionEvent event) {

    }

    @FXML
    void mostrarCartelera(ActionEvent event) {

    }

    @FXML
    void mostrarEntradas(ActionEvent event) {

    }


    private void cargarEventos() {

        gp_billboard_buy_event.getChildren().clear();

        int col = 0;
        int row = 0;

        for (Evento evento : RepositorioEventos.getEventosPublicados()) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EventCardView.fxml"));
                AnchorPane card = loader.load();

                EventCardController controller = loader.getController();
                controller.setEvento(evento);

                gp_billboard_buy_event.add(card, col, row);

                col++;
                if (col == 3) {
                    col = 0;
                    row++;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void crearAsientos() {
        
    }        
    

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
