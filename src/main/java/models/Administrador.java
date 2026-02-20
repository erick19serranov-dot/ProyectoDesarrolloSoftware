package models;

import java.util.ArrayList;

public class Administrador {

private String name;
private String password;
private ArrayList<Administrador> lista = new ArrayList<>();

public Administrador(String name, String password) {
    this.name = name;
    this.password = password;
}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void guardarEnArchivo() {
        try {
            java.io.FileWriter writer = new java.io.FileWriter("administradores.txt", true);
            writer.write(this.name + "," + this.password + System.lineSeparator());
            writer.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

public void cargarDesdeArchivo() {
    lista.clear();
    try {
        java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader("administradores.txt"));
        String linea;
        while ((linea = reader.readLine()) != null) {
            String[] partes = linea.split(",", 2);
            if (partes.length == 2) {
                Administrador admin = new Administrador(partes[0], partes[1]);
                lista.add(admin);
            }
        }
        reader.close();
    } catch (java.io.IOException e) {
        e.printStackTrace();
    }
}

public ArrayList<Administrador> getLista() {
    return lista;
}
}
