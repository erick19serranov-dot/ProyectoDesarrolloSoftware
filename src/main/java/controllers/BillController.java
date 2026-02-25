package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import models.Factura;

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

    public void setFactura(Factura factura, String cliente, String detalles) {
        txt_name_customer_bill.setText(cliente);
        txt_id_bill.setText(String.valueOf(factura.getNumeroFactura()));
        txt_subtotal_bill.setText(String.format("%.2f", factura.getSubtotal()));
        txt_iva_bill.setText(String.format("%.2f", factura.getSubtotal() * factura.getIva()));
        txt_total_bill.setText(String.format("%.2f", factura.getTotal()));
        txt_date_bill.setText(java.time.LocalDate.now().toString());
    }

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