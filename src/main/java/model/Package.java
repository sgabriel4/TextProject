package model;

import java.util.ArrayList;

/**
 * Clase Package referencia al package del proyecto asociado
 * @author Sergio
 */
public class Package {

    private int id;
    private String name;
    private ArrayList<Document> documents;

    public Package() {
    }

    public Package(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<Document> documents) {
        this.documents = documents;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    

}
