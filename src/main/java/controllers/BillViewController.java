package controllers;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Entrada;
import models.EntradaEstudiante;
import models.EntradaGeneral;
import models.EntradaVIP;
import models.Evento;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BillViewController {

    @FXML
    private TextField txtNumeroFactura;

    @FXML
    private TextField txtFecha;

    @FXML
    private TextField txtSubtotal;

    @FXML
    private TextField txtIVA;

    @FXML
    private TextField txtTotal;

    @FXML
    private TableView<DetalleFactura> tablaDetalle;

    @FXML
    private TableColumn<DetalleFactura, String> colEvento;

    @FXML
    private TableColumn<DetalleFactura, String> colTipoAsiento;

    @FXML
    private TableColumn<DetalleFactura, String> colNumero;

    @FXML
    private TableColumn<DetalleFactura, Double> colPrecio;

    private static final double IVA_PORCENTAJE = 0.13; // 13% IVA típico en Costa Rica

    @FXML
    private void initialize() {
        if (colEvento != null) {
            colEvento.setCellValueFactory(data -> data.getValue().eventoProperty());
        }
        if (colTipoAsiento != null) {
            colTipoAsiento.setCellValueFactory(data -> data.getValue().tipoAsientoProperty());
        }
        if (colNumero != null) {
            colNumero.setCellValueFactory(data -> data.getValue().numeroProperty());
        }
        if (colPrecio != null) {
            colPrecio.setCellValueFactory(data -> data.getValue().precioProperty().asObject());
        }
    }


    public void setData(Entrada entrada, Evento evento) {
        if (entrada == null) {
            return;
        }

        String numeroFactura = "FAC-" + System.currentTimeMillis();
        if (txtNumeroFactura != null) {
            txtNumeroFactura.setText(numeroFactura);
        }

        if (txtFecha != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            txtFecha.setText(LocalDateTime.now().format(formatter));
        }

        double subtotal = entrada.calcularPrecioFinal();
        double iva = subtotal * IVA_PORCENTAJE;
        double total = subtotal + iva;

        if (txtSubtotal != null) {
            txtSubtotal.setText(String.format("%.2f", subtotal));
        }
        if (txtIVA != null) {
            txtIVA.setText(String.format("%.2f", iva));
        }
        if (txtTotal != null) {
            txtTotal.setText(String.format("%.2f", total));
        }

        if (tablaDetalle != null) {
            String nombreEvento = (evento != null && evento.getNombre() != null) ? evento.getNombre() : "";
            String tipo = obtenerTipoEntrada(entrada);
            String numeroAsiento = entrada.getAsientoTexto();

            DetalleFactura detalle = new DetalleFactura(nombreEvento, tipo, numeroAsiento, subtotal);
            ObservableList<DetalleFactura> items = FXCollections.observableArrayList(detalle);
            tablaDetalle.setItems(items);
        }
    }

    private String obtenerTipoEntrada(Entrada entrada) {
        if (entrada instanceof EntradaVIP) {
            return "VIP";
        } else if (entrada instanceof EntradaEstudiante) {
            return "Estudiante";
        } else if (entrada instanceof EntradaGeneral) {
            return "General";
        }
        return "Otro";
    }


    public static void mostrarFactura(Entrada entrada, Evento evento) {
        try {
            FXMLLoader loader = new FXMLLoader(BillViewController.class.getResource("/views/BillView.fxml"));
            Parent root = loader.load();

            BillViewController controller = loader.getController();
            controller.setData(entrada, evento);

            Stage stage = new Stage();
            stage.setTitle("Factura de compra");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static class DetalleFactura {
        private final SimpleStringProperty evento;
        private final SimpleStringProperty tipoAsiento;
        private final SimpleStringProperty numero;
        private final SimpleDoubleProperty precio;

        public DetalleFactura(String evento, String tipoAsiento, String numero, double precio) {
            this.evento = new SimpleStringProperty(evento);
            this.tipoAsiento = new SimpleStringProperty(tipoAsiento);
            this.numero = new SimpleStringProperty(numero);
            this.precio = new SimpleDoubleProperty(precio);
        }

        public SimpleStringProperty eventoProperty() {
            return evento;
        }

        public SimpleStringProperty tipoAsientoProperty() {
            return tipoAsiento;
        }

        public SimpleStringProperty numeroProperty() {
            return numero;
        }

        public SimpleDoubleProperty precioProperty() {
            return precio;
        }
    }
}

