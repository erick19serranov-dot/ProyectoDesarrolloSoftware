package controllers;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import models.*;
import java.util.Optional;

public class BuyTicketViewController {

    private eventoController eventoCtrl = new eventoController();
    private Evento eventoSeleccionado;

    @FXML private ComboBox<Evento> comboEventos;
    @FXML private GridPane gridAsientos;
    @FXML private Label lblNombreEvento, lblFecha, lblPrecioBase;
    @FXML private TextField txtNombreCliente;
    @FXML private ToggleGroup tipoEntrada;
    @FXML private RadioButton rbGeneral, rbVIP, rbEstudiante;

    @FXML
    public void initialize() {

        comboEventos.getItems().addAll(eventoCtrl.getEventos());
        comboEventos.setOnAction(e -> cargarAsientosEvento());


        tipoEntrada = new ToggleGroup();
        rbGeneral.setToggleGroup(tipoEntrada);
        rbVIP.setToggleGroup(tipoEntrada);
        rbEstudiante.setToggleGroup(tipoEntrada);
        rbGeneral.setSelected(true);
    }

    @FXML
    private void cargarAsientosEvento() {
        eventoSeleccionado = comboEventos.getSelectionModel().getSelectedItem();
        if (eventoSeleccionado != null) {
            lblNombreEvento.setText(eventoSeleccionado.getNombre());
            lblFecha.setText(eventoSeleccionado.getFecha());
            lblPrecioBase.setText("$" + eventoSeleccionado.getPrecioBase());

            gridAsientos.getChildren().clear();

            boolean[][] asientos = eventoSeleccionado.getAsientos();
            int filas = asientos.length;
            int columnas = asientos[0].length;

            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    Rectangle asiento = new Rectangle(30, 30);
                    asiento.setFill(asientos[i][j] ? Color.RED : Color.GREEN);
                    asiento.setStroke(Color.BLACK);

                    final int fila = i;
                    final int columna = j;

                    asiento.setOnMouseClicked(e -> seleccionarAsiento(fila, columna, asiento));

                    gridAsientos.add(asiento, j, i);
                }
            }
        }
    }

    private void seleccionarAsiento(int fila, int columna, Rectangle asiento) {
        if (eventoSeleccionado == null) {
            mostrarAlerta("Error", "Seleccione un evento primero", Alert.AlertType.ERROR);
            return;
        }

        if (eventoSeleccionado.estaOcupado(fila, columna)) {
            mostrarAlerta("Asiento Ocupado", "Este asiento ya está reservado", Alert.AlertType.WARNING);
            return;
        }


        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Compra");
        confirmacion.setHeaderText("Asiento: Fila " + (fila+1) + ", Columna " + (columna+1));
        confirmacion.setContentText("¿Desea comprar este asiento?");

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            procesarVenta(fila, columna);
        }
    }

    @FXML
    private void procesarVenta(int fila, int columna) {
        try {
            String nombreCliente = txtNombreCliente.getText();
            if (nombreCliente.isEmpty()) {
                mostrarAlerta("Error", "Ingrese el nombre del cliente", Alert.AlertType.ERROR);
                return;
            }


            String idEntrada = "ENT-" + System.currentTimeMillis();
            double precioBase = eventoSeleccionado.getPrecioBase();
            Entrada entrada = null;


            if (rbGeneral.isSelected()) {
                entrada = new EntradaGeneral(idEntrada, nombreCliente, fila, columna, precioBase);
            } else if (rbVIP.isSelected()) {
                entrada = new EntradaVIP(idEntrada, nombreCliente, fila, columna, precioBase, 50.0); // Recargo de $50
            } else if (rbEstudiante.isSelected()) {
                entrada = new EntradaEstudiante(idEntrada, nombreCliente, fila, columna, precioBase, 0.30); // 30% descuento
            }

            if (entrada != null && eventoSeleccionado.venderEntrada(entrada)) {
                generarTicket(entrada);
                actualizarVistaAsientos();
                mostrarAlerta("Éxito", "Venta realizada correctamente", Alert.AlertType.INFORMATION);
            }

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al procesar la venta: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void generarTicket(Entrada entrada) {
        try {
            String nombreArchivo = "ticket_" + entrada.getIdEntrada() + ".txt";
            java.io.FileWriter writer = new java.io.FileWriter(nombreArchivo);
            writer.write(entrada.generarDetalle());
            writer.close();
            System.out.println("Ticket generado: " + nombreArchivo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void actualizarVistaAsientos() {
        cargarAsientosEvento();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}