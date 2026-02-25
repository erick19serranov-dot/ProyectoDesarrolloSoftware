package models;

public class EntradaVIP extends Entrada {
    private double aumentoPrecioVIP = 0.4; // 40% extra

    public EntradaVIP(String idEntrada, int fila, int columna) {
        super(idEntrada, fila, columna);
    }

    @Override
    public double calcularPrecioFinal(double precioBase) {
        return precioBase * (1 + aumentoPrecioVIP);
    }

    public double getRecargoLounge() {
        return aumentoPrecioVIP;
    }
}