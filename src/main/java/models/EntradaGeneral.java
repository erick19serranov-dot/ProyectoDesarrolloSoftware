package models;

public class EntradaGeneral extends Entrada {

    public EntradaGeneral(String idEntrada, int fila, int columna) {
        super(idEntrada, fila, columna);
    }

    @Override
    public double calcularPrecioFinal(double precioBase) {
        return precioBase;
    }
}