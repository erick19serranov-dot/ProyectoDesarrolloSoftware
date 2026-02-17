package models;

public class EntradaVIP extends Entrada {

    private double aumentoPrecioVIP = 0.4;

    public EntradaVIP(String idEntrada, String nombreCliente, int fila, int columna, double precioBase, double recargoLounge) {
        super(idEntrada, nombreCliente, fila, columna, precioBase);
        this.aumentoPrecioVIP = recargoLounge;
    }

    @Override
    public double calcularPrecioFinal(){
        return precioBase * aumentoPrecioVIP;
    }

    public double getRecargoLounge() {
        return aumentoPrecioVIP;
    }
}
