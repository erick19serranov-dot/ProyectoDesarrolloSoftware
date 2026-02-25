package models;

public abstract class Entrada implements IImprimible {
    protected String idEntrada;
    protected int fila;
    protected int columna;

    public Entrada(String idEntrada, int fila, int columna) {
        this.idEntrada = idEntrada;
        this.fila = fila;
        this.columna = columna;
    }

    public String getIdEntrada() { 
        return idEntrada;
    }
    public void setIdEntrada(String idEntrada) { this.idEntrada = idEntrada; }
    public int getFila() { return fila; }
    public void setFila(int fila) { this.fila = fila; }
    public int getColumna() { return columna; }
    public void setColumna(int columna) { this.columna = columna; }

    public String getAsientoTexto() {
        char letra = (char) ('A' + fila);
        return letra + String.valueOf(columna + 1);
    }

    @Override
    public abstract double calcularPrecioFinal(double precioBase);
}