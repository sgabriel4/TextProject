package persistence.factory;


import javax.servlet.ServletContext;
import persistence.dao.DocumentDao;
import persistence.dao.PackageDao;
import persistence.dao.ProjectDao;
import persistence.dao.UserDao;
import persistence.impl.DocumentImpl;
import persistence.impl.PackageImpl;
import persistence.impl.ProjectImpl;
import persistence.impl.UserImpl;

/**
 * Patron DAO necesario para obtener los DAO de cada entidad, y obtener la base de datos.
 * @author Sergio
 */
public abstract class DAOFactory {

    public static DAOFactory getFactory(TipoBD bd, ServletContext context) {
        
        switch (bd) {
            case MYSQL:
                return new MysqlDaoFactory(context);
            case ORACLE:
                throw new IllegalArgumentException();
            default:
                throw new IllegalArgumentException();
        }

    }

    public UserDao getUserDao() { return new UserImpl(); }

    public ProjectDao getProjectDao() { return new ProjectImpl(); }
    
    public PackageDao getPackageDao() { return new PackageImpl(); }
    
    public DocumentDao getDocumentDao() { return new DocumentImpl(); }
}
