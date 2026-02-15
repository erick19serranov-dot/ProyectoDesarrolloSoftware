package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import models.Evento;
import java.io.IOException;
import java.util.Optional;

public class AdminViewC {

    private Evento eventoCtrl = new Evento();

    @FXML private TextField txtIdEvento, txtNombre, txtFecha, txtPrecioBase, txtFilas, txtColumnas;
    @FXML private TextArea txtAreaEventos;
    @FXML private TableView<Evento> tablaEventos;
    @FXML private Tab tabAgregar, tabGestionar, tabFacturacion;
    @FXML private GridPane gridAsientosAdmin;


    @FXML private TableColumn<Evento, String> colId;
    @FXML private TableColumn<Evento, String> colNombre;
    @FXML private TableColumn<Evento, String> colFecha;
    @FXML private TableColumn<Evento, Double> colPrecioBase;

    @FXML
    public void initialize() {
        cargarEventos();
        configurarTablaEventos();


        validarEntradaNumerica(txtPrecioBase);
        validarEntradaNumerica(txtFilas);
        validarEntradaNumerica(txtColumnas);
    }

    @FXML
    private void crearEvento() {
        try {
            String id = txtIdEvento.getText();
            String nombre = txtNombre.getText();
            String fecha = txtFecha.getText();


            if (id.isEmpty() || nombre.isEmpty() || fecha.isEmpty()) {
                mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
                return;
            }

            double precio = Double.parseDouble(txtPrecioBase.getText());
            int filas = Integer.parseInt(txtFilas.getText());
            int columnas = Integer.parseInt(txtColumnas.getText());


            if (filas <= 0 || columnas <= 0) {
                mostrarAlerta("Error", "Las filas y columnas deben ser mayores a 0", Alert.AlertType.ERROR);
                return;
            }

            eventoCtrl.crearEvento(id, nombre, fecha, precio, filas, columnas);
            limpiarCampos();
            cargarEventos();
            mostrarAlerta("Éxito", "Evento creado correctamente", Alert.AlertType.INFORMATION);
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Formato numérico incorrecto", Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al crear evento: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void editarEventoSeleccionado() {
        Evento seleccionado = tablaEventos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            mostrarDialogoEdicion(seleccionado);
        } else {
            mostrarAlerta("Advertencia", "Seleccione un evento para editar", Alert.AlertType.WARNING);
        }
    }

    private void mostrarDialogoEdicion(Evento evento) {

        Dialog<Evento> dialog = new Dialog<>();
        dialog.setTitle("Editar Evento");
        dialog.setHeaderText("Editar información del evento: " + evento.getNombre());


        ButtonType botonGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        ButtonType botonCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(botonGuardar, botonCancelar);


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

        TextField txtNuevoNombre = new TextField(evento.getNombre());
        txtNuevoNombre.setPromptText("Nombre del evento");

        TextField txtNuevaFecha = new TextField(evento.getFecha());
        txtNuevaFecha.setPromptText("Fecha (DD/MM/YYYY)");

        TextField txtNuevoPrecio = new TextField(String.valueOf(evento.getPrecioBase()));
        txtNuevoPrecio.setPromptText("Precio base");

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(txtNuevoNombre, 1, 0);
        grid.add(new Label("Fecha:"), 0, 1);
        grid.add(txtNuevaFecha, 1, 1);
        grid.add(new Label("Precio Base:"), 0, 2);
        grid.add(txtNuevoPrecio, 1, 2);

        dialog.getDialogPane().setContent(grid);


        Node botonGuardarNode = dialog.getDialogPane().lookupButton(botonGuardar);
        botonGuardarNode.setDisable(false);


        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == botonGuardar) {
                try {
                    String nuevoNombre = txtNuevoNombre.getText();
                    String nuevaFecha = txtNuevaFecha.getText();
                    double nuevoPrecio = Double.parseDouble(txtNuevoPrecio.getText());

                    if (nuevoNombre.isEmpty() || nuevaFecha.isEmpty()) {
                        mostrarAlerta("Error", "Nombre y fecha son obligatorios", Alert.AlertType.ERROR);
                        return null;
                    }

                    boolean editado = eventoCtrl.editarEvento(
                            evento.getIdEvento(),
                            nuevoNombre,
                            nuevaFecha,
                            nuevoPrecio
                    );

                    if (editado) {
                        cargarEventos();
                        return evento;
                    }
                } catch (NumberFormatException e) {
                    mostrarAlerta("Error", "Precio debe ser un número válido", Alert.AlertType.ERROR);
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(resultado -> {
            mostrarAlerta("Éxito", "Evento editado correctamente", Alert.AlertType.INFORMATION);
        });
    }

    @FXML
    private void eliminarEventoSeleccionado() {
        Evento seleccionado = tablaEventos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            // Confirmar eliminación
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("¿Está seguro de eliminar el evento?");
            confirmacion.setContentText("Evento: " + seleccionado.getNombre());

            Optional<ButtonType> resultado = confirmacion.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                eventoCtrl.eliminarEvento(seleccionado.getIdEvento());
                cargarEventos();
                mostrarAlerta("Éxito", "Evento eliminado correctamente", Alert.AlertType.INFORMATION);
            }
        } else {
            mostrarAlerta("Advertencia", "Seleccione un evento para eliminar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void mostrarRecaudacion() {
        Evento seleccionado = tablaEventos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            double recaudacion = eventoCtrl.calcularRecaudacionEvento(seleccionado.getIdEvento());
            mostrarAlerta("Recaudación",
                    "Recaudación total para '" + seleccionado.getNombre() + "': $" + recaudacion,
                    Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Advertencia", "Seleccione un evento para ver la recaudación", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void reiniciarSalaEvento() {
        Evento seleccionado = tablaEventos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            // Confirmar reinicio
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar reinicio");
            confirmacion.setHeaderText("¿Está seguro de reiniciar la sala?");
            confirmacion.setContentText("Esto liberará todos los asientos para: " + seleccionado.getNombre());

            Optional<ButtonType> resultado = confirmacion.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                eventoCtrl.reiniciarSalaEvento(seleccionado.getIdEvento());
                mostrarAlerta("Éxito", "Sala reiniciada correctamente", Alert.AlertType.INFORMATION);
            }
        } else {
            mostrarAlerta("Advertencia", "Seleccione un evento para reiniciar la sala", Alert.AlertType.WARNING);
        }
    }

    private void cargarEventos() {
        tablaEventos.getItems().clear();
        tablaEventos.getItems().addAll(eventoCtrl.getEventos());
    }

    private void configurarTablaEventos() {

        if (colId == null) {
            colId = new TableColumn<>("ID");
            colId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdEvento()));

            colNombre = new TableColumn<>("Nombre");
            colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));

            colFecha = new TableColumn<>("Fecha");
            colFecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFecha()));

            colPrecioBase = new TableColumn<>("Precio Base");
            colPrecioBase.setCellValueFactory(cellData -> {
                Double precio = cellData.getValue().getPrecioBase();
                return new javafx.beans.property.SimpleObjectProperty<>(precio);
            });


            tablaEventos.getColumns().clear();
            tablaEventos.getColumns().addAll(colId, colNombre, colFecha, colPrecioBase);
        }


        TableColumn<Evento, Integer> colCapacidad = new TableColumn<>("Capacidad");
        colCapacidad.setCellValueFactory(cellData -> {
            boolean[][] asientos = cellData.getValue().getAsientos();
            int capacidad = asientos.length * asientos[0].length;
            return new javafx.beans.property.SimpleObjectProperty<>(capacidad);
        });

        tablaEventos.getColumns().add(colCapacidad);
    }

    private void limpiarCampos() {
        txtIdEvento.clear();
        txtNombre.clear();
        txtFecha.clear();
        txtPrecioBase.clear();
        txtFilas.clear();
        txtColumnas.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void validarEntradaNumerica(TextField campo) {
        campo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) {
                campo.setText(oldValue);
            }
        });
    }


    @FXML
    private void exportarEventos() {
        try {
            PersistenciaEventosC.guardarEventos(eventoCtrl.getEventos());
            mostrarAlerta("Éxito", "Eventos exportados correctamente", Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            mostrarAlerta("Error", "Error al exportar eventos: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}