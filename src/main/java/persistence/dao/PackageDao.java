package persistence.dao;

import java.util.ArrayList;
import model.Package;
import model.Project;

/**
 * Patron DAO de la entidad Package. Se abstraen los metodos necesarios para trabajar con la clase Package
 * @author Sergio
 */
public interface PackageDao {

    public ArrayList<Package> list(Project project);

    public boolean add(Package pack, int project_id);
    
    public boolean delete(int id);
}
