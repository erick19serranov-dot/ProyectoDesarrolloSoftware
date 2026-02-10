package models;

import java.util.ArrayList;
import java.util.List;

public class Evento {
    private String idEvento;
    private String nombre;
    private String fecha;
    private double precioBase;

    private boolean [][] asientos;
    private List<Entrada> entradasVendidas;

    public Evento(String idEvento, String nombre,String fecha, double precioBase, int filas, int columnas){

        this.idEvento = idEvento;
        this.nombre = nombre;
        this.fecha = fecha;
        this.precioBase = precioBase;

        this.asientos = new boolean[filas][columnas];
        this.entradasVendidas = new ArrayList<>();
        }


        public boolean venderEntrada(Entrada entrada){
        int fila = entrada.getFila();
        int columna = entrada.getColumna();

        if (asientos[fila][columna]) {
            return false;
        }

        asientos[fila][columna] = true;
        entradasVendidas.add(entrada);
        return true;
        }

        public double calcularRecaudacion(){
        double total = 0;
        for (Entrada e : entradasVendidas){
            total += e.calcularPrecioFinal();
        }
        return total;
        }

    public void reiniciarSala() {
        for (int i = 0; i < asientos.length; i++){
            for ( int j = 0; j < asientos[i].length; j++){
                asientos[i][j] = false;
            }
        }
         entradasVendidas.clear();;
    }

    public boolean estaOcupado(int fila, int columna){
        return asientos[fila][columna];
    }

    public String getIdEvento() {
        return idEvento;
    }

    public String getNombre(){
        return nombre;
    }

    public String getFecha(){
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

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }
}
