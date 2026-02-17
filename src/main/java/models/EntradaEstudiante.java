package models;

public class EntradaEstudiante extends Entrada {

    private double porcentajeDescuento = 0.2;

    public EntradaEstudiante(String idEntrada, String nombreCliente, int fila, int columna, double precioBase, double porcentajeDescuento) {
        super(idEntrada, nombreCliente, fila, columna, precioBase);
        this.porcentajeDescuento = porcentajeDescuento;
    }

    @Override
    public double calcularPrecioFinal(){
        double descuento = precioBase * porcentajeDescuento;
        return Math.max(precioBase - descuento, 0);
    }
}
