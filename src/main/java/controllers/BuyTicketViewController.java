package controllers;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.Evento;
import models.RepositorioEventos;

public class BuyTicketViewController {

    private Evento eventoActual;
    private List<ToggleButton> asientosSeleccionados = new ArrayList<>();

    public void setEvento(Evento evento) {
        this.eventoActual = evento;

        // Aquí se cargan los asientos
        cargarAsientos(eventoActual, false);
    }

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
    private TextField txt_name_customer;

    @FXML
    private TextField txt_name_event_buy;

    @FXML
    private TextField txt_price_event_buy;

    @FXML
    private TextField txt_search_event_buy;

    @FXML
    private TextField txt_seats_available_event_buy;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        cargarEventos();
        sp_billboard_buy_event.setFitToWidth(true);
    }

    @FXML
    void agregarCompra(ActionEvent event) {
    }

    @FXML
    void cancelarCampos(ActionEvent event) {

    }

    @FXML
    void comprarEntrada(ActionEvent event) {
        try {
            FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/views/BillView.fxml"));
            Parent root = loader.load();
            Stage stage = new javafx.stage.Stage();
            stage.setScene(new javafx.scene.Scene(root));
            stage.setTitle("Factura");
            stage.show();

            //Pasar datos
            BillController billController = loader.getController();
            billController.setNombre(txt_name_customer.getText());

        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo abrir la vista de factura.");
            e.printStackTrace();

        }
    }

    @FXML
    void eliminarCompra(ActionEvent event) {

    }

    @FXML
    void mostrarAdmin(ActionEvent event) {

    }

    @FXML
    void mostrarCartelera(ActionEvent event) {

    }

    @FXML
    void mostrarEntradas(ActionEvent event) {

    }

    //Método para factura
    public void confirmarReserva() {

        boolean[][] matriz = eventoActual.getAsientos();

        for (ToggleButton t : asientosSeleccionados) {

            String etiqueta = t.getText();

            int fila = etiqueta.charAt(0) - 'A';
            int col = Integer.parseInt(etiqueta.substring(1)) - 1;

            matriz[fila][col] = true;

            t.setDisable(true);
            t.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        }

        asientosSeleccionados.clear();
    }


    private void cargarEventos() {

        gp_billboard_buy_event.getChildren().clear();

        int col = 0;
        int row = 0;

        for (Evento evento : RepositorioEventos.getEventosPublicados()) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EventCardView.fxml"));
                AnchorPane card = loader.load();

                EventCardController controller = loader.getController();
                controller.setEvento(evento);

                controller.setOnEventoSeleccionado(event -> {
                    cargarAsientos(event, false);
                });

                gp_billboard_buy_event.add(card, col, row);

                col++;
                if (col == 3) {
                    col = 0;
                    row++;
                }

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
            row.setPercentHeight(100.0 / 10);
            row.setVgrow(Priority.ALWAYS);
            gp_stage_seats.getRowConstraints().add(row);
        }

        for (int fila = 0; fila < 10; fila++) {
            for (int col = 0; col < 15; col++) {

                if (col == 7) {
                    continue;
                }

                String etiqueta = generarNumeroAsiento(fila, col > 7 ? col - 1 : col);

                ToggleButton asiento = new ToggleButton(etiqueta);
                asiento.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                int columnaReal = col > 7 ? col - 1 : col;

                boolean[][] matriz = evento.getAsientos();

                if (matriz[fila][columnaReal]) {
                    asiento.setDisable(true);
                    asiento.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                } else {
                    asiento.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                }

                int f = fila;
                int c = columnaReal;

                asiento.setOnAction(e -> {

                    if (f == 0 && !isVIP) {
                        asiento.setSelected(false);
                        return;
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

    //Método para obtener los asientos seleccionados en texto
    public List<String> obtenerAsientosSeleccionados() {
        List<String> seleccionados = new ArrayList<>();

        for (ToggleButton t : asientosSeleccionados) {
            seleccionados.add(t.getText());
        }

        return seleccionados;
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
