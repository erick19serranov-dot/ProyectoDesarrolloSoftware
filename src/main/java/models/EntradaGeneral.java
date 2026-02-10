package models;

public class EntradaGeneral extends Entrada {

    public EntradaGeneral(String idEntrada, String nombreCliente, int fila, int columna, double precioBase){
        super(idEntrada, nombreCliente, fila, columna, precioBase);
    }

    @Override
    public double calcularPrecioFinal(){
        return precioBase;
    }

    @Override
    public String generarDetalle(){
        return  "==ENTRADA GENBERAL TICKET==\n" +
                "ID:" + idEntrada + "\n" +
                "Cliente: " + nombreCliente + "\n" +
                "Asiento: " + getAsientoTexto() + "\n" +
                "Precio Base: " + precioBase + "\n" +
                "Precio Final: " + calcularPrecioFinal() + "\n";
    }
}