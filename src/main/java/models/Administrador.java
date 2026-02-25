package models;

import java.io.*;
import java.util.ArrayList;

public class Administrador {
    private String name;
    private String password;
    private ArrayList<Administrador> lista = new ArrayList<>();

    public Administrador(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public void guardarEnArchivo() {
        try (FileWriter writer = new FileWriter("administradores.txt", true)) {
            writer.write(this.name + "," + this.password + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarDesdeArchivo() {
        lista.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("administradores.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",", 2);
                if (partes.length == 2) {
                    Administrador admin = new Administrador(partes[0], partes[1]);
                    lista.add(admin);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Administrador> getLista() {
        return lista;
    }
}