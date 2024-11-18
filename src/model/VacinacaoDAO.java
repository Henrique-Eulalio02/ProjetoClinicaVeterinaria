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
    public Vacinacao create(int animalId, int veterinarioId, int vacinaId, Timestamp data, Timestamp dataProximaDose) {
        try {
            PreparedStatement stmt;
            stmt = DAO.connect().prepareStatement("INSERT INTO vacinacao (animalId, veterinarioId, vacinaId, data, dataProximaDose) VALUES (?,?,?,?,?)");
            stmt.setInt(1, animalId);
            stmt.setInt(2, veterinarioId);
            stmt.setInt(3, vacinaId);
            stmt.setTimestamp(4, data);
            stmt.setTimestamp(5, dataProximaDose);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(VacinacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return this.retrieveById(lastId("vacinacao", "id"));
    }
    
    private Vacinacao buildObject(ResultSet rs) {
        Vacinacao vacinacao = null;
        try {
            vacinacao = new Vacinacao(rs.getInt("id"), rs.getInt("animalId"), rs.getInt("veterinarioId"), rs.getInt("vacinaId"), rs.getTimestamp("data"), rs.getTimestamp("dataProximaDose"));
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
    
    public List<Vacinacao> retrieveBySelectedAnimal () {
        return this.retrieve("SELECT * FROM vacinacao WHERE animalId = " + Controller.getAnimalSelecionado().getId());
    }
    
    // RetrieveBySimilarName
    public List retrieveBySimilarName(String nome) {
        return this.retrieve("SELECT * FROM vacinacao WHERE nome LIKE '%" + nome + "%'");
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
            return "Erro ao buscar animal";
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
            return "Erro ao buscar veterinário";
        }
    }
    
    public String retriveVacinaName(int vacinaId) {
        try {
            PreparedStatement stmt = connect().prepareStatement("SELECT nome FROM vacina WHERE id = ?");
            stmt.setInt(1, vacinaId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("nome");
            } else {
                return "Vacina não encontrada";
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
            return "Erro ao buscar vacina";
        }
    }
    
     // Updade
    public void update(Vacinacao vacinacao) {
        try {
            PreparedStatement stmt;
            stmt = connect().prepareStatement("UPDATE vacinacao SET animalId=?, veterinarioId=?, vacinaId=?, data=?, dataProximaDose=? WHERE id=?");
            stmt.setInt(1, vacinacao.getAnimalId());
            stmt.setInt(2, vacinacao.getVeterinarioId());
            stmt.setInt(3, vacinacao.getVacinaId());
            stmt.setTimestamp(4, vacinacao.getData());
            stmt.setTimestamp(5, vacinacao.getDataProximaDose());
            stmt.setInt(6, vacinacao.getId());
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
