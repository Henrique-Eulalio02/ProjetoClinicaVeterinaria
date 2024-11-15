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

public class AnimalDAO {
    private static AnimalDAO instance;

    private AnimalDAO() {
        connect();
        createTable();
    }

    // Singleton
    public static AnimalDAO getInstance() {
        return (instance == null ? (instance = new AnimalDAO()) : instance);
    }
    
    // Create
    public Animal create(String nome, String especie, String raca, int idade, String sexo, int tutorId, int veterinarioId) {
        try {
            PreparedStatement stmt;
            stmt = DAO.connect().prepareStatement("INSERT INTO animal (nome, especie, raca, idade, sexo, tutorId, veterinarioId) VALUES (?,?,?,?,?,?,?)");
            stmt.setString(1, nome);
            stmt.setString(2, especie);
            stmt.setString(3, raca);
            stmt.setInt(4, idade);
            stmt.setString(5, sexo);
            stmt.setInt(6, tutorId);
            stmt.setInt(7, veterinarioId);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return this.retrieveById(lastId("animal", "id"));
    }
    
    private Animal buildObject(ResultSet rs) {
        Animal animal = null;
        try {
            animal = new Animal(rs.getInt("id"), rs.getString("nome"), rs.getString("especie"), rs.getString("raca"), rs.getInt("idade"), rs.getString("sexo"), rs.getInt("tutorId"), rs.getInt("veterinarioId"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        
        return animal;
    }
    
    // Generic Retriever
    public List retrieve(String query) {    
        List<Animal> animais = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                animais.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        
        return animais;
    }
    
    // RetrieveById
    public Animal retrieveById(int id) {
        List<Animal> animais = this.retrieve("SELECT * FROM animal WHERE id = " + id);
        return (animais.isEmpty() ? null : animais.get(0));
    }
    
    // RetrieveAll
    public List retrieveAll() {
        return this.retrieve("SELECT * FROM animal");
    }
    
    // RetrieveLast
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM animal WHERE id = " + lastId("animal", "id"));
    }
    
    // RetrieveBySimilarName
    public List retrieveBySimilarName(String nome) {
        return this.retrieve("SELECT * FROM animal WHERE nome LIKE '%" + nome + "%'");
    }
    
    public List retrieveByIdTutor(int id) {
        List<Animal> animaisPorDono = this.retrieve("SELECT * FROM animal WHERE tutorId = " + id);
        return animaisPorDono;
    }
    
    public String retriveTutorName(int tutorId) {
        try {
            PreparedStatement stmt = connect().prepareStatement("SELECT nome FROM tutor WHERE id = ?");
            stmt.setInt(1, tutorId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("nome");
            } else {
                return "Tutor não encontrado";
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
            return "Erro ao buscar tutor";
        }
    }
    
     // Updade
    public void update(Animal animal) {
        try {
            PreparedStatement stmt;
            stmt = connect().prepareStatement("UPDATE animal SET nome=?, especie=?, raca=?, idade=?, sexo=?, tutorId=?, veterinarioId=? WHERE id=?");
            stmt.setString(1, animal.getNome());
            stmt.setString(2, animal.getEspecie());
            stmt.setString(3, animal.getRaca());
            stmt.setInt(4, animal.getIdade());
            stmt.setString(5, animal.getSexo());
            stmt.setInt(6, animal.getTutorId());
            stmt.setInt(7, animal.getVeterinarioId());
            stmt.setInt(8, animal.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
    // Delete   
    public void delete(Animal animal) {
        PreparedStatement stmt;
        try {
            stmt = connect().prepareStatement("DELETE FROM animal WHERE id = ?");
            stmt.setInt(1, animal.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
