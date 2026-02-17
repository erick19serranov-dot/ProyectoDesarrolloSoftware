package controllers;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
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
import models.Evento;

public class AdminViewController {

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
    private Spinner<?> sp_hour_event_manage;
    @FXML
    private Spinner<?> sp_minutes_event_manage;
    @FXML
    private TableView<?> table_manage_event;
    @FXML
    private TableColumn<Evento, String> tbl_col_category_manage_event;
    @FXML
    private TableColumn<Evento, LocalDate> tbl_col_date_manage_event;
    @FXML
    private TableColumn<Evento, String> tbl_col_description_manage_event;
    @FXML
    private TableColumn<Evento, Integer> tbl_col_id_manage_event;
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

    @FXML
    void agregarEventoTabla(ActionEvent event) {

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
        image_event_manage.setImage(null);
    }

}
