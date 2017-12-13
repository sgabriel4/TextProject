package persistence.dao;

import java.util.ArrayList;
import model.Project;
import model.User;

/**
 * Patron DAO de la entidad Project. Se abstraen los metodos necesarios para trabajar con la clase Project
 * @author Sergio
 */
public interface ProjectDao {

    public ArrayList<Project> list(User user);
    
    public boolean add(Project project, User user);
    
    public boolean delete(int id);
}
