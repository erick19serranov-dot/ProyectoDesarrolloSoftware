package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import models.Evento;
import models.EventoSeleccionado;

import java.net.URL;
import java.util.ResourceBundle;

public class EventCardController implements Initializable {

    private Evento evento;
    private EventoSeleccionado listener;

    @FXML private AnchorPane pane_event_card;
    @FXML private Button btn_selectCard;
    @FXML private Label txt_name_event_card;
    @FXML private ImageView img_event_card;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        actualizarNombreEvento();
    }

    @FXML
    void setCard(ActionEvent event) {
        if (listener != null) {
            listener.eventoSeleccionado(evento);
        }
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
        actualizarNombreEvento();
    }

    private void actualizarNombreEvento() {
        if (evento != null) {
            txt_name_event_card.setText(evento.getNombre());
            if (evento.getImagen() != null) {
                img_event_card.setImage(evento.getImagen());
            }
        }
    }

    public void setOnEventoSeleccionado(EventoSeleccionado listener) {
        this.listener = listener;
    }
}