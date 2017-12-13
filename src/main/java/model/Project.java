package model;

import java.util.ArrayList;

/**
 * Clase Project referencia al proyecto asociado a un usuario
 * @author Sergio
 */
public class Project {
    private int id;
    private String name;
    private ArrayList<Package> packages;

    public Project() {
    }

    public Project(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Package> getPackages() {
        return packages;
    }

    public void setPackages(ArrayList<Package> packages) {
        this.packages = packages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
