package persistence.dao;

import java.util.ArrayList;
import model.Document;
import model.Package;

/**
 * Patron DAO de la entidad Document. Se abstraen los metodos necesarios para trabajar con la clase Document
 * @author Sergio
 */
public interface DocumentDao {

    public ArrayList<Document> list(Package pack);

    public boolean add(Document document, int pack_id);
    
    public boolean delete(int id);
}
