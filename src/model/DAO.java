package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DAO {
    private static final String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/clinicaVeterinaria";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    
    private static Connection connection;
    private static Properties properties;
    
    private static Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            properties.setProperty("user", USERNAME);
            properties.setProperty("password", PASSWORD);
        }
        
        System.out.println("Propriedades definidas com sucesso.");
        
        return properties;
    }
    
    public static Connection connect() {
        if (connection == null) {
            try {
                Class.forName(DATABASE_DRIVER);
                connection = DriverManager.getConnection(DATABASE_URL, getProperties());
                System.out.println("Conexao realizada com sucesso.");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        
        return connection;
    }
   
    public static void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Conexao fechada com sucesso.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
     protected static ResultSet getResultSet(String query) {
        Statement s;
        ResultSet rs = null;
        try {
            s = (Statement) connection.createStatement();
            rs = s.executeQuery(query);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        
        return rs;
    }

    protected static int executeUpdate(PreparedStatement queryStatement) throws SQLException {
        int update;
        update = queryStatement.executeUpdate();
        return update;
    }

    protected static int lastId(String tableName, String primaryKey) {
        Statement s;
        int lastId = -1;
        try {
            s = (Statement) connection.createStatement();
            ResultSet rs = s.executeQuery("SELECT MAX(" + primaryKey + ") AS id FROM " + tableName);
            if (rs.next()) {
                lastId = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        
        return lastId;
    }
    
        protected static final boolean createTable() {
        try {
            PreparedStatement stm;

            // Tabela veterinario
            stm = DAO.connect().prepareStatement("CREATE TABLE IF NOT EXISTS veterinario ( \n"
                    +  "id INT AUTO_INCREMENT PRIMARY KEY, \n"
                    +  "nome VARCHAR(50), \n"
                    +  "endereco VARCHAR(100), \n"
                    +  "cpf VARCHAR(50), \n" 
                    +  "celular VARCHAR(50), \n"
                    +  "crmv VARCHAR(50) \n"
                    +  ");");
            executeUpdate(stm);

            // Tabela tutor
            stm = DAO.connect().prepareStatement("CREATE TABLE IF NOT EXISTS tutor ( \n"
                    +  "id INT AUTO_INCREMENT PRIMARY KEY, \n"
                    +  "nome VARCHAR(50), \n"
                    +  "endereco VARCHAR(100), \n"
                    +  "cpf VARCHAR(50), \n" 
                    +  "celular VARCHAR(50) \n"
                    +  ");");
            executeUpdate(stm);

            // Tabela animal
            stm = DAO.connect().prepareStatement("CREATE TABLE IF NOT EXISTS animal ( \n"
                    +  "id INT AUTO_INCREMENT PRIMARY KEY, \n"
                    +  "nome VARCHAR(100), \n"
                    +  "especie VARCHAR(50), \n"
                    +  "raca VARCHAR(50), \n"
                    +  "idade INT, \n"
                    +  "sexo VARCHAR(40), \n"
                    +  "tutorId INT, \n"
                    +  "veterinarioId INT, \n"
                    +  "FOREIGN KEY (veterinarioId) REFERENCES veterinario(id), \n"
                    +  "FOREIGN KEY (tutorId) REFERENCES tutor(id) \n"
                    +  ");");
            executeUpdate(stm);

            // Tabela vacina
            stm = DAO.connect().prepareStatement("CREATE TABLE IF NOT EXISTS vacina ( \n"
                    +  "id INT AUTO_INCREMENT PRIMARY KEY, \n"
                    +  "nome VARCHAR(100), \n"
                    +  "descricao VARCHAR(100), \n"
                    +  "marca VARCHAR(50), \n"
                    +  "quantidadeEstoque INT \n"
                    +  ");");
            executeUpdate(stm);

            // Tabela consulta
            stm = DAO.connect().prepareStatement("CREATE TABLE IF NOT EXISTS consulta ( \n"
                    +    "id INT AUTO_INCREMENT PRIMARY KEY, \n"
                    +    "animalId INT, \n"
                    +    "veterinarioId INT, \n"
                    +    "motivo VARCHAR(255), \n"
                    +    "diagnostico VARCHAR(255), \n"
                    +    "FOREIGN KEY (animalId) REFERENCES animal(id), \n"
                    +    "FOREIGN KEY (veterinarioId) REFERENCES veterinario(id) \n"
                    +    ");");
            executeUpdate(stm);

            // Tabela vacinacao
            stm = DAO.connect().prepareStatement("CREATE TABLE IF NOT EXISTS vacinacao ( \n"
                    +    "id INT AUTO_INCREMENT PRIMARY KEY, \n"
                    +    "animalId INT, \n"
                    +    "veterinarioId INT, \n"
                    +    "vacinaId INT, \n"
                    +    "dataProximaDose TIMESTAMP, \n"
                    +    "FOREIGN KEY (vacinaId) REFERENCES vacina(id), \n"
                    +    "FOREIGN KEY (animalId) REFERENCES animal(id), \n"
                    +    "FOREIGN KEY (veterinarioId) REFERENCES veterinario(id) \n"
                    +    ");");
            executeUpdate(stm);

            // Tabela castracoes
            stm = DAO.connect().prepareStatement("CREATE TABLE IF NOT EXISTS castracoes ( \n"
                    +   "id INT AUTO_INCREMENT PRIMARY KEY, \n"
                    +   "animalId INT, \n"
                    +   "veterinarioId INT, \n"
                    +   "tipoCastracao VARCHAR(100), \n"
                    +   "idadeAnimalNaCastracao INT, \n"
                    +   "pesoAnimalNaCastracao FLOAT, \n"
                    +   "FOREIGN KEY (animalId) REFERENCES animal(id), \n"
                    +   "FOREIGN KEY (veterinarioId) REFERENCES veterinario(id) \n"
                    +   ");");
            executeUpdate(stm);

            // Tabela exame
            stm = DAO.connect().prepareStatement("CREATE TABLE IF NOT EXISTS exame ( \n"
                    +   "id INT AUTO_INCREMENT PRIMARY KEY, \n"
                    +   "animalId INT, \n"
                    +   "veterinarioId INT, \n"
                    +   "tipoExame VARCHAR(100), \n"
                    +   "resultados TEXT, \n"
                    +   "data TIMESTAMP, \n"
                    +   "servicoId INT, \n"
                    +   "FOREIGN KEY (animalId) REFERENCES animal(id), \n"
                    +   "FOREIGN KEY (veterinarioId) REFERENCES veterinario(id) \n"
                    +   ");");
            executeUpdate(stm);

            return true;
        } catch (SQLException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e);
        }

        return false;
    }
}
