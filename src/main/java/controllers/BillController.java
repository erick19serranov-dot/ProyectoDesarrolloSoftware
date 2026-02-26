package controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Factura;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BillController implements Initializable {

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
    private Stage stageActual;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configurar el botón manualmente como respaldo
        if (btn_imprimir_bill != null) {
            btn_imprimir_bill.setOnAction(e -> {
                System.out.println("Botón clickeado desde setOnAction");
                imprimirBill();
            });
        }
    }

    @FXML
    void imprimirBill() {
        System.out.println("=== MÉTODO imprimirBill() EJECUTADO ===");
        
        try {
            // Verificar factura
            if (facturaActual == null) {
                System.out.println("ERROR: facturaActual es null");
                mostrarAlerta(Alert.AlertType.WARNING, "Sin datos", "No hay factura para exportar.");
                return;
            }
            System.out.println("Factura encontrada: #" + facturaActual.getNumeroFactura());

            // Obtener Stage
            Stage stage = obtenerStage();
            if (stage == null) {
                System.out.println("ERROR: No se pudo obtener el Stage");
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo obtener la ventana.");
                return;
            }
            System.out.println("Stage obtenido correctamente");

            // Configurar FileChooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar factura");
            fileChooser.getExtensionFilters().clear();
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Imagen PNG", "*.png")
            );
            fileChooser.setInitialFileName("factura_" + facturaActual.getNumeroFactura() + ".png");
            System.out.println("FileChooser configurado");

            // Mostrar diálogo
            System.out.println("Mostrando diálogo de guardar...");
            File destino = fileChooser.showSaveDialog(stage);
            System.out.println("FileChooser retornó: " + (destino != null ? destino.getAbsolutePath() : "null (cancelado)"));

            if (destino == null) {
                System.out.println("Usuario canceló la operación");
                return;
            }

            // Asegurar extensión .png
            if (!destino.getName().toLowerCase().endsWith(".png")) {
                destino = new File(destino.getParentFile(), destino.getName() + ".png");
            }

            // Guardar imagen
            System.out.println("Guardando imagen en: " + destino.getAbsolutePath());
            guardarFacturaComoImagen(destino);
            System.out.println("Imagen guardada exitosamente");
            
            // Mostrar confirmación
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", 
                    "Factura guardada como imagen:\n" + destino.getAbsolutePath());
                    
        } catch (Exception e) {
            System.err.println("ERROR en imprimirBill():");
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", 
                    "Error al guardar la factura:\n" + e.getMessage() + 
                    "\n\nRevisa la consola para más detalles.");
        }
    }

    private Stage obtenerStage() {
        // Intentar obtener desde el botón
        if (btn_imprimir_bill != null) {
            Scene scene = btn_imprimir_bill.getScene();
            if (scene != null) {
                Stage stage = (Stage) scene.getWindow();
                if (stage != null) {
                    return stage;
                }
            }
        }
        
        // Usar stage guardado si está disponible
        if (stageActual != null) {
            return stageActual;
        }
        
        return null;
    }

    private void guardarFacturaComoImagen(File destino) throws IOException {
        System.out.println("Iniciando guardado de imagen...");
        
        // Obtener Scene
        Scene scene = null;
        if (btn_imprimir_bill != null) {
            scene = btn_imprimir_bill.getScene();
        }
        
        if (scene == null) {
            throw new IOException("No se pudo obtener la escena de la ventana.");
        }
        System.out.println("Scene obtenida");
        
        // Obtener nodo raíz
        Node root = scene.getRoot();
        if (root == null) {
            throw new IOException("No se pudo obtener el nodo raíz.");
        }
        System.out.println("Nodo raíz obtenido: " + root.getClass().getSimpleName());
        
        // Capturar snapshot
        System.out.println("Capturando snapshot...");
        WritableImage image = root.snapshot(null, null);
        if (image == null) {
            throw new IOException("No se pudo capturar la imagen de la factura.");
        }
        System.out.println("Snapshot capturado: " + image.getWidth() + "x" + image.getHeight());
        
        // Convertir a BufferedImage
        System.out.println("Convirtiendo a BufferedImage...");
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        if (bufferedImage == null) {
            throw new IOException("No se pudo convertir la imagen.");
        }
        System.out.println("BufferedImage creado: " + bufferedImage.getWidth() + "x" + bufferedImage.getHeight());
        
        // Escribir archivo
        System.out.println("Escribiendo archivo PNG...");
        boolean escrito = ImageIO.write(bufferedImage, "png", destino);
        if (!escrito) {
            throw new IOException("No se pudo escribir el archivo PNG.");
        }
        System.out.println("Archivo escrito exitosamente");
    }

    public void setFactura(Factura factura, String cliente, String detalles) {
        System.out.println("setFactura() llamado - Factura #" + (factura != null ? factura.getNumeroFactura() : "null"));
        
        this.facturaActual = factura;
        this.nombreCliente = cliente;
        this.detallesFactura = detalles;

        if (txt_name_customer_bill != null) {
            txt_name_customer_bill.setText(cliente != null ? cliente : "");
        }
        if (txt_id_bill != null) {
            txt_id_bill.setText(factura != null ? String.valueOf(factura.getNumeroFactura()) : "");
        }
        if (txt_subtotal_bill != null && factura != null) {
            txt_subtotal_bill.setText(String.format("%.2f", factura.getSubtotal()));
        }
        if (txt_iva_bill != null && factura != null) {
            txt_iva_bill.setText(String.format("%.2f", factura.getMontoIva()));
        }
        if (txt_total_bill != null && factura != null) {
            txt_total_bill.setText(String.format("%.2f", factura.getTotal()));
        }
        if (txt_date_bill != null) {
            txt_date_bill.setText(java.time.LocalDate.now().toString());
        }
        
        System.out.println("Datos de factura cargados en la interfaz");
    }

    public void setStage(Stage stage) {
        this.stageActual = stage;
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
