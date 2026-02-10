package models;

public class EntradaVIP extends Entrada {

    private double recargoLounge;

    public EntradaVIP(String idEntrada, String nombreCliente, int fila, int columna, double precioBase, double recargoLounge) {
        super(idEntrada, nombreCliente, fila, columna, precioBase);
        this.recargoLounge = recargoLounge;
    }

    @Override
    public double calcularPrecioFinal(){
        return precioBase + recargoLounge;
    }

    @Override
    public String generarDetalle(){
        return "==ENTRADA VIP TICKET==\n" +
                "ID: " + idEntrada + "\n" +
                "Cliente: " + nombreCliente + "\n" +
                "Asiento: " + getAsientoTexto() + "\n" +
                "Precio Base: " + precioBase + "\n" +
                "Recargo Lounge: " + recargoLounge + "\n" +
                "Precio Final: " + calcularPrecioFinal() + "\n" +
                "Incluye acceso a lounge VIP\n";
    }

    public double getRecargoLounge() {
        return recargoLounge;
    }
}
