package models;

public class EntradaGeneral extends Entrada {

    public EntradaGeneral(String idEntrada, String nombreCliente, int fila, int columna, double precioBase){
        super(idEntrada, nombreCliente, fila, columna, precioBase);
    }

    @Override
    public double calcularPrecioFinal(){

        return precioBase;
    }


}