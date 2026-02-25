package controllers;

import models.Evento;

import java.io.IOException;
import java.time.LocalDate;
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
        try {
            LocalDate fechaEvento = LocalDate.parse(fecha);
            Evento nuevo = new Evento(idEvento, nombre, "", fechaEvento, null, precioBase);
            nuevo.setAsientos(new boolean[filas][columnas]);
            eventos.add(nuevo);
        } catch (Exception e) {
            System.err.println("Error al crear el evento: " + e.getMessage());
        }
    }

    public boolean editarEvento(String idEvento, String nuevoNombre,
                                String nuevaFecha, double nuevoPrecioBase) {

        Evento e = buscarEventoPorId(idEvento);
        if (e == null) {
            return false;
        }

        try {
            LocalDate fechaEvento = LocalDate.parse(nuevaFecha);
            e.setNombre(nuevoNombre);
            e.setFecha(fechaEvento);
            e.setPrecioBase(nuevoPrecioBase);
            return true;
        } catch (Exception ex) {
            System.err.println("Error al editar el evento: " + ex.getMessage());
            return false;
        }
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
            if (e.getId().equals(idEvento)) {
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