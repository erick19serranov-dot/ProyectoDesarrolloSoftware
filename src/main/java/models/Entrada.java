package models;

public abstract class Entrada implements IImprimible {

    protected String idEntrada;
    protected String nombreCliente;
    protected int fila;
    protected int columna;
    protected double precioBase;

    public Entrada(String idEntrada, String nombreCliente, int fila, int columna, double precioBase){
        this.idEntrada = idEntrada;
        this.nombreCliente = nombreCliente;
        this.fila = fila;
        this.columna = columna;
        this.precioBase = precioBase;
    }

    public abstract double calcularPrecioFinal();

    public String getIdEntrada() {
        return idEntrada;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public double getPrecioBase() {
        return precioBase;
    }
    public String getAsientoTexto(){
        return "fila: " + fila + "columna: " + columna;
    }
}
