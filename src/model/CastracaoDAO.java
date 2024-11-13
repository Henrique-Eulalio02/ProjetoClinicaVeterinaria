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
    public Castracao create(int servicoId, String tipoCastracao, int idadeAnimalNaCastracao, double pesoAnimalNaCastracao) {
        try {          
            PreparedStatement stmt;
            stmt = DAO.connect().prepareStatement("INSERT INTO castracao (servicoId, tipoCastracao, idadeAnimalNaCastracao, pesoAnimalNaCastracao) VALUES (?,?,?,?)");
            stmt.setInt(1, servicoId);
            stmt.setString(2, tipoCastracao);
            stmt.setInt(3, idadeAnimalNaCastracao);
            stmt.setDouble(4, pesoAnimalNaCastracao);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(CastracaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return this.retrieveById(lastId("castracao", "id"));
    }
    
    private Castracao buildObject(ResultSet rs) {
        Castracao castracao = null;
        try {
            castracao = new Castracao(rs.getInt("id"), rs.getInt("servicoId"), rs.getString("tipoCastracao"), rs.getInt("idadeAnimalNaCastracao"), rs.getDouble("pesoAnimalNaCastracao"));
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
            stmt = connect().prepareStatement("UPDATE consulta SET servicoId=?, tipoCastracao=?, idadeAnimalNaCastracao=?, pesoAnimalNaCastracao=? WHERE id=?");
            stmt.setInt(1, castracao.getServicoId());
            stmt.setString(2, castracao.getTipoCastracao());
            stmt.setInt(3, castracao.getIdadeAnimalNaCastracao());
            stmt.setDouble(4, castracao.getPesoAnimalNaCastracao());
            stmt.setInt(5, castracao.getId());
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
