package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import models.Factura;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import java.io.File;
import java.io.IOException;



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
        fileChooser.setTitle("Guardar factura (PDF)");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF (*.pdf)", "*.pdf")
        );
        fileChooser.setInitialFileName("factura_" + facturaActual.getNumeroFactura() + ".pdf");

        Window window = btn_imprimir_bill.getScene() != null ? btn_imprimir_bill.getScene().getWindow() : null;
        File destino = fileChooser.showSaveDialog(window);

        if (destino == null) {
            return;
        }

        if (!destino.getName().toLowerCase().endsWith(".pdf")) {
            destino = new File(destino.getParentFile(), destino.getName() + ".pdf");
        }

        try {
            generarPdfFactura(destino);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Exportar factura", "Factura guardada en PDF:\n" + destino.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo guardar el PDF.");
        }
    }

    private void generarPdfFactura(File destino) throws IOException {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);

            try (PDPageContentStream content = new PDPageContentStream(doc, page)) {
                PDType1Font fontTitulo = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
                PDType1Font fontNormal = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
                float margin = 50;
                float y = page.getMediaBox().getHeight() - margin;
                float lineHeight = 14f;

                content.beginText();
                content.setFont(fontTitulo, 16);
                content.newLineAtOffset(margin, y);
                content.showText("FACTURA #" + facturaActual.getNumeroFactura());
                content.endText();
                y -= lineHeight * 1.5f;

                content.beginText();
                content.setFont(fontNormal, 12);
                content.newLineAtOffset(margin, y);
                content.showText("Cliente: " + (nombreCliente != null ? nombreCliente : ""));
                content.endText();
                y -= lineHeight;

                content.beginText();
                content.newLineAtOffset(margin, y);
                content.showText("Fecha: " + java.time.LocalDate.now());
                content.endText();
                y -= lineHeight * 1.2f;

                if (detallesFactura != null && !detallesFactura.isEmpty()) {
                    content.beginText();
                    content.newLineAtOffset(margin, y);
                    content.showText("Detalle:");
                    content.endText();
                    y -= lineHeight;
                    for (String linea : detallesFactura.split("\n")) {
                        if (y < margin + lineHeight) break;
                        content.beginText();
                        content.newLineAtOffset(margin + 10, y);
                        content.showText(linea);
                        content.endText();
                        y -= lineHeight;
                    }
                    y -= lineHeight * 0.5f;
                }

                content.beginText();
                content.newLineAtOffset(margin, y);
                content.showText("Subtotal: " + String.format("%.2f", facturaActual.getSubtotal()));
                content.endText();
                y -= lineHeight;

                content.beginText();
                content.newLineAtOffset(margin, y);
                content.showText("IVA (13%): " + String.format("%.2f", facturaActual.getMontoIva()));
                content.endText();
                y -= lineHeight;

                content.setFont(fontTitulo, 12);
                content.beginText();
                content.newLineAtOffset(margin, y);
                content.showText("TOTAL: " + String.format("%.2f", facturaActual.getTotal()));
                content.endText();
            }

            doc.save(destino);
        }
    }

    
    public void setFactura(Factura factura, String cliente, String detalles) {
        this.facturaActual = factura;
        this.nombreCliente = cliente;
        this.detallesFactura = detalles;

        txt_name_customer_bill.setText(cliente);
        txt_id_bill.setText(String.valueOf(factura.getNumeroFactura()));
        // Subtotal sin IVA
        txt_subtotal_bill.setText(String.format("%.2f", factura.getSubtotal()));
        // Solo el IVA calculado
        txt_iva_bill.setText(String.format("%.2f", factura.getMontoIva()));
        // Total con IVA incluido
        txt_total_bill.setText(String.format("%.2f", factura.getTotal()));
        txt_date_bill.setText(java.time.LocalDate.now().toString());
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}