package controllers;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminViewController implements Initializable {

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
    private ComboBox<String> cmb_category_event_manage;
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

    private ObservableList<Evento> eventosList = FXCollections.observableArrayList();
    private String imagenPath;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarSpinners();
        configurarComboBox();
        configurarDatePicker();
        configurarTabla();
        cargarEventosTabla();
        table_manage_event.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                cargarEventoEnFormulario(newVal);
            }
        });
        actualizarEstadisticas();
    }

    private void configurarSpinners() {
        sp_hour_event_manage.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        sp_minutes_event_manage.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
    }

    private void configurarComboBox() {
        cmb_category_event_manage.getItems().addAll("Concierto", "Obra de teatro");
    }

    private void configurarDatePicker() {
        date_event_manage.setValue(LocalDate.now());
    }

    private void configurarTabla() {
        tbl_col_id_manage_event.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        tbl_col_name_manage_event.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tbl_col_description_manage_event.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion() != null ? cellData.getValue().getDescripcion() : ""));
        tbl_col_category_manage_event.setCellValueFactory(cellData -> new SimpleStringProperty(""));
        tbl_col_price_manage_event.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrecioBase()).asObject());
        tbl_col_date_manage_event.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFecha()));
        tbl_col_time_manage_event.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getHora()));
    }

    private void cargarEventosTabla() {
        eventosList.setAll(RepositorioEventos.getEventos());
        table_manage_event.setItems(eventosList);
    }

    @FXML
    void agregarEventoTabla(ActionEvent event) {
        crearEvento();
    }


    @FXML
    void cancelarCampos(ActionEvent event) {
        limpiarCampos();
    }

    @FXML
    void eliminarEventoTabla(ActionEvent event) {
        eliminarEvento();
    }

    @FXML
    void importarImagen(ActionEvent event) {
        agregarImagen();
    }

    @FXML
    void modificarEventoTabla(ActionEvent event) {
        editarEvento();
    }

    @FXML
    void publicarEventoTabla(ActionEvent event) {
        publicarEvento();
    }

    @FXML
    void regresarPagPrincipal(ActionEvent event) {
        cargarBillboard();
    }

    private void publicarEvento() {
        Evento seleccionado = table_manage_event.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin Selección", "Seleccione un evento.");
            return;
        }
        RepositorioEventos.publicarEvento(seleccionado);
        cargarEventosCartelera(billboard_GP_manage);
        mostrarAlerta(Alert.AlertType.INFORMATION, "Publicado", "Evento publicado correctamente.");
    }

    private void editarEvento() {
        Evento seleccionado = table_manage_event.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin selección", "Seleccione un evento.");
            return;
        }
        String nombre = txt_name_event_manage.getText().trim();
        String descripcion = txt_description_event_manage.getText().trim();
        LocalDate fecha = date_event_manage.getValue();
        String precioStr = txt_price_event_manage.getText().trim();

        if (nombre.isEmpty() || fecha == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos requeridos", "Complete nombre y fecha.");
            return;
        }
        double precio;
        try {
            precio = Double.parseDouble(precioStr);
            if (precio < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Precio inválido", "Ingrese un número válido.");
            return;
        }

        LocalDateTime hora = LocalDateTime.of(fecha, java.time.LocalTime.of(sp_hour_event_manage.getValue(), sp_minutes_event_manage.getValue()));

        if (RepositorioEventos.existeEventoIgual(nombre, hora, seleccionado.getId())) {
            mostrarAlerta(Alert.AlertType.WARNING, "Evento duplicado", "Ya existe otro evento con el mismo nombre, fecha y hora.");
            return;
        }

        seleccionado.setNombre(nombre);
        seleccionado.setDescripcion(descripcion);
        seleccionado.setFecha(fecha);
        seleccionado.setHora(hora);
        seleccionado.setPrecioBase(precio);
        if (imagenPath != null) {
            seleccionado.setImagen(new Image("file:" + imagenPath));
            seleccionado.setImagenPath(imagenPath);
        }
        RepositorioEventos.actualizarEvento(seleccionado);
        cargarEventosTabla();
        limpiarCampos();
        mostrarAlerta(Alert.AlertType.INFORMATION, "Evento modificado", "Cambios guardados.");
    }

    private void eliminarEvento() {
        Evento seleccionado = table_manage_event.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin selección", "Seleccione un evento.");
            return;
        }
        Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "¿Eliminar \"" + seleccionado.getNombre() + "\"?", ButtonType.OK, ButtonType.CANCEL).showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            RepositorioEventos.eliminarEvento(seleccionado);
            cargarEventosTabla();
            limpiarCampos();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Eliminado", "Evento eliminado.");
        }
    }

    private void crearEvento() {
        String nombre = txt_name_event_manage.getText().trim();
        String descripcion = txt_description_event_manage.getText().trim();
        LocalDate fecha = date_event_manage.getValue();
        String precioStr = txt_price_event_manage.getText().trim();

        if (nombre.isEmpty() || fecha == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos requeridos", "Complete nombre y fecha.");
            return;
        }
        double precio;
        try {
            precio = Double.parseDouble(precioStr);
            if (precio < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Precio inválido", "Ingrese un número válido.");
            return;
        }

        LocalDateTime hora = LocalDateTime.of(fecha, java.time.LocalTime.of(sp_hour_event_manage.getValue(), sp_minutes_event_manage.getValue()));

        if (RepositorioEventos.existeEventoIgual(nombre, hora, null)) {
            mostrarAlerta(Alert.AlertType.WARNING, "Evento duplicado", "Ya existe un evento con el mismo nombre, fecha y hora.");
            return;
        }

        String id = "E" + System.currentTimeMillis();
        Evento nuevo = new Evento(id, nombre, descripcion, fecha, hora, precio, imagenPath);
        if (imagenPath != null) {
            nuevo.setImagen(new Image("file:" + imagenPath));
        }
        RepositorioEventos.agregarEvento(nuevo);
        cargarEventosTabla();
        limpiarCampos();
        mostrarAlerta(Alert.AlertType.INFORMATION, "Evento agregado", "Evento creado.");
    }

    private void cargarEventoEnFormulario(Evento e) {
        txt_name_event_manage.setText(e.getNombre());
        txt_description_event_manage.setText(e.getDescripcion());
        date_event_manage.setValue(e.getFecha());
        txt_price_event_manage.setText(String.valueOf(e.getPrecioBase()));
        sp_hour_event_manage.getValueFactory().setValue(e.getHora().getHour());
        sp_minutes_event_manage.getValueFactory().setValue(e.getHora().getMinute());
        image_event_manage.setImage(e.getImagen());
        imagenPath = e.getImagenPath();
    }

    private void agregarImagen() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.jpg", "*.png", "*.jpeg"));
        File file = fc.showOpenDialog(null);
        if (file != null) {
            image_event_manage.setImage(new Image(file.toURI().toString()));
            imagenPath = file.getAbsolutePath();
        }
    }

    private void cargarBillboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/BillboardView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btn_goback_event_manage.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Cartelera");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarEventosCartelera(GridPane grid) {
        grid.getChildren().clear();
        int col = 0, row = 0;
        for (Evento e : RepositorioEventos.getEventosPublicados()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EventCardView.fxml"));
                AnchorPane card = loader.load();
                ((EventCardController) loader.getController()).setEvento(e);
                grid.add(card, col, row);
                col++;
                if (col == 3) {
                    col = 0;
                    row++;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void setNombre(String nombre) {
        text_username_admin.setText(nombre);
    }

    private void limpiarCampos() {
        txt_name_event_manage.clear();
        txt_description_event_manage.clear();
        txt_price_event_manage.clear();
        date_event_manage.setValue(LocalDate.now());
        sp_hour_event_manage.getValueFactory().setValue(0);
        sp_minutes_event_manage.getValueFactory().setValue(0);
        image_event_manage.setImage(null);
        imagenPath = null;
        table_manage_event.getSelectionModel().clearSelection();
    }

    private void actualizarEstadisticas() {
        int total = RepositorioEventos.getEventos().size();
        int publicados = RepositorioEventos.getEventosPublicados().size();
        double ingresos = 0;
        int vendidos = 0, vip = 0, est = 0, gen = 0;
        for (Evento e : RepositorioEventos.getEventos()) {
            ingresos += e.calcularRecaudacion();
            for (Entrada ent : e.getEntradasVendidas()) {
                vendidos++;
                if (ent instanceof EntradaVIP) {
                    vip++;
                } else if (ent instanceof EntradaEstudiante) {
                    est++;
                } else {
                    gen++;
                }
            }
        }
        number_total_event_billboard.setText(String.valueOf(total));
        number_event_active_billboard.setText(String.valueOf(publicados));
        number_total_income_billboard.setText(String.format("%.2f", ingresos));
        number_tickets_sold_billboard.setText(String.valueOf(vendidos));
        number_tickets_vip_billboard.setText(String.valueOf(vip));
        number_tickets_student_billboard.setText(String.valueOf(est));
        number_tickets_regular_billboard.setText(String.valueOf(gen));
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
