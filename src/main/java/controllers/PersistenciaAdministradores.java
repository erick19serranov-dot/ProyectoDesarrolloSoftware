package controllers;

import models.Administrador;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class PersistenciaAdministradores {

    private static final String archivo = "administradores.txt";
    private static final String separador = ";";



    public static void guardarAdministrador(Administrador admin) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            bw.write(admin.getName() + separador + admin.getPassword());
            bw.newLine();
        }
    }


    public static void agregarAdministrador(String nombre, String password) throws IOException {
        Administrador admin = new Administrador(nombre, password);
        guardarAdministrador(admin);
    }


    public static void crearAdministradorPorDefecto() throws IOException {
        agregarAdministrador("profe", "1234");
    }


    public static List<Administrador> cargarAdministradores() throws IOException {
        List<Administrador> administradores = new ArrayList<>();
        File file = new File(archivo);

        if (!file.exists()) {
            return administradores;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(separador);
                if (partes.length >= 2) {
                    Administrador admin = new Administrador(partes[0], partes[1]);
                    administradores.add(admin);
                }
            }
        }
        return administradores;
    }
}
