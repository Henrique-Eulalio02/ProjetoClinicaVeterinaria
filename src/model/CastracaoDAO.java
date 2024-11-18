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

public class CastracaoDAO {
    private static CastracaoDAO instance;

    private CastracaoDAO() {
        connect();
        createTable();
    }

    // Singleton
    public static CastracaoDAO getInstance() {
        return (instance == null ? (instance = new CastracaoDAO()) : instance);
    }
    
    // Create
    public Castracao create(int animalId, int veterinarioId, String tipoCastracao, int idadeAnimalNaCastracao, double pesoAnimalNaCastracao) {
        try {          
            PreparedStatement stmt;
            stmt = DAO.connect().prepareStatement("INSERT INTO castracao (animalId, veterinarioId, tipoCastracao, idadeAnimalNaCastracao, pesoAnimalNaCastracao) VALUES (?,?,?,?,?)");
            stmt.setInt(1, animalId);
            stmt.setInt(2, veterinarioId);
            stmt.setString(3, tipoCastracao);
            stmt.setInt(4, idadeAnimalNaCastracao);
            stmt.setDouble(5, pesoAnimalNaCastracao);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(CastracaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return this.retrieveById(lastId("castracao", "id"));
    }
    
    private Castracao buildObject(ResultSet rs) {
        Castracao castracao = null;
        try {
            castracao = new Castracao(rs.getInt("id"), rs.getInt("animalId"), rs.getInt("veterinarioId"), rs.getString("tipoCastracao"), rs.getInt("idadeAnimalNaCastracao"), rs.getDouble("pesoAnimalNaCastracao"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        
        return castracao;
    }
    
    // Generic Retriever
    public List retrieve(String query) {    
        List<Castracao> castracoes = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                castracoes.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        
        return castracoes;
    }
    
    // RetrieveById
    public Castracao retrieveById(int id) {
        List<Castracao> castracoes = this.retrieve("SELECT * FROM castracao WHERE id = " + id);
        return (castracoes.isEmpty() ? null : castracoes.get(0));
    }
    
    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM castracao");
    }
    
    // RetrieveLast
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM castracao WHERE id = " + lastId("castracao", "id"));
    }
    
    // RetrieveBySimilarName
    public List retrieveBySimilarName(String nome) {
        return this.retrieve("SELECT * FROM castracao WHERE nome LIKE '%" + nome + "%'");
    }
    
     // Updade
    public void update(Castracao castracao) {
        try {
            PreparedStatement stmt;
            stmt = connect().prepareStatement("UPDATE consulta SET animalId=?, veterinarioId=?, tipoCastracao=?, idadeAnimalNaCastracao=?, pesoAnimalNaCastracao=? WHERE id=?");
            stmt.setInt(1, castracao.getAnimalId());
            stmt.setInt(2, castracao.getVeterinarioId());
            stmt.setString(3, castracao.getTipoCastracao());
            stmt.setInt(4, castracao.getIdadeAnimalNaCastracao());
            stmt.setDouble(5, castracao.getPesoAnimalNaCastracao());
            stmt.setInt(6, castracao.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
    // Delete   
    public void delete(Castracao castracao) {
        PreparedStatement stmt;
        try {
            stmt = connect().prepareStatement("DELETE FROM castracao WHERE id = ?");
            stmt.setInt(1, castracao.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
