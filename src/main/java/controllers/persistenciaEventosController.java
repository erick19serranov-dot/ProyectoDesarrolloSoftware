package controllers;

import models.Evento;

import java.io.*;
import java.util.List;

public class persistenciaEventosController {

    private static final String ARCHIVO_EVENTOS = "eventos.txt";

    public static void guardarEventos(List<Evento> eventos) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_EVENTOS));

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
        bw.close();
    }

    private static String serializarMatriz(boolean[][] matriz) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                sb.append(matriz[i][j] ? "1" : "0");
            }
            if (i < matriz.length - 1) sb.append("|");
        }
        return sb.toString();
    }
}
