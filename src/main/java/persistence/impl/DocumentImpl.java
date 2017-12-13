package persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Document;
import model.Package;
import persistence.dao.DocumentDao;
import persistence.factory.MysqlDaoFactory;

/**
 * Patron DAO. Implementacion de metodos DocumentDao.
 * @author Sergio
 */
public class DocumentImpl implements DocumentDao {

    private final Connection conn;

    /**
     * Constructor en donde se conecta con mysql
     */
    public DocumentImpl() {
        this.conn = MysqlDaoFactory.createConnection();
    }

    /**
     * Metodo que agrega un objeto Document a la tabla Document de la base de datos.
     * @param document Objeto Document a guardar
     * @param pack_id int del package relacionado al documento 
     * @return boleano para saber el estado de la agregacion. True = guardo
     */
    @Override
    public boolean add(Document document, int pack_id) {

        boolean resultado = false;
        String sql = "INSERT INTO Document(Package_package_id, document_showName, document_fileName, document_type) values(?,?,?,?)";
        try {
            PreparedStatement pstm = this.conn.prepareStatement(sql);
            pstm.setInt(1, pack_id);
            pstm.setString(2, document.getShowName());
            pstm.setString(3, document.getFileName());
            pstm.setString(4, document.getType());
            pstm.executeUpdate();
            resultado = true;

        } catch (SQLException ex) {
            Logger.getLogger(DocumentImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
    
    /**
     * Metodo que lista todos los documentos que hay en un package. Asociado a la BD
     * @param pack Package a listar los documentos
     * @return vector con los documentos obtenidos (en caso de existir)
     */
    @Override
    public ArrayList<Document> list(Package pack) {

        ArrayList<Document> documents = new ArrayList<>();

        ResultSet rs;
        String sql = "SELECT * FROM Document WHERE Package_package_id=" + pack.getId();

        try {

            PreparedStatement pstm = this.conn.prepareStatement(sql);
            rs = pstm.executeQuery();

            if (!rs.next()) {
                System.out.println("NO HAY DATOS");

            } else {
                do {
                    Document doc = new Document();
                    doc.setId(rs.getInt("document_id"));
                    doc.setShowName(rs.getString("document_showName"));
                    doc.setFileName(rs.getString("document_fileName"));
                    doc.setType(rs.getString("document_type"));
                    documents.add(doc);

                } while (rs.next());
            }

        } catch (SQLException ex) {
            Logger.getLogger(DocumentImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return documents;
    }
    
    /**
     * Metodo que elimina un documento a partir del ID, en la tabla Document
     * @param id id del documento a eliminar
     * @return boleano para saber el estado de la eliminacion. True = eliminado
     */
    @Override
    public boolean delete(int id) {
        boolean result = false;
        String sql = "DELETE FROM Document WHERE document_id = ?";

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
     * Map the current row of the given ResultSet to an Document.
     *
     * @param resultSet The ResultSet of which the current row is to be mapped to an Document.
     * @return The mapped Document from the current row of the given ResultSet.
     * @throws SQLException If something fails at database level.
     */
    private static Document map(ResultSet resultSet) throws SQLException {
        Document doc = new Document();
        doc.setId(resultSet.getInt("document_id"));
        doc.setShowName(resultSet.getString("document_showName"));
        doc.setFileName(resultSet.getString("document_fileName"));
        doc.setType(resultSet.getString("document_type"));
        return doc;
    }

}
