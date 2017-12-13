package persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Project;
import model.Package;
import persistence.dao.PackageDao;
import persistence.factory.MysqlDaoFactory;

/**
 * Patron DAO. Implementacion de metodos PackageDao.
 * @author Sergio
 */
public class PackageImpl implements PackageDao {

    private final Connection conn;

    /**
     * Constructor en donde se conecta con mysql
     */
    public PackageImpl() {
        this.conn = MysqlDaoFactory.createConnection();
    }

     /**
     * Metodo que agrega un objeto Package a la tabla Package de la base de datos.
     * @param pack Objeto Package a guardar
     * @param project_id id del proyecto relacionado al package 
     * @return boleano para saber el estado de la agregacion. True = guardo
     */
    @Override
    public boolean add(Package pack, int project_id) {

        boolean resultado = false;
        String sql = "INSERT INTO Package(Project_project_id, package_name) values(?,?)";
        try {
            PreparedStatement pstm = this.conn.prepareStatement(sql);
            pstm.setInt(1, project_id);
            pstm.setString(2, pack.getName());
            pstm.executeUpdate();
            resultado = true;

        } catch (SQLException ex) {
            Logger.getLogger(DocumentImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    /**
     * Metodo que lista todos los packages que hay en un proyecto. Asociado a la BD
     * @param project Proyecto a listar los packages
     * @return vector con los packages obtenidos (en caso de existir)
     */
    @Override
    public ArrayList<Package> list(Project project) {

        ArrayList<Package> packages = new ArrayList<>();

        ResultSet rs;
        String sql = "SELECT * FROM Package WHERE Project_project_id =" + project.getId();

        try {

            PreparedStatement pstm = this.conn.prepareStatement(sql);
            rs = pstm.executeQuery();

            if (!rs.next()) {
                System.out.println("NO HAY DATOS");

            } else {
                do {
                    Package pack = new Package();
                    pack.setId(rs.getInt("package_id"));
                    pack.setName(rs.getString("package_name"));
                    packages.add(pack);

                } while (rs.next());
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProjectImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return packages;

    }
    
    /**
     * Metodo que elimina un package a partir del ID, en la tabla Package
     * @param id id del package a eliminar
     * @return boleano para saber el estado de la eliminacion. True = eliminado
     */
    @Override
    public boolean delete(int id) {
        boolean result = false;
        String sql = "DELETE FROM Package WHERE package_id = ?";

        try {
            PreparedStatement pstm = this.conn.prepareStatement(sql);
            pstm.setInt(1, id);
            int filasAfectadas = pstm.executeUpdate();
            result = (filasAfectadas != 0);
            Logger.getLogger(PackageImpl.class.getName()).log(Level.SEVERE, "BORRA {0}", result);

        } catch (SQLException ex) {
            Logger.getLogger(PackageImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    /**
     * Map the current row of the given ResultSet to an Package.
     *
     * @param resultSet The ResultSet of which the current row is to be mapped to an Package.
     * @return The mapped Package from the current row of the given ResultSet.
     * @throws SQLException If something fails at database level.
     */
    private static Package map(ResultSet resultSet) throws SQLException {
        Package pack = new Package();
        pack.setId(resultSet.getInt("package_id"));
        pack.setName(resultSet.getString("package_name"));
        return pack;
    }

}
