package controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import models.Factura;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

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

    private Factura facturaActual;
    private String nombreCliente;
    private String detallesFactura;

    @FXML
    void imprimirBill() {
        if (facturaActual == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin datos", "No hay factura para exportar.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar factura (imagen)");
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imagen PNG (*.png)", "*.png")
        );
        fileChooser.setInitialFileName("factura_" + facturaActual.getNumeroFactura() + ".png");

        Window window = btn_imprimir_bill.getScene() != null ? btn_imprimir_bill.getScene().getWindow() : null;
        File destino = fileChooser.showSaveDialog(window);

        if (destino == null) {
            return;
        }

        if (!destino.getName().toLowerCase().endsWith(".png")) {
            destino = new File(destino.getParentFile(), destino.getName() + ".png");
        }

        try {
            guardarFacturaComoImagen(destino);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Guardado", "Factura guardada como imagen:\n" + destino.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo guardar la imagen.\n" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error inesperado.\n" + e.getMessage());
        }
    }

    private void guardarFacturaComoImagen(File destino) throws IOException {
        Scene scene = btn_imprimir_bill.getScene();
        if (scene == null) {
            throw new IOException("La ventana de la factura no está disponible.");
        }
        Node root = scene.getRoot();
        WritableImage image = root.snapshot(null, null);
        if (image == null) {
            throw new IOException("No se pudo capturar la factura.");
        }
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        if (bufferedImage == null) {
            throw new IOException("No se pudo convertir la imagen.");
        }
        ImageIO.write(bufferedImage, "png", destino);
    }

    public void setFactura(Factura factura, String cliente, String detalles) {
        this.facturaActual = factura;
        this.nombreCliente = cliente;
        this.detallesFactura = detalles;

        if (txt_name_customer_bill != null) txt_name_customer_bill.setText(cliente != null ? cliente : "");
        if (txt_id_bill != null) txt_id_bill.setText(String.valueOf(factura.getNumeroFactura()));
        if (txt_subtotal_bill != null) txt_subtotal_bill.setText(String.format("%.2f", factura.getSubtotal()));
        if (txt_iva_bill != null) txt_iva_bill.setText(String.format("%.2f", factura.getMontoIva()));
        if (txt_total_bill != null) txt_total_bill.setText(String.format("%.2f", factura.getTotal()));
        if (txt_date_bill != null) txt_date_bill.setText(java.time.LocalDate.now().toString());
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
