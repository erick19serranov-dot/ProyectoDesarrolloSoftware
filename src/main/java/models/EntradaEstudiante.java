package models;

public class EntradaEstudiante extends Entrada {

    private double porcentajeDescuento;

    public EntradaEstudiante(String idEntrada, String nombreCliente, int fila, int columna, double precioBase, double porcentajeDescuento) {

        super(idEntrada, nombreCliente, fila, columna, precioBase);
        this.porcentajeDescuento = porcentajeDescuento;
    }

    @Override
    public double calcularPrecioFinal(){
        double descuento = precioBase * porcentajeDescuento;
        return Math.max(precioBase - descuento, 0);

    }


    @Override
    public String generarDetalle() {
        return "==ENTRADA ESTUDIANTE TICKET==\n" +
                "ID: " + idEntrada + "\n" +
                "Cliente: " + nombreCliente + "\n" +
                "Asiento: " + getAsientoTexto() + "\n" +
                "Precio Base: " + precioBase + "\n" +
                "Descuento: " + (porcentajeDescuento * 100) + "\n" +
                "Precio Final: " + calcularPrecioFinal() + "\n";
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }
}
