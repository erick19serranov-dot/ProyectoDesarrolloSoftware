package models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;

public class Evento {

    private String id;
    private String nombre;
    private String descripcion;
    private LocalDate fecha;
    private LocalDateTime hora;
    private double precioBase;

    private boolean[][] asientos;
    private List<Entrada> entradasVendidas;

    private List<Evento> listaEventos;

    private Image image;

    public Evento(String id, String nombre, String descripcion, LocalDate fecha, LocalDateTime hora, double precioBase, Image image) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hora = hora;
        this.precioBase = precioBase;
        this.asientos = new boolean[15][10];
        this.listaEventos = new ArrayList<>();
        this.image = image;
    }

    public List<Evento> getListaEventos() {
        return listaEventos;
    }

    public void setListaEventos(List<Evento> listaEventos) {
        this.listaEventos = listaEventos;
    }

    public boolean venderEntrada(Entrada entrada) {
        int fila = entrada.getFila();
        int columna = entrada.getColumna();

        if (asientos[fila][columna]) {
            return false;
        }

        asientos[fila][columna] = true;
        entradasVendidas.add(entrada);
        return true;
    }

    /*
        public double calcularRecaudacion(){
        double total = 0;
        for (Entrada e : entradasVendidas){
            total += e.calcularPrecioFinal();
        }
        return total;
        }
     */
    public void reiniciarSala() {
        for (int i = 0; i < asientos.length; i++) {
            for (int j = 0; j < asientos[i].length; j++) {
                asientos[i][j] = false;
            }
        }
        entradasVendidas.clear();;

    }

    public void setAsientos(boolean[][] asientos) {
        this.asientos = asientos;
    }

    public boolean estaOcupado(int fila, int columna) {
        return asientos[fila][columna];
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getHora() {
        return hora;
    }

    public void setHora(LocalDateTime hora) {
        this.hora = hora;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public boolean[][] getAsientos() {
        return asientos;
    }

    public List<Entrada> getEntradasVendidas() {
        return entradasVendidas;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public void setImagen(Image imagen) {
        this.image = image;
    }

    public Object getImagen() {
return image;
    }

}
