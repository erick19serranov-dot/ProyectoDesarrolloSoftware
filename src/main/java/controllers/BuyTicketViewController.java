package controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import models.EntradaGeneral;
import models.Evento;

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
    private TextField txt_name_event_buy;

    @FXML
    private TextField txt_price_event_buy;

    @FXML
    private TextField txt_search_event_buy;

    @FXML
    private TextField txt_seats_available_event_buy;

    @FXML
    void agregarCompra(ActionEvent event) {

    }

    @FXML
    void cancelarCampos(ActionEvent event) {

    }

    @FXML
    void comprarEntrada(ActionEvent event) {
        String nombreEvento = txt_name_event_buy.getText() != null ? txt_name_event_buy.getText().trim() : "";
        String descripcion = txt_description_event_buy.getText() != null ? txt_description_event_buy.getText().trim() : "";
        String precioStr = txt_price_event_buy.getText() != null ? txt_price_event_buy.getText().trim() : "";

        if (nombreEvento.isEmpty()) {
            mostrarAlerta("Campos requeridos", "Seleccione un evento para comprar.", Alert.AlertType.WARNING);
            return;
        }

        double precio;
        try {
            precio = Double.parseDouble(precioStr);
        } catch (NumberFormatException e) {
            mostrarAlerta("Dato inválido", "El precio del evento no es válido.", Alert.AlertType.WARNING);
            return;
        }

        String idEvento = "E" + System.currentTimeMillis();
        Evento eventoFactura = new Evento(idEvento, nombreEvento, descripcion, LocalDate.now(), LocalDateTime.now(), precio);

        String idEntrada = "ENT-" + System.currentTimeMillis();
        EntradaGeneral entrada = new EntradaGeneral(idEntrada, "Invitado", 0, 0, precio);

        BillViewController.mostrarFactura(entrada, eventoFactura);
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
    void mostrarCompraEntradas(ActionEvent event) {

    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
