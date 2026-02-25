package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RepositorioEventos {
    private static final ObservableList<Evento> eventos = FXCollections.observableArrayList();
    private static final ObservableList<Evento> eventosPublicados = FXCollections.observableArrayList();
    private static final String EVENTOS_DIR = "eventos";
    private static final String PUBLICADOS_FILE = "publicados.txt";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    static {
        cargarDesdeArchivo();
        cargarPublicados();
    }

    public static ObservableList<Evento> getEventos() { return eventos; }
    public static ObservableList<Evento> getEventosPublicados() { return eventosPublicados; }

    public static void agregarEvento(Evento evento) {
        eventos.add(evento);
        guardarEvento(evento);
    }

    public static void eliminarEvento(Evento evento) {
        eventos.remove(evento);
        eventosPublicados.remove(evento);
        File file = new File(EVENTOS_DIR, evento.getId() + ".txt");
        if (file.exists()) file.delete();
        guardarPublicados();
    }

    public static void actualizarEvento(Evento evento) {
        guardarEvento(evento);
    }

    public static void publicarEvento(Evento evento) {
        if (!eventosPublicados.contains(evento)) {
            eventosPublicados.add(evento);
            guardarPublicados();
        }
        actualizarEvento(evento);
    }

    public static Evento buscarEventoPorId(String id) {
        for (Evento e : eventos) {
            if (e.getId().equals(id)) return e;
        }
        return null;
    }

    private static void guardarEvento(Evento evento) {
        File dir = new File(EVENTOS_DIR);
        if (!dir.exists()) dir.mkdirs();
        File file = new File(dir, evento.getId() + ".txt");
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            pw.println(evento.getId());
            pw.println(evento.getNombre());
            pw.println(evento.getDescripcion() != null ? evento.getDescripcion() : "");
            pw.println(evento.getFecha().format(DATE_FORMAT));
            pw.println(evento.getHora().format(TIME_FORMAT));
            pw.println(evento.getPrecioBase());
            pw.println(evento.getImagenPath() != null ? evento.getImagenPath() : "");
            boolean[][] asientos = evento.getAsientos();
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 14; j++) {
                    pw.print(asientos[i][j] ? "1" : "0");
                }
                pw.println();
            }
            List<Entrada> entradas = evento.getEntradasVendidas();
            pw.println(entradas.size());
            for (Entrada e : entradas) {
                String tipo = (e instanceof EntradaVIP) ? "VIP" : (e instanceof EntradaEstudiante) ? "EST" : "GEN";
                pw.println(tipo + "," + e.getFila() + "," + e.getColumna());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void cargarDesdeArchivo() {
        eventos.clear();
        File dir = new File(EVENTOS_DIR);
        if (!dir.exists()) return;
        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));
        if (files == null) return;
        for (File file : files) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String id = br.readLine();
                String nombre = br.readLine();
                String descripcion = br.readLine();
                LocalDate fecha = LocalDate.parse(br.readLine(), DATE_FORMAT);
                LocalDateTime hora = LocalDateTime.parse(br.readLine(), TIME_FORMAT);
                double precioBase = Double.parseDouble(br.readLine());
                String imagenPath = br.readLine();
                if (imagenPath != null && imagenPath.isEmpty()) imagenPath = null;
                Evento evento = new Evento(id, nombre, descripcion, fecha, hora, precioBase, imagenPath);
                boolean[][] asientos = new boolean[10][14];
                for (int i = 0; i < 10; i++) {
                    String linea = br.readLine();
                    if (linea == null) break;
                    for (int j = 0; j < 14; j++) {
                        asientos[i][j] = linea.charAt(j) == '1';
                    }
                }
                evento.setAsientos(asientos);
                int numEntradas = Integer.parseInt(br.readLine());
                for (int k = 0; k < numEntradas; k++) {
                    String[] parts = br.readLine().split(",");
                    String tipo = parts[0];
                    int fila = Integer.parseInt(parts[1]);
                    int col = Integer.parseInt(parts[2]);
                    Entrada entrada;
                    switch (tipo) {
                        case "VIP": entrada = new EntradaVIP("", fila, col); break;
                        case "EST": entrada = new EntradaEstudiante("", fila, col); break;
                        default: entrada = new EntradaGeneral("", fila, col);
                    }
                    evento.getEntradasVendidas().add(entrada);
                }
                eventos.add(evento);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void guardarPublicados() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(PUBLICADOS_FILE))) {
            for (Evento e : eventosPublicados) {
                pw.println(e.getId());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void cargarPublicados() {
        eventosPublicados.clear();
        File f = new File(PUBLICADOS_FILE);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String id;
            while ((id = br.readLine()) != null) {
                Evento e = buscarEventoPorId(id);
                if (e != null) eventosPublicados.add(e);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}