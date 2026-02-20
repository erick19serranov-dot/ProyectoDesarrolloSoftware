package models;


public class Factura {
    
    private int numeroFactura = 0;
    private double subtotal;
    private double iva = 0.13;
    private double total;

    public Factura(double subtotal, double total) {
        this.subtotal = subtotal;
        this.total = total;
    }

    public int getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(int numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    public double calcularTotal(){
        double total = this.getSubtotal()*iva;
        return total + this.getSubtotal();
        
    }
    
}
