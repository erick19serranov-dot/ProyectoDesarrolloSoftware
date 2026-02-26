package models;

public class Factura {
    private static int contador = 1;
    private int numeroFactura;
    private double subtotal;
    private double iva = 0.13;
    private double total;

    public Factura(double subtotal) {
        this.numeroFactura = contador++;
        this.subtotal = subtotal;
        this.total = calcularTotal();
    }

    public int getNumeroFactura() { return numeroFactura; }

    public double getSubtotal() { return subtotal; }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
        this.total = calcularTotal();
    }

    /**
     * Devuelve la tasa de IVA (por ejemplo 0.13 = 13%).
     */
    public double getIva() { return iva; }

    /**
     * Devuelve únicamente el monto correspondiente al IVA.
     */
    public double getMontoIva() {
        return subtotal * iva;
    }

    /**
     * Devuelve el subtotal incluyendo el IVA (subtotal + IVA).
     */
    public double getSubtotalConIva() {
        return subtotal + getMontoIva();
    }

    public double getTotal() { return total; }

    private double calcularTotal() {
        return subtotal * (1 + iva);
    }

    public String generarTextoFactura(String nombreCliente, String detalles) {
        StringBuilder sb = new StringBuilder();
        sb.append("FACTURA #").append(numeroFactura).append("\n");
        sb.append("Cliente: ").append(nombreCliente).append("\n");
        sb.append(detalles).append("\n");
        sb.append("Subtotal: ").append(String.format("%.2f", subtotal)).append("\n");
        sb.append("IVA (13%): ").append(String.format("%.2f", getMontoIva())).append("\n");
        sb.append("TOTAL: ").append(String.format("%.2f", total)).append("\n");
        return sb.toString();
    }
}