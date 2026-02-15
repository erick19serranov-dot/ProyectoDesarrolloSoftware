package controllers;

import models.Evento;

import javax.swing.JOptionPane;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventoC {

    private List<Evento> eventos;

    public EventoC() {
        try {
            eventos = PersistenciaEventosC.cargarEventos();
        } catch (IOException | NumberFormatException e) {
            eventos = new ArrayList<>();
        }
    }

    public void crearEvento(String idEvento, String nombre, String fecha,
                            double precioBase, int filas, int columnas) {

        Evento nuevo = new Evento(idEvento, nombre, fecha, precioBase, filas, columnas);
        eventos.add(nuevo);
    }

    public boolean editarEvento(String idEvento, String nuevoNombre,
                                String nuevaFecha, double nuevoPrecioBase) {

        Evento e = buscarEventoPorId(idEvento);
        if (e == null) {
            return false;
        }

        e.setNombre(nuevoNombre);
        e.setFecha(nuevaFecha);
        e.setPrecioBase(nuevoPrecioBase);
        return true;
    }

    public boolean eliminarEvento(String idEvento) {
        Evento e = buscarEventoPorId(idEvento);
        if (e != null) {
            eventos.remove(e);
            return true;
        }
        return false;
    }

    public Evento buscarEventoPorId(String idEvento) {
        for (Evento e : eventos) {
            if (e.getIdEvento().equals(idEvento)) {
                return e;
            }
        }
        return null;
    }

    public double calcularRecaudacionEvento(String idEvento) {
        Evento e = buscarEventoPorId(idEvento);
        return (e != null) ? e.calcularRecaudacion() : 0;
    }

    public boolean reiniciarSalaEvento(String idEvento) {
        Evento e = buscarEventoPorId(idEvento);
        if (e != null) {
            e.reiniciarSala();
            return true;
        }
        return false;
    }

    public void guardarCambios() {
        try {
            PersistenciaEventosC.guardarEventos(eventos);
        } catch (IOException e) {
        }
    }

    public List<Evento> getEventos() {
        return eventos;
    }
}