package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RepositorioEventos {

    private static final ObservableList<Evento> eventosPublicados = FXCollections.observableArrayList();

    public static ObservableList<Evento> getEventosPublicados() {
        return eventosPublicados;
    }

    public static void publicarEvento(Evento evento) {
        if (!eventosPublicados.contains(evento)) {
            eventosPublicados.add(evento);
        }
    }
}
