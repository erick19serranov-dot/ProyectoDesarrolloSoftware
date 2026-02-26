package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import models.Factura;

import java.net.URL;
import java.util.ResourceBundle;

public class BillController {

    @FXML private Button btn_imprimir_bill;
    @FXML private TableColumn<?, String> tbl_col_category_bill;
    @FXML private TableColumn<?, String> tbl_col_evvent_bill;
    @FXML private TableColumn<?, Number> tbl_col_id_bill;
    @FXML private TableColumn<?, String> tbl_col_number_bill;
    @FXML private TableColumn<?, Double> tbl_col_price_bill;
    @FXML private TextField txt_date_bill;
    @FXML private TextField txt_id_bill;
    @FXML private TextField txt_iva_bill;
    @FXML private TextField txt_name_customer_bill;
    @FXML private TextField txt_subtotal_bill;
    @FXML private TextField txt_total_bill;
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//
//    }

    @FXML
    void imprimirBill() {

        mostrarAlerta(Alert.AlertType.INFORMATION, "Imprimir", "Factura generada en archivo.");
    }
    

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}