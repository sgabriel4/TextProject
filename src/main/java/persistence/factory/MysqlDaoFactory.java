package persistence.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.ServletContext;

/**
 * Patron DAO para la creacion de la conexcion con MYSQL. Se obtienen los parametros desde el web.xml
 * @author Sergio
 */
public class MysqlDaoFactory extends DAOFactory {

    private static String BDNAME;
    private static String DRIVER;
    private static String URL;
    private static String USER;
    private static String CLAVE;

    public MysqlDaoFactory(ServletContext servletContext) {

        MysqlDaoFactory.DRIVER = (String) servletContext.getInitParameter("jdbcDriver");
        MysqlDaoFactory.URL = (String) servletContext.getInitParameter("jdbcUrl");
        MysqlDaoFactory.BDNAME = (String) servletContext.getInitParameter("jdbcDb");
        MysqlDaoFactory.USER = (String) servletContext.getInitParameter("jdbcUser");
        MysqlDaoFactory.CLAVE = (String) servletContext.getInitParameter("jdbcPassword");
    }

    public static Connection createConnection() {

        Connection con = null;
        try {
            Class.forName(MysqlDaoFactory.DRIVER);
            con = DriverManager.getConnection(MysqlDaoFactory.URL + MysqlDaoFactory.BDNAME,
                    MysqlDaoFactory.USER, MysqlDaoFactory.CLAVE);
        } catch (ClassNotFoundException | SQLException ex) {
            
        }

        return con;
    }
}
