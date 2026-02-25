package models;

import javafx.scene.image.Image;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Evento {
    private String id;
    private String nombre;
    private String descripcion;
    private LocalDate fecha;
    private LocalDateTime hora;
    private double precioBase;
    private boolean[][] asientos; // 10 filas x 14 columnas
    private List<Entrada> entradasVendidas;
    private Image imagen;
    private String imagenPath;

    public Evento(String id, String nombre, String descripcion, LocalDate fecha, LocalDateTime hora, double precioBase, String imagenPath) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.hora = hora;
        this.precioBase = precioBase;
        this.asientos = new boolean[10][14];
        this.entradasVendidas = new ArrayList<>();
        this.imagenPath = imagenPath;
        if (imagenPath != null && !imagenPath.isEmpty()) {
            this.imagen = new Image("file:" + imagenPath);
        }
    }

    // Getters y setters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public LocalDateTime getHora() { return hora; }
    public void setHora(LocalDateTime hora) { this.hora = hora; }
    public double getPrecioBase() { return precioBase; }
    public void setPrecioBase(double precioBase) { this.precioBase = precioBase; }
    public boolean[][] getAsientos() { return asientos; }
    public void setAsientos(boolean[][] asientos) { this.asientos = asientos; }
    public List<Entrada> getEntradasVendidas() { return entradasVendidas; }
    public Image getImagen() { return imagen; }
    public void setImagen(Image imagen) { this.imagen = imagen; }
    public String getImagenPath() { return imagenPath; }
    public void setImagenPath(String imagenPath) { this.imagenPath = imagenPath; }

    public boolean venderEntrada(Entrada entrada) {
        int fila = entrada.getFila();
        int columna = entrada.getColumna();
        if (fila < 0 || fila >= 10 || columna < 0 || columna >= 14) return false;
        if (asientos[fila][columna]) return false;
        asientos[fila][columna] = true;
        entradasVendidas.add(entrada);
        return true;
    }

    public double calcularRecaudacion() {
        double total = 0;
        for (Entrada e : entradasVendidas) {
            total += e.calcularPrecioFinal(precioBase);
        }
        return total;
    }

    public void reiniciarSala() {
        for (int i = 0; i < asientos.length; i++) {
            for (int j = 0; j < asientos[i].length; j++) {
                asientos[i][j] = false;
            }
        }
        entradasVendidas.clear();
    }

    public boolean estaOcupado(int fila, int columna) {
        return asientos[fila][columna];
    }
}