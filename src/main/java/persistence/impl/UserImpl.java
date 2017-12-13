package persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import persistence.dao.UserDao;
import persistence.factory.MysqlDaoFactory;

/**
 * Patron DAO. Implementacion de metodos UserDao.
 * @author Sergio
 */
public class UserImpl implements UserDao {

    private final Connection conn;

     /**
     * Constructor en donde se conecta con mysql
     */
    public UserImpl() {
        this.conn = MysqlDaoFactory.createConnection();
    }

    /**
     * Metodo que agrega un objeto User a la tabla User de la base de datos.
     * @param user Objeto User a guardar
     * @return boleano para saber el estado de la agregacion. True = guardo
     */
    @Override
    public boolean add(User user) {

        boolean resultado = false;
        String sql = "INSERT INTO User(user_username, user_password) values(?,?)";
        try {
            PreparedStatement pstm = this.conn.prepareStatement(sql);
            pstm.setString(1, user.getUsername());
            pstm.setString(2, user.getPassword());
            pstm.executeUpdate();
            resultado = true;

        } catch (SQLException ex) {
            Logger.getLogger(DocumentImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    /**
     * Metodo que busca un usuario por su username y password
     * @param username username del usuario a buscar
     * @param password correspondiente al username del usuario a buscar
     * @return user si el username y password correspondian con los registro de la BD
     */
    @Override
    public User search(String username, String password) {

        User user = null;
        ResultSet rs;
        String sql = "SELECT * FROM User WHERE user_username = ? AND user_password = ?";

        try {

            PreparedStatement pstm = this.conn.prepareStatement(sql);
            pstm.setString(1, username);
            pstm.setString(2, password);
            rs = pstm.executeQuery();

            if (rs.next()) {
                user = map(rs);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }


    /**
     * Map the current row of the given ResultSet to an User.
     *
     * @param resultSet The ResultSet of which the current row is to be mapped
     * to an User.
     * @return The mapped User from the current row of the given ResultSet.
     * @throws SQLException If something fails at database level.
     */
    private static User map(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("user_id"));
        user.setUsername(resultSet.getString("user_username"));
        user.setPassword(resultSet.getString("user_password"));
        return user;
    }

}
