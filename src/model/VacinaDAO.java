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

public class VacinaDAO {
    private static VacinaDAO instance;

    private VacinaDAO() {
        connect();
        createTable();
    }

    // Singleton
    public static VacinaDAO getInstance() {
        return (instance == null ? (instance = new VacinaDAO()) : instance);
    }
    
    // Create
    public Vacina create(String nome, String descricao, String marca, int quantidadeEstoque) {
        try {
            PreparedStatement stmt;
            stmt = DAO.connect().prepareStatement("INSERT INTO vacina (nome, descricao, marca, quantidadeEstoque) VALUES (?,?,?,?)");
            stmt.setString(1, nome);
            stmt.setString(2, descricao);
            stmt.setString(3, marca);
            stmt.setInt(4, quantidadeEstoque);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(VacinaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return this.retrieveById(lastId("vacina", "id"));
    }
    
    private Vacina buildObject(ResultSet rs) {
        Vacina vacina = null;
        try {
            vacina = new Vacina(rs.getInt("id"), rs.getString("nome"), rs.getString("descricao"), rs.getString("marca"), rs.getInt("quantidadeEstoque"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        
        return vacina;
    }
    
    // Generic Retriever
    public List retrieve(String query) {    
        List<Vacina> vacinas = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                vacinas.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        
        return vacinas;
    }
    
    // RetrieveById
    public Vacina retrieveById(int id) {
        List<Vacina> vacinas = this.retrieve("SELECT * FROM vacina WHERE id = " + id);
        return (vacinas.isEmpty() ? null : vacinas.get(0));
    }
    
    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM vacina");
    }
    
    // RetrieveLast
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM vacina WHERE id = " + lastId("vacina", "id"));
    }
    
    // RetrieveBySimilarName
    public List retrieveBySimilarName(String nome) {
        return this.retrieve("SELECT * FROM vacina WHERE nome LIKE '%" + nome + "%'");
    }
    
     // Updade
    public void update(Vacina vacina) {
        try {
            PreparedStatement stmt;
            stmt = connect().prepareStatement("UPDATE vacina SET nome=?, descricao=?, marca=?, quantidadeEstoque=? WHERE id=?");
            stmt.setString(1, vacina.getNome());
            stmt.setString(2, vacina.getDescricao());
            stmt.setString(3, vacina.getMarca());
            stmt.setInt(4, vacina.getQuantidadeEstoque());
            stmt.setInt(5, vacina.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
    // Delete   
    public void delete(Vacina vacina) {
        PreparedStatement stmt;
        try {
            stmt = connect().prepareStatement("DELETE FROM vacina WHERE id = ?");
            stmt.setInt(1, vacina.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
