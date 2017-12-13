package persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Project;
import model.User;
import persistence.dao.ProjectDao;
import persistence.factory.MysqlDaoFactory;

/**
 * Patron DAO. Implementacion de metodos ProjectDao.
 * @author Sergio
 */
public class ProjectImpl implements ProjectDao {

    private final Connection conn;

    /**
     * Constructor en donde se conecta con mysql
     */
    public ProjectImpl() {
        this.conn = MysqlDaoFactory.createConnection();
    }

    /**
     * Metodo que agrega un objeto Project a la tabla Project de la base de datos.
     * @param project Objeto Project a guardar
     * @param user User asociado al proyecto
     * @return boleano para saber el estado de la agregacion. True = guardo
     */
    @Override
    public boolean add(Project project, User user) {

        boolean resultado = false;
        String sql = "INSERT INTO Project (project_name, User_user_id) values(?,?)";
        try {
            PreparedStatement pstm = this.conn.prepareStatement(sql);
            pstm.setString(1, project.getName());
            pstm.setInt(2, user.getId());
            pstm.executeUpdate();
            resultado = true;

        } catch (SQLException ex) {
            Logger.getLogger(ProjectImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    /**
     * Metodo que lista todos los packages que hay en un User. Asociado a la BD
     * @param user User a listar los proyectos
     * @return vector con los proyectos obtenidos (en caso de existir)
     */
    @Override
    public ArrayList<Project> list(User user) {

        ArrayList<Project> projects = new ArrayList<>();

        ResultSet rs;
        String sql = "SELECT * FROM Project WHERE User_user_id ="+user.getId();

        try {

            PreparedStatement pstm = this.conn.prepareStatement(sql);
            rs = pstm.executeQuery();

            if (!rs.next()) {
                System.out.println("NO HAY DATOS");

            } else {
                do {
                    Project proj = new Project();
                    proj.setId(rs.getInt("project_id"));
                    proj.setName(rs.getString("project_name"));
                    projects.add(proj);

                } while (rs.next());
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProjectImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return projects;

    }
    
    /**
     * Metodo que elimina un proyecto a partir del ID, en la tabla Project
     * @param id id del projecto a eliminar
     * @return boleano para saber el estado de la eliminacion. True = eliminado
     */
    @Override
    public boolean delete(int id) {
        boolean result = false;
        String sql = "DELETE FROM Project WHERE project_id = ?";

        try {
            PreparedStatement pstm = this.conn.prepareStatement(sql);
            pstm.setInt(1, id);
            int filasAfectadas = pstm.executeUpdate();
            result = (filasAfectadas != 0);
            Logger.getLogger(ProjectImpl.class.getName()).log(Level.SEVERE, "BORRA {0}", result);

        } catch (SQLException ex) {
            Logger.getLogger(ProjectImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }



    /**
     * Map the current row of the given ResultSet to an Project.
     *
     * @param resultSet The ResultSet of which the current row is to be mapped
     * to an Project.
     * @return The mapped Project from the current row of the given ResultSet.
     * @throws SQLException If something fails at database level.
     */
    private static Project map(ResultSet resultSet) throws SQLException {
        Project proj = new Project();
        proj.setId(resultSet.getInt("project_id"));
        proj.setName(resultSet.getString("project_name"));
        return proj;
    }

    
}
