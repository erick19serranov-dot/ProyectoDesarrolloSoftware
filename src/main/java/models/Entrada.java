package models;

public abstract class Entrada implements IImprimible {

    protected String idEntrada;
    protected int fila = 10;
    protected int columna = 15;

    public Entrada(String idEntrada, int fila, int columna) {
        this.idEntrada = idEntrada;
        this.fila = fila;
        this.columna = columna;
    }

    public String getIdEntrada() {
        return idEntrada;
    }

    public void setIdEntrada(String idEntrada) {
        this.idEntrada = idEntrada;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public String getAsientoTexto(){
    return "Fila: " + (fila + 1) + ", Columna: " + (columna + 1);
}

}