package controllers;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

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

    }

    @FXML
    void eliminarCompra(ActionEvent event) {

    }

    @FXML
    void mostrarAdmin(ActionEvent event) {

    }

    @FXML
    void mostrarCartelera(ActionEvent event) {
        crearAsientos();
    }

    @FXML
    void mostrarCompraEntradas(ActionEvent event) {

    }

    private void crearAsientos() {
        gp_stage_seats.getChildren().clear();
    int totalRows = 15; 
    int totalCols = 11; 

    for (int row = 0; row < totalRows; row++) {
        char rowLetter = (char) ('A' + row);
        int seatNum = 1;

        for (int col = 0; col < totalCols; col++) {
            if (col == totalCols / 2) {
                continue;
            }

            String seatLabel = rowLetter + Integer.toString(seatNum);
            javafx.scene.control.ToggleButton seatBtn = new javafx.scene.control.ToggleButton(seatLabel);
            seatBtn.setMaxWidth(Double.MAX_VALUE);
            seatBtn.setMaxHeight(Double.MAX_VALUE);
            gp_stage_seats.add(seatBtn, col, row);
            seatNum++;
        }
    }
}
    
}
