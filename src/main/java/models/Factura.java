package models;

public class Factura {
    private static int contador = 1;
    private int numeroFactura;
    private double subtotal;
    private double total;

    public Factura(double subtotal) {
        this.numeroFactura = contador++;
        this.subtotal = subtotal;
        this.total = subtotal; // Sin IVA
    }

    public int getNumeroFactura() { return numeroFactura; }
    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; this.total = subtotal; }
    public double getTotal() { return total; }

    public String generarTextoFactura(String nombreCliente, String detalles) {
        StringBuilder sb = new StringBuilder();
        sb.append("FACTURA #").append(numeroFactura).append("\n");
        sb.append("Cliente: ").append(nombreCliente).append("\n");
        sb.append(detalles).append("\n");
        sb.append("TOTAL: ").append(String.format("%.2f", total)).append("\n");
        return sb.toString();
    }
}