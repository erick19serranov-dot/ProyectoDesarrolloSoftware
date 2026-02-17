package controllers;

import models.Administrador;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class PersistenciaAdministradores {

    private static final String archivo = "administradores.txt";
    private static final String separador = ";";


private static void guardarAdministrador(Administrador admin) throws IOException {
    BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true));
    // Usamos los getters del Administrador para mantener encapsulamiento
    bw.write(admin.getName() + separador + admin.getPassword());
    bw.newLine();
    bw.close();
}
private static void agregarAdministrador(String nombre, String password) throws IOException {
    Administrador admin = new Administrador();
    admin.setName(nombre);
    admin.setPassword(password);
    guardarAdministrador(admin);
}

private static void crearAdministrador() throws IOException {
    Administrador admin = new Administrador();
    admin.setName("profe");
    admin.setPassword("1234");
    agregarAdministrador(admin);
}

}


                    
                