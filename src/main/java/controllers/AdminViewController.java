package controllers;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Entrada;
import models.EntradaEstudiante;
import models.EntradaGeneral;
import models.EntradaVIP;
import models.Evento;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminViewController implements Initializable {

    private final EventoC eventoController = MainController.getEventoController();

    @FXML
    private GridPane billboard_GP_manage;
    @FXML
    private ScrollPane billboard_ScP_manage;
    @FXML
    private Button btn_add_event_manage;
    @FXML
    private ToggleButton btn_available_billboard;
    @FXML
    private Button btn_cancel_event_manage;
    @FXML
    private Button btn_delete_event_manage;
    @FXML
    private Button btn_goback_event_manage;
    @FXML
    private Button btn_import_image_event_manage;
    @FXML
    private Button btn_manage_event_billboard;
    @FXML
    private Button btn_post_event_manage;
    @FXML
    private Button btn_update_event_manage;
    @FXML
    private ComboBox<?> cmb_category_event_manage;
    @FXML
    private DatePicker date_event_manage;
    @FXML
    private ImageView image_event_manage;
    @FXML
    private AnchorPane image_pane;
    @FXML
    private Label number_event_active_billboard;
    @FXML
    private Label number_income_concerts_billboard;
    @FXML
    private Label number_income_plays_billboard;
    @FXML
    private Label number_tickets_regular_billboard;
    @FXML
    private Label number_tickets_sold_billboard;
    @FXML
    private Label number_tickets_student_billboard;
    @FXML
    private Label number_tickets_vip_billboard;
    @FXML
    private Label number_total_event_billboard;
    @FXML
    private Label number_total_income_billboard;
    @FXML
    private Label label_value_tickets_sold;
    @FXML
    private Label label_value_events_active;
    @FXML
    private Label label_value_events_total;
    @FXML
    private Spinner<Integer> sp_hour_event_manage;
    @FXML
    private Spinner<Integer> sp_minutes_event_manage;
    @FXML
    private TableView<Evento> table_manage_event;
    @FXML
    private TableColumn<Evento, String> tbl_col_category_manage_event;
    @FXML
    private TableColumn<Evento, LocalDate> tbl_col_date_manage_event;
    @FXML
    private TableColumn<Evento, String> tbl_col_description_manage_event;
    @FXML
    private TableColumn<Evento, String> tbl_col_id_manage_event;
    @FXML
    private TableColumn<Evento, String> tbl_col_name_manage_event;
    @FXML
    private TableColumn<Evento, Double> tbl_col_price_manage_event;
    @FXML
    private TableColumn<Evento, LocalDateTime> tbl_col_time_manage_event;
    @FXML
    private Label text_username_admin;
    @FXML
    private TextField txt_date_manage_billboard;
    @FXML
    private TextField txt_description_event_manage;
    @FXML
    private TextArea txt_description_manage_billboard;
    @FXML
    private TextField txt_name_event_manage;
    @FXML
    private TextField txt_name_manage_billboard;
    @FXML
    private TextField txt_price_event_manage;
    @FXML
    private TextField txt_price_manage_billboard;
    @FXML
    private TextField txt_seats_manage_billboard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarSpinners();
        configurarTabla();
        refrescarTabla();
        actualizarFacturacion();
        table_manage_event.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                cargarEventoEnFormulario(newVal);
            }
        });
    }


    @FXML
    void agregarEventoTabla(ActionEvent event) {
     crearEvento();
    }

    @FXML
    void asignarDisponible(ActionEvent event) {

    }

    @FXML
    void cancelarCampos(ActionEvent event) {
        limpiarCampos();
    }

    @FXML
    void devolverEventoGestion(ActionEvent event) {

    }

    @FXML
    void eliminarEventoTabla(ActionEvent event) {
      borrarEvento();
    }

    @FXML
    void importarImagen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagenes", "*.jpg", "*.png", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            image_event_manage.setImage(new Image(selectedFile.toURI().toString()));
        }
        
    }

    @FXML
    void modificarEventoTabla(ActionEvent event) {
        editarEvento();
    }

    @FXML
    void publicarEventoTabla(ActionEvent event) {

    }

    @FXML
    void regresarPagPrincipal(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/BillboardView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) btn_goback_event_manage.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void limpiarCampos() {
        txt_name_event_manage.clear();
        txt_description_event_manage.clear();
        txt_date_manage_billboard.clear();
        txt_price_event_manage.clear();
        txt_seats_manage_billboard.clear();
        if (date_event_manage != null) date_event_manage.setValue(null);
        if (sp_hour_event_manage != null) sp_hour_event_manage.getValueFactory().setValue(0);
        if (sp_minutes_event_manage != null) sp_minutes_event_manage.getValueFactory().setValue(0);
        image_event_manage.setImage(null);
        table_manage_event.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


    private void configurarSpinners() {
        if (sp_hour_event_manage != null) {
            sp_hour_event_manage.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        }
        if (sp_minutes_event_manage != null) {
            sp_minutes_event_manage.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        }
    }

    private void configurarTabla() {
        tbl_col_id_manage_event.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        tbl_col_name_manage_event.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tbl_col_description_manage_event.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion() != null ? cellData.getValue().getDescripcion() : ""));
        tbl_col_category_manage_event.setCellValueFactory(cellData -> new SimpleStringProperty(""));
        tbl_col_price_manage_event.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrecioBase()).asObject());
        tbl_col_date_manage_event.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFecha()));
        tbl_col_time_manage_event.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getHora()));
    }

    private void refrescarTabla() {
        table_manage_event.getItems().clear();
        table_manage_event.getItems().addAll(eventoController.getEventos());
    }

    private void cargarEventoEnFormulario(Evento e) {
        txt_name_event_manage.setText(e.getNombre());
        txt_description_event_manage.setText(e.getDescripcion() != null ? e.getDescripcion() : "");
        date_event_manage.setValue(e.getFecha());
        txt_price_event_manage.setText(String.valueOf(e.getPrecioBase()));
        if (e.getHora() != null && sp_hour_event_manage != null && sp_minutes_event_manage != null) {
            sp_hour_event_manage.getValueFactory().setValue(e.getHora().getHour());
            sp_minutes_event_manage.getValueFactory().setValue(e.getHora().getMinute());
        }
    }

    private void actualizarFacturacion() {
        int vip = 0, estudiante = 0, regular = 0;
        int totalBoletos = 0;
        int eventosActivos = 0;
        double ingresosConciertos = 0;
        double ingresosTeatro = 0;
        double ingresosTotales = 0;

        LocalDate hoy = LocalDate.now();
        List<Evento> eventos = eventoController.getEventos();

        for (Evento evento : eventos) {
            if (evento.getFecha() != null && !evento.getFecha().isBefore(hoy)) {
                eventosActivos++;
            }
            List<Entrada> entradas = evento.getEntradasVendidas();
            if (entradas != null) {
                for (Entrada e : entradas) {
                    totalBoletos++;
                    if (e instanceof EntradaVIP) vip++;
                    else if (e instanceof EntradaEstudiante) estudiante++;
                    else if (e instanceof EntradaGeneral) regular++;
                }
                double recaudacion = evento.calcularRecaudacion();
                ingresosTotales += recaudacion;
                String nombreEvento = evento.getNombre() != null ? evento.getNombre().toLowerCase() : "";
                if (nombreEvento.contains("concierto")) {
                    ingresosConciertos += recaudacion;
                } else {
                    ingresosTeatro += recaudacion;
                }
            }
        }

        int totalEventos = eventos.size();

        if (number_tickets_vip_billboard != null) number_tickets_vip_billboard.setText(String.valueOf(vip));
        if (number_tickets_student_billboard != null) number_tickets_student_billboard.setText(String.valueOf(estudiante));
        if (number_tickets_regular_billboard != null) number_tickets_regular_billboard.setText(String.valueOf(regular));
        if (label_value_tickets_sold != null) label_value_tickets_sold.setText(String.valueOf(totalBoletos));
        if (label_value_events_active != null) label_value_events_active.setText(String.valueOf(eventosActivos));
        if (label_value_events_total != null) label_value_events_total.setText(String.valueOf(totalEventos));
        if (number_income_concerts_billboard != null) number_income_concerts_billboard.setText(String.format("%.2f", ingresosConciertos));
        if (number_income_plays_billboard != null) number_income_plays_billboard.setText(String.format("%.2f", ingresosTeatro));
        if (number_total_income_billboard != null) number_total_income_billboard.setText(String.format("%.2f", ingresosTotales));
    }

    private void crearEvento() {
   String nombre = txt_name_event_manage.getText() != null ? txt_name_event_manage.getText().trim() : "";
        String descripcion = txt_description_event_manage.getText() != null ? txt_description_event_manage.getText().trim() : "";
        LocalDate fecha = date_event_manage.getValue();
        String precioStr = txt_price_event_manage.getText() != null ? txt_price_event_manage.getText().trim() : "";

        if (nombre.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos requeridos", "Ingrese el nombre del evento.");
            return;
        }
        if (fecha == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos requeridos", "Seleccione la fecha del evento.");
            return;
        }
        double precio;
        try {
            precio = Double.parseDouble(precioStr);
            if (precio < 0) throw new NumberFormatException("Precio no válido");
        } catch (NumberFormatException ex) {
            mostrarAlerta(Alert.AlertType.WARNING, "Dato inválido", "Ingrese un precio válido.");
            return;
        }

        int hora = sp_hour_event_manage != null ? sp_hour_event_manage.getValue() : 0;
        int minutos = sp_minutes_event_manage != null ? sp_minutes_event_manage.getValue() : 0;
        LocalDateTime horaEvento = LocalDateTime.of(fecha.getYear(), fecha.getMonthValue(), fecha.getDayOfMonth(), hora, minutos);

        String id = "E" + System.currentTimeMillis();
        Evento nuevo = new Evento(id, nombre, descripcion, fecha, horaEvento, precio);
        nuevo.setAsientos(new boolean[10][10]);
        eventoController.getEventos().add(nuevo);
        eventoController.guardarCambios();
        refrescarTabla();
        actualizarFacturacion();
        limpiarCampos();
        mostrarAlerta(Alert.AlertType.INFORMATION, "Evento agregado", "El evento se ha agregado correctamente.");
    }

private void borrarEvento() {

      Evento seleccionado = table_manage_event.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin selección", "Seleccione un evento de la tabla para eliminar.");
            return;
        }
        Optional<ButtonType> resultado = new Alert(Alert.AlertType.CONFIRMATION, "¿Eliminar el evento \"" + seleccionado.getNombre() + "\"?", ButtonType.OK, ButtonType.CANCEL).showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            eventoController.getEventos().remove(seleccionado);
            eventoController.guardarCambios();
            refrescarTabla();
            actualizarFacturacion();
            limpiarCampos();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Evento eliminado", "El evento se ha eliminado correctamente.");
        }
    }

private void editarEvento() {

 Evento seleccionado = table_manage_event.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin selección", "Seleccione un evento de la tabla para modificar.");
            return;
        }
        String nombre = txt_name_event_manage.getText() != null ? txt_name_event_manage.getText().trim() : "";
        String descripcion = txt_description_event_manage.getText() != null ? txt_description_event_manage.getText().trim() : "";
        LocalDate fecha = date_event_manage.getValue();
        String precioStr = txt_price_event_manage.getText() != null ? txt_price_event_manage.getText().trim() : "";

        if (nombre.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos requeridos", "Ingrese el nombre del evento.");
            return;
        }
        if (fecha == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos requeridos", "Seleccione la fecha del evento.");
            return;
        }
        double precio;
        try {
            precio = Double.parseDouble(precioStr);
            if (precio < 0) throw new NumberFormatException("Precio no válido");
        } catch (NumberFormatException ex) {
            mostrarAlerta(Alert.AlertType.WARNING, "Dato inválido", "Ingrese un precio válido.");
            return;
        }

        int hora = sp_hour_event_manage != null ? sp_hour_event_manage.getValue() : 0;
        int minutos = sp_minutes_event_manage != null ? sp_minutes_event_manage.getValue() : 0;
        LocalDateTime horaEvento = LocalDateTime.of(fecha.getYear(), fecha.getMonthValue(), fecha.getDayOfMonth(), hora, minutos);

        seleccionado.setNombre(nombre);
        seleccionado.setDescripcion(descripcion);
        seleccionado.setFecha(fecha);
        seleccionado.setHora(horaEvento);
        seleccionado.setPrecioBase(precio);
        eventoController.guardarCambios();
        refrescarTabla();
        actualizarFacturacion();
        limpiarCampos();
        mostrarAlerta(Alert.AlertType.INFORMATION, "Evento modificado", "El evento se ha actualizado correctamente.");
    }}