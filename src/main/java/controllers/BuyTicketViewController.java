package controllers;

import java.io.IOException;
import java.time.LocalDateTime;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

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
        // Cuando se presione el boton de administrador, se abre el loginView sin cerrar BuyView
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/LoginAdminView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Login Administrador");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Para evitar interacción con la ventana principal mientras está abierto
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo abrir la ventana de login de administrador.");
        }
    }

    @FXML
    void mostrarCartelera(ActionEvent event) {
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
        Stage stage = (Stage) btn_Cartelera_Entradas.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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
        Stage stage = (Stage) btn_Entradas_Entradas.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
