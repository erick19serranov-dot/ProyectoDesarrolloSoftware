package models;

public class EntradaVIP extends Entrada {

    private double aumentoPrecioVIP = 0.4;

    public EntradaVIP(String idEntrada, int fila, int columna) {
        super(idEntrada, fila, columna);
    }

    

    @Override
    public double calcularPrecioFinal(double precioBase){
        return precioBase * aumentoPrecioVIP;
    }

    public double getRecargoLounge() {
        return aumentoPrecioVIP;
    }
}
