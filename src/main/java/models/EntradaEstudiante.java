package models;

public class EntradaEstudiante extends Entrada {

    private double porcentajeDescuento = 0.2;

    public EntradaEstudiante(String idEntrada, int fila, int columna) {
        super(idEntrada, fila, columna);
    }

    @Override
    public double calcularPrecioFinal(double precioBase) {
        double descuento = precioBase * porcentajeDescuento;
        return Math.max(precioBase - descuento, 0);
    }
}
