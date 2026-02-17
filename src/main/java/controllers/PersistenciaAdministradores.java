package controllers;

import models.Administrador;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class PersistenciaAdministradores {

    private static final String ARCHIVO_ADMINISTRADORES = "administradores.txt";
    private static final String SEPARADOR = ";";


    public static List<Administrador> cargarAdministradores() throws IOException {
        List<Administrador> lista = new ArrayList<>();
        File archivo = new File(ARCHIVO_ADMINISTRADORES);

        if (!archivo.exists()) {
            return lista;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;
                String[] partes = linea.split(SEPARADOR, 2);
                if (partes.length >= 2) {
                    String usuario = partes[0].trim();
                    String password = partes[1].trim();
                    if (!usuario.isEmpty()) {
                        lista.add(new Administrador(usuario, password));
                    }
                }
            }
        }
        return lista;
    }

    public static void guardarAdministradores(List<Administrador> administradores) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_ADMINISTRADORES))) {
            for (Administrador a : administradores) {
                bw.write(a.getName() + SEPARADOR + a.getPassword());
                bw.newLine();
            }
        }
    }

  
    public static void agregarAdministrador(Administrador admin) throws IOException {
        List<Administrador> lista = cargarAdministradores();
        String nombreNuevo = admin.getName();
        lista.removeIf(a -> nombreNuevo.equalsIgnoreCase(a.getName()));
        lista.add(admin);
        guardarAdministradores(lista);


    }

   
    public static void agregarAdministrador(String usuario, String password) throws IOException {
        agregarAdministrador(new Administrador(usuario, password));
        // Agregar un administrador desde código
PersistenciaAdministradores.agregarAdministrador("dylan", "1234");
PersistenciaAdministradores.agregarAdministrador("erik", "clave123");
    }
}
