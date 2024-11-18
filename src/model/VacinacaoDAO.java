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

public class VacinacaoDAO {
    private static VacinacaoDAO instance;

    private VacinacaoDAO() {
        connect();
        createTable();
    }

    // Singleton
    public static VacinacaoDAO getInstance() {
        return (instance == null ? (instance = new VacinacaoDAO()) : instance);
    }
    
    // Create
    public Vacinacao create(int animalId, int veterinarioId, Vacina vacina, Timestamp dataProximaDose) {
        try {
            int vacinaId = vacina.getId();
            
            PreparedStatement stmt;
            stmt = DAO.connect().prepareStatement("INSERT INTO vacinacao (animalId, veterinarioId, vacinaId, dataProximaDose) VALUES (?,?,?,?)");
            stmt.setInt(1, animalId);
            stmt.setInt(2, veterinarioId);
            stmt.setInt(3, vacinaId);
            stmt.setTimestamp(4, dataProximaDose);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(VacinacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return this.retrieveById(lastId("vacinacao", "id"));
    }
    
    private Vacinacao buildObject(ResultSet rs) {
        Vacinacao vacinacao = null;
        try {
            vacinacao = new Vacinacao(rs.getInt("id"), rs.getInt("animalId"), rs.getInt("veterinarioId"), rs.getInt("vacinaId"), rs.getTimestamp("dataProximaDose"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        
        return vacinacao;
    }
    
    // Generic Retriever
    public List retrieve(String query) {    
        List<Vacinacao> vacinacoes = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                vacinacoes.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        
        return vacinacoes;
    }
    
    // RetrieveById
    public Vacinacao retrieveById(int id) {
        List<Vacinacao> vacinacoes = this.retrieve("SELECT * FROM vacinacao WHERE id = " + id);
        return (vacinacoes.isEmpty() ? null : vacinacoes.get(0));
    }
    
    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM vacinacao");
    }
    
    // RetrieveLast
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM vacinacao WHERE id = " + lastId("vacinacao", "id"));
    }
    
    // RetrieveBySimilarName
    public List retrieveBySimilarName(String nome) {
        return this.retrieve("SELECT * FROM vacinacao WHERE nome LIKE '%" + nome + "%'");
    }
    
     // Updade
    public void update(Vacinacao vacinacao) {
        try {
            PreparedStatement stmt;
            stmt = connect().prepareStatement("UPDATE vacinacao SET animalId=?, veterinarioId=?, vacinaId=?, dataProximaDose=? WHERE id=?");
            stmt.setInt(1, vacinacao.getAnimalId());
            stmt.setInt(2, vacinacao.getVeterinarioId());
            stmt.setInt(3, vacinacao.getVacinaId());
            stmt.setTimestamp(4, vacinacao.getDataProximaDose());
            stmt.setInt(5, vacinacao.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
    // Delete   
    public void delete(Vacinacao vacinacao) {
        PreparedStatement stmt;
        try {
            stmt = connect().prepareStatement("DELETE FROM vacinacao WHERE id = ?");
            stmt.setInt(1, vacinacao.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
