package model;

import controller.Controller;
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

public class ExameDAO {
    private static ExameDAO instance;

    private ExameDAO() {
        connect();
        createTable();
    }

    // Singleton
    public static ExameDAO getInstance() {
        return (instance == null ? (instance = new ExameDAO()) : instance);
    }
    
    // Create
    public Exame create(int animalId, int veterinarioId, String tipoExame, String resultados, Timestamp data) {
        try {          
            PreparedStatement stmt;
            stmt = DAO.connect().prepareStatement("INSERT INTO exame (animalId, veterinarioId, tipoExame, resultados, data) VALUES (?,?,?,?,?)");
            stmt.setInt(1, animalId);
            stmt.setInt(2, veterinarioId);
            stmt.setString(3, tipoExame);
            stmt.setString(4, resultados);
            stmt.setTimestamp(5, data);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(ExameDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return this.retrieveById(lastId("exame", "id"));
    }
    
    private Exame buildObject(ResultSet rs) {
        Exame exame = null;
        try {
            exame = new Exame(rs.getInt("id"), rs.getInt("animalId"), rs.getInt("veterinarioId"), rs.getString("tipoExame"), rs.getString("resultados"), rs.getTimestamp("data"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        
        return exame;
    }
    
    // Generic Retriever
    public List retrieve(String query) {    
        List<Exame> exames = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                exames.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        
        return exames;
    }
    
    // RetrieveById
    public Exame retrieveById(int id) {
        List<Exame> exames = this.retrieve("SELECT * FROM exame WHERE id = " + id);
        return (exames.isEmpty() ? null : exames.get(0));
    }
    
    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM exame");
    }
    
    public List<Exame> retrieveBySelectedAnimal () {
        return this.retrieve("SELECT * FROM exame WHERE animalId = " + Controller.getAnimalSelecionado().getId());
    }
    
    // RetrieveLast
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM exame WHERE id = " + lastId("exame", "id"));
    }
    
    // RetrieveBySimilarName
    public List retrieveBySimilarName(String nome) {
        return this.retrieve("SELECT * FROM exame WHERE nome LIKE '%" + nome + "%'");
    }
    
    public List retrieveByTipoExame(String nome) {
        return this.retrieve("SELECT tipExame FROM exame WHERE tipoExame LIKE '%" + nome + "%'");
    }
    
    public String retriveAnimalName(int animalId) {
        try {
            PreparedStatement stmt = connect().prepareStatement("SELECT nome FROM animal WHERE id = ?");
            stmt.setInt(1, animalId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("nome");
            } else {
                return "Animal não encontrado";
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
            return "Erro ao buscar tutor";
        }
    } 
    
    public String retriveVeterinarioName(int veterinarioId) {
        try {
            PreparedStatement stmt = connect().prepareStatement("SELECT nome FROM veterinario WHERE id = ?");
            stmt.setInt(1, veterinarioId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("nome");
            } else {
                return "Veterinario não encontrado";
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
            return "Erro ao buscar tutor";
        }
    }
    
     // Updade
    public void update(Exame exame) {
        try {
            PreparedStatement stmt;
            stmt = connect().prepareStatement("UPDATE exame SET animalId=?, veterinarioId=?, tipoExame=?, resultados=?, data=? WHERE id=?");
            stmt.setInt(1, exame.getAnimalId());
            stmt.setInt(2, exame.getVeterinarioId());
            stmt.setString(3, exame.getTipoExame());
            stmt.setString(4, exame.getResultados());
            stmt.setTimestamp(5, exame.getData());
            stmt.setInt(6, exame.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
    // Delete   
    public void delete(Exame exame) {
        PreparedStatement stmt;
        try {
            stmt = connect().prepareStatement("DELETE FROM exame WHERE id = ?");
            stmt.setInt(1, exame.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
