package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static model.DAO.connect;
import static model.DAO.createTable;
import static model.DAO.executeUpdate;
import static model.DAO.getResultSet;
import static model.DAO.lastId;


public class ServicoDAO {
    private static ServicoDAO instance;

    private ServicoDAO() {
        connect();
        createTable();
    }

    // Singleton
    public static ServicoDAO getInstance() {
        return (instance == null ? (instance = new ServicoDAO()) : instance);
    }
    
    // Create
    public Servico create(int animalId, int veterinarioId, Timestamp data, double valor, double gasto) {
        try {
            PreparedStatement stmt;
            stmt = DAO.connect().prepareStatement("INSERT INTO servico (animalId, veterinarioId, data, valor, gasto) VALUES (?,?,?,?,?)");
            stmt.setInt(1, animalId);
            stmt.setInt(2, veterinarioId);
            stmt.setTimestamp(3, data);
            stmt.setDouble(4, valor);
            stmt.setDouble(5, gasto);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(ServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return this.retrieveById(lastId("servico", "id"));
    }
    
    private Servico buildObject(ResultSet rs) {
        Servico servico = null;
        try {
            servico = new Servico(rs.getInt("id"), rs.getInt("animalId"), rs.getInt("veterinarioId"), rs.getTimestamp("data"), rs.getDouble("valor"), rs.getDouble("gasto"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        
        return servico;
    }
    
    // Generic Retriever
    public List retrieve(String query) {    
        List<Servico> servicos = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                servicos.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        
        return servicos;
    }
    
    // RetrieveById
    public Servico retrieveById(int id) {
        List<Servico> servicos = this.retrieve("SELECT * FROM servico WHERE id = " + id);
        return (servicos.isEmpty() ? null : servicos.get(0));
    }
    
    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM servico");
    }
    
    // RetrieveLast
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM servico WHERE id = " + lastId("servico", "id"));
    }
    
    // RetrieveBySimilarName
    public List retrieveBySimilarName(String nome) {
        return this.retrieve("SELECT * FROM servico WHERE nome LIKE '%" + nome + "%'");
    }
    
     // Updade
    public void update(Servico servico) {
        try {
            PreparedStatement stmt;
            stmt = connect().prepareStatement("UPDATE animal SET animalId=?, veterinarioId=?, data=?, valor=?, gasto=? WHERE id=?");
            stmt.setInt(1, servico.getAnimalId());
            stmt.setInt(2, servico.getVeterinarioId());
            stmt.setTimestamp(3, servico.getData());
            stmt.setDouble(4, servico.getValor());
            stmt.setDouble(5, servico.getGasto());
            stmt.setInt(6, servico.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
    // Delete   
    public void delete(Servico servico) {
        PreparedStatement stmt;
        try {
            stmt = connect().prepareStatement("DELETE FROM servico WHERE id = ?");
            stmt.setInt(1, servico.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
