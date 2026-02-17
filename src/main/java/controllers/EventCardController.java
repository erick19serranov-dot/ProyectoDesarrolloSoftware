package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class EventCardController {
    
    @FXML
    private Button btn_selectCard;
    @FXML
    private Label txt_name_event_card;

    @FXML
    void setCard(ActionEvent event) {
        
    }

    public void actualizarNombreEvento(String nombreEvento) {
        txt_name_event_card.setText(nombreEvento);
    }
        
}