package controllers;

import models.Evento;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaEventosC {

    private static final String ARCHIVO_EVENTOS = "eventos.txt";

    public static void guardarEventos(List<Evento> eventos) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_EVENTOS))) {
            for (Evento e : eventos) {
                bw.write(e.getIdEvento() + ";" +
                        e.getNombre() + ";" +
                        e.getFecha() + ";" +
                        e.getPrecioBase() + ";" +
                        e.getAsientos().length + ";" +
                        e.getAsientos()[0].length + ";" +
                        serializarMatriz(e.getAsientos()));
                bw.newLine();
            }
        }
    }

    private static String serializarMatriz(boolean[][] matriz) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                sb.append(matriz[i][j] ? "1" : "0");
            }
            if (i < matriz.length - 1) {
                sb.append("|");
            }
        }
        return sb.toString();
    }

    private static boolean[][] deserializarMatriz(String texto, int filas, int columnas) {
        boolean[][] matriz = new boolean[filas][columnas];
        String[] filasTexto = texto.split("\\|");

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matriz[i][j] = filasTexto[i].charAt(j) == '1';
            }
        }
        return matriz;
    }

    public static List<Evento> cargarEventos() throws IOException {
        List<Evento> eventos = new ArrayList<>();
        File archivo = new File(ARCHIVO_EVENTOS);

        if (!archivo.exists()) {
            return eventos;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");

                String id = datos[0];
                String nombre = datos[1];
                String fecha = datos[2];
                double precioBase = Double.parseDouble(datos[3]);
                int filas = Integer.parseInt(datos[4]);
                int columnas = Integer.parseInt(datos[5]);
                boolean[][] asientos = deserializarMatriz(datos[6], filas, columnas);

                Evento evento = new Evento(id, nombre, fecha, precioBase, filas, columnas);
                evento.setAsientos(asientos);
                eventos.add(evento);
            }
        }
        return eventos;
    }
}