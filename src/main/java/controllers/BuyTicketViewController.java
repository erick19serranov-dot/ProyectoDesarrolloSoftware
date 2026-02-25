package controllers;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Entrada;
import models.EntradaEstudiante;
import models.EntradaGeneral;
import models.EntradaVIP;
import models.Evento;
import models.Factura;
import models.RepositorioEventos;

public class BuyTicketViewController implements Initializable {

    private Evento eventoActual;
    private List<ToggleButton> asientosSeleccionados = new ArrayList<>();
    private ObservableList<Entrada> carrito = FXCollections.observableArrayList();

    @FXML private Button btn_Cartelera_Entradas;
    @FXML private Button btn_Entradas_Entradas;
    @FXML private Button btn_add_buy_event;
    @FXML private Button btn_admin_Entradas;
    @FXML private Button btn_buy_table_buy_event;
    @FXML private Button btn_cancel_buy_event;
    @FXML private Button btn_delete_table_buy_event;
    @FXML private ComboBox<String> cmb_category_event_buy;
    @FXML private GridPane gp_billboard_buy_event;
    @FXML private GridPane gp_stage_seats;
    @FXML private AnchorPane pane_stage_seats;
    @FXML private ScrollPane sc_shopping_list_buy;
    @FXML private ScrollPane sp_billboard_buy_event;
    @FXML private TableView<Entrada> table_shopping_list;
    @FXML private TableColumn<Entrada, String> tbl_col_category_buy_event;
    @FXML private TableColumn<Entrada, LocalDateTime> tbl_col_date_time_buy_event;
    @FXML private TableColumn<Entrada, String> tbl_col_name_event_buy_event;
    @FXML private TableColumn<Entrada, String> tbl_col_number_seat_buy_event;
    @FXML private TableColumn<Entrada, Double> tbl_col_price_buy_event;
    @FXML private TableColumn<Entrada, Double> tbl_col_subtotal_buy_event;
    @FXML private TableColumn<Entrada, Double> tbl_col_total_buy_event;
    @FXML private TextField txt_amount_event_buy;
    @FXML private TextField txt_date_event_buy;
    @FXML private TextArea txt_description_event_buy;
    @FXML private TextField txt_name_customer;
    @FXML private TextField txt_name_event_buy;
    @FXML private TextField txt_price_event_buy;
    @FXML private TextField txt_seats_available_event_buy;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarEventos();
        sp_billboard_buy_event.setFitToWidth(true);
        configurarComboTipo();
        configurarTablaCarrito();
        table_shopping_list.setItems(carrito);
    }

    private void configurarComboTipo() {
        cmb_category_event_buy.getItems().addAll("General", "VIP", "Estudiante");
        cmb_category_event_buy.setValue("General");
    }

    private void configurarTablaCarrito() {
        tbl_col_number_seat_buy_event.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAsientoTexto()));
        tbl_col_category_buy_event.setCellValueFactory(cellData -> {
            Entrada e = cellData.getValue();
            if (e instanceof EntradaVIP) return new SimpleStringProperty("VIP");
            if (e instanceof EntradaEstudiante) return new SimpleStringProperty("Estudiante");
            return new SimpleStringProperty("General");
        });
        tbl_col_price_buy_event.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().calcularPrecioFinal(eventoActual.getPrecioBase())).asObject());
        tbl_col_subtotal_buy_event.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().calcularPrecioFinal(eventoActual.getPrecioBase())).asObject());
        tbl_col_total_buy_event.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().calcularPrecioFinal(eventoActual.getPrecioBase())).asObject());
        tbl_col_name_event_buy_event.setCellValueFactory(cellData -> new SimpleStringProperty(eventoActual != null ? eventoActual.getNombre() : ""));
        tbl_col_date_time_buy_event.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(eventoActual != null ? eventoActual.getHora() : null));
    }

    public void setEvento(Evento evento) {
        this.eventoActual = evento;
        cargarAsientos(eventoActual, false);
        actualizarInfoEvento();
    }

    private void actualizarInfoEvento() {
        if (eventoActual != null) {
            txt_name_event_buy.setText(eventoActual.getNombre());
            txt_description_event_buy.setText(eventoActual.getDescripcion());
            txt_price_event_buy.setText(String.valueOf(eventoActual.getPrecioBase()));
            txt_date_event_buy.setText(eventoActual.getFecha() + " " + eventoActual.getHora().toLocalTime());
            txt_seats_available_event_buy.setText(String.valueOf(contarAsientosDisponibles()));
        }
    }

    private int contarAsientosDisponibles() {
        int count = 0;
        boolean[][] asientos = eventoActual.getAsientos();
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 14; j++)
                if (!asientos[i][j]) count++;
        return count;
    }

    @FXML
    void agregarCompra(ActionEvent event) {
        if (asientosSeleccionados.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin selección", "Seleccione al menos un asiento.");
            return;
        }
        String tipo = cmb_category_event_buy.getValue();
        for (ToggleButton tb : asientosSeleccionados) {
            String etiqueta = tb.getText();
            int fila = etiqueta.charAt(0) - 'A';
            int col = Integer.parseInt(etiqueta.substring(1)) - 1;
            Entrada entrada;
            switch (tipo) {
                case "VIP": entrada = new EntradaVIP("", fila, col); break;
                case "Estudiante": entrada = new EntradaEstudiante("", fila, col); break;
                default: entrada = new EntradaGeneral("", fila, col);
            }
            carrito.add(entrada);
            tb.setDisable(true);
            tb.setStyle("-fx-background-color: orange; -fx-text-fill: black;");
        }
        asientosSeleccionados.clear();
        actualizarTotal();
    }

    private void actualizarTotal() {
        double total = 0;
        for (Entrada e : carrito) total += e.calcularPrecioFinal(eventoActual.getPrecioBase());
        txt_amount_event_buy.setText(String.format("%.2f", total));
    }

    @FXML
    void cancelarCampos(ActionEvent event) {
        for (ToggleButton tb : asientosSeleccionados) {
            tb.setSelected(false);
            tb.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        }
        asientosSeleccionados.clear();
    }

    @FXML
    void comprarEntrada(ActionEvent event) {
        if (carrito.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Carrito vacío", "Agregue entradas al carrito.");
            return;
        }
        String nombreCliente = txt_name_customer.getText();
        if (nombreCliente == null || nombreCliente.trim().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Nombre requerido", "Ingrese el nombre del cliente.");
            return;
        }

        Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "¿Confirmar compra?", ButtonType.OK, ButtonType.CANCEL).showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            for (Entrada e : carrito) eventoActual.venderEntrada(e);
            RepositorioEventos.actualizarEvento(eventoActual);

            double subtotal = 0;
            for (Entrada e : carrito) subtotal += e.calcularPrecioFinal(eventoActual.getPrecioBase());
            Factura factura = new Factura(subtotal);
            String detalles = generarDetalleFactura();
            String textoFactura = factura.generarTextoFactura(nombreCliente, detalles);
            guardarFactura(textoFactura, factura.getNumeroFactura());
            mostrarFactura(factura, nombreCliente, detalles);

            carrito.clear();
            asientosSeleccionados.clear();
            cargarAsientos(eventoActual, false);
            actualizarInfoEvento();
            txt_name_customer.clear();
            txt_amount_event_buy.clear();
        }
    }

    private String generarDetalleFactura() {
        StringBuilder sb = new StringBuilder();
        for (Entrada e : carrito) {
            String tipo = (e instanceof EntradaVIP) ? "VIP" : (e instanceof EntradaEstudiante) ? "Estudiante" : "General";
            sb.append("Asiento ").append(e.getAsientoTexto()).append(" (").append(tipo).append("): ")
                    .append(String.format("%.2f", e.calcularPrecioFinal(eventoActual.getPrecioBase()))).append("\n");
        }
        return sb.toString();
    }

    private void guardarFactura(String texto, int numero) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("facturas/factura_" + numero + ".txt"))) {
            pw.print(texto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarFactura(Factura factura, String cliente, String detalles) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/BillView.fxml"));
            Parent root = loader.load();
            BillController controller = loader.getController();
            controller.setFactura(factura, cliente, detalles);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Factura");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); 
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo mostrar la factura.");
        }
    }

    @FXML
    void eliminarCompra(ActionEvent event) {
        Entrada seleccionada = table_shopping_list.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            carrito.remove(seleccionada);
            String asientoTexto = seleccionada.getAsientoTexto();
            for (Node node : gp_stage_seats.getChildren()) {
                if (node instanceof ToggleButton) {
                    ToggleButton tb = (ToggleButton) node;
                    if (tb.getText().equals(asientoTexto)) {
                        tb.setDisable(false);
                        tb.setSelected(false);
                        tb.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                        break;
                    }
                }
            }
            actualizarTotal();
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin selección", "Seleccione una entrada del carrito.");
        }
    }

    @FXML
    void mostrarAdmin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/LoginAdminView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Login Administrador");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo abrir el login.");
        }
    }

    @FXML
    void mostrarCartelera(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/BillboardView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Cartelera");
            stage.show();
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo abrir la cartelera.");
            e.printStackTrace();
        }
    }

    @FXML
    void mostrarEntradas(ActionEvent event) {
        cargarEventos();
    }
    

    private void cargarEventos() {
        gp_billboard_buy_event.getChildren().clear();
        int col = 0, row = 0;
        for (Evento evento : RepositorioEventos.getEventosPublicados()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EventCardView.fxml"));
                AnchorPane card = loader.load();
                EventCardController controller = loader.getController();
                controller.setEvento(evento);
                controller.setOnEventoSeleccionado(this::setEvento);
                gp_billboard_buy_event.add(card, col, row);
                col++;
                if (col == 3) { col = 0; row++; }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void cargarAsientos(Evento evento, boolean isVIP) {
        this.eventoActual = evento;
        gp_stage_seats.getChildren().clear();
        asientosSeleccionados.clear();
        gp_stage_seats.getColumnConstraints().clear();
        gp_stage_seats.getRowConstraints().clear();

        for (int c = 0; c < 15; c++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(100.0 / 15);
            col.setHgrow(Priority.ALWAYS);
            gp_stage_seats.getColumnConstraints().add(col);
        }
        for (int f = 0; f < 10; f++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100.0 / 9);
            row.setVgrow(Priority.ALWAYS);
            gp_stage_seats.getRowConstraints().add(row);
        }

        boolean[][] matriz = evento.getAsientos();
        for (int fila = 0; fila < 10; fila++) {
            for (int col = 0; col < 15; col++) {
                if (col == 7) continue;
                int columnaReal = col > 7 ? col - 1 : col;
                String etiqueta = generarNumeroAsiento(fila, columnaReal);
                ToggleButton asiento = new ToggleButton(etiqueta);
                asiento.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                if (matriz[fila][columnaReal]) {
                    asiento.setDisable(true);
                    asiento.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                } else {
                    asiento.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                }

                int f = fila, c = columnaReal;
                asiento.setOnAction(e -> {
                    if (asiento.isDisabled()) return;
                    if (f == 0 && !isVIP) {
                        if (cmb_category_event_buy.getValue() != null && cmb_category_event_buy.getValue().equals("VIP")) {
                            asiento.setSelected(true);
                        } else {
                            asiento.setSelected(false);
                            mostrarAlerta(Alert.AlertType.WARNING, "Fila VIP", "La primera fila es solo para VIP.");
                            return;
                        }
                    }
                    if (asiento.isSelected()) {
                        asiento.setStyle("-fx-background-color: yellow; -fx-text-fill: black;");
                        asientosSeleccionados.add(asiento);
                    } else {
                        asiento.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                        asientosSeleccionados.remove(asiento);
                    }
                });
                gp_stage_seats.add(asiento, col, fila);
            }
        }
    }

    private String generarNumeroAsiento(int fila, int col) {
        char letra = (char) ('A' + fila);
        return letra + String.valueOf(col + 1);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}