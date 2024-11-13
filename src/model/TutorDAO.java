package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static model.DAO.connect;
import static model.DAO.createTable;
import static model.DAO.executeUpdate;
import static model.DAO.getResultSet;
import static model.DAO.lastId;

public class TutorDAO {
    private static TutorDAO instance;

    private TutorDAO() {
        connect();
        createTable();
    }

    // Singleton
    public static TutorDAO getInstance() {
        return (instance == null ? (instance = new TutorDAO()) : instance);
    }
    
    // Create
    public Tutor create(String nome, String endereco, String cpf, String celular) {
        try {
            PreparedStatement stmt;
            stmt = DAO.connect().prepareStatement("INSERT INTO tutor (nome, endereco, cpf, celular) VALUES (?,?,?,?)");
            stmt.setString(1, nome);
            stmt.setString(2, endereco);
            stmt.setString(3, cpf);
            stmt.setString(4, celular);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return this.retrieveById(lastId("tutor", "id"));
    }
    
    private Tutor buildObject(ResultSet rs) {
        Tutor tutor = null;
        try {
            tutor = new Tutor(rs.getInt("id"), rs.getString("nome"), rs.getString("endereco"), rs.getString("cpf"), rs.getString("celular"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        
        return tutor;
    }
    
    // Generic Retriever
    public List retrieve(String query) {    
        List<Tutor> tutores = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                tutores.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        
        return tutores;
    }
    
    // RetrieveById
    public Tutor retrieveById(int id) {
        List<Tutor> tutores = this.retrieve("SELECT * FROM tutor WHERE id = " + id);
        return (tutores.isEmpty() ? null : tutores.get(0));
    }
    
    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM tutor");
    }
    
    // RetrieveLast
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM tutor WHERE id = " + lastId("tutor", "id"));
    }
    
    // RetrieveBySimilarName
    public List retrieveBySimilarName(String nome) {
        return this.retrieve("SELECT * FROM tutor WHERE nome LIKE '%" + nome + "%'");
    }
    
     // Updade
    public void update(Tutor tutor) {
        try {
            PreparedStatement stmt;
            stmt = connect().prepareStatement("UPDATE tutor SET nome=?, endereco=?, cpf=?, celular=? WHERE id=?");
            stmt.setString(1, tutor.getNome());
            stmt.setString(2, tutor.getEndereco());
            stmt.setString(3, tutor.getCpf());
            stmt.setString(4, tutor.getCelular());
            stmt.setInt(5, tutor.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
    // Delete   
    public void delete(Tutor tutor) {
        PreparedStatement stmt;
        try {
            stmt = connect().prepareStatement("DELETE FROM tutor WHERE id = ?");
            stmt.setInt(1, tutor.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
