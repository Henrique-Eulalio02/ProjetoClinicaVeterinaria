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

public class ConsultaGeralDAO {
    private static ConsultaGeralDAO instance;

    private ConsultaGeralDAO() {
        connect();
        createTable();
    }

    // Singleton
    public static ConsultaGeralDAO getInstance() {
        return (instance == null ? (instance = new ConsultaGeralDAO()) : instance);
    }
    
    // Create
    public ConsultaGeral create(int animalId, int veterinarioId, String motivo, String diagnostico) {
        try {          
            PreparedStatement stmt;
            stmt = DAO.connect().prepareStatement("INSERT INTO consulta (animalId, veterinarioId, motivo, diagnostico) VALUES (?,?,?,?)");
            stmt.setInt(1, animalId);
            stmt.setInt(2, veterinarioId);
            stmt.setString(3, motivo);
            stmt.setString(4, diagnostico);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaGeralDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return this.retrieveById(lastId("consulta", "id"));
    }
    
    private ConsultaGeral buildObject(ResultSet rs) {
        ConsultaGeral consultaGeral = null;
        try {
            consultaGeral = new ConsultaGeral(rs.getInt("id"), rs.getInt("animalId"), rs.getInt("veterinarioId"), rs.getString("motivo"), rs.getString("diagnostico"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        
        return consultaGeral;
    }
    
    // Generic Retriever
    public List retrieve(String query) {    
        List<ConsultaGeral> consultas = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                consultas.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        
        return consultas;
    }
    
    // RetrieveById
    public ConsultaGeral retrieveById(int id) {
        List<ConsultaGeral> consultas = this.retrieve("SELECT * FROM consulta WHERE id = " + id);
        return (consultas.isEmpty() ? null : consultas.get(0));
    }
    
    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM consulta");
    }
    
    // RetrieveLast
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM consulta WHERE id = " + lastId("consulta", "id"));
    }
    
    public List<ConsultaGeral> retrieveBySelectedAnimal () {
        return this.retrieve("SELECT * FROM consulta WHERE animalId = " + Controller.getAnimalSelecionado().getId());
    }
    
    public List retrieveByDiagnostico(String diag) {
        return this.retrieve("SELECT * FROM consulta WHERE diagnostico LIKE '%" + diag + "%'");
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
    
     // Update
    public void update(ConsultaGeral consultaGeral) {
        try {
            PreparedStatement stmt;
            stmt = connect().prepareStatement("UPDATE consulta SET animalId=?, veterinarioId=?, motivo=?, diagnostico=? WHERE id=?");
            stmt.setInt(1, consultaGeral.getAnimalId());
            stmt.setInt(2, consultaGeral.getVeterinarioId());
            stmt.setString(3, consultaGeral.getMotivo());
            stmt.setString(4, consultaGeral.getDiagnostico());
            stmt.setInt(5, consultaGeral.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
    // Delete   
    public void delete(ConsultaGeral consultaGeral) {
        PreparedStatement stmt;
        try {
            stmt = connect().prepareStatement("DELETE FROM consulta WHERE id = ?");
            stmt.setInt(1, consultaGeral.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
