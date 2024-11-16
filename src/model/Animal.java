package model;

import java.util.List;

public class Animal {
   private int id;
   private String nome;
   private String especie;
   private String raca;
   private int idade;
   private String sexo;
   private int tutorId;
   private int veterinarioId;

    public Animal(int id, String nome, String especie, String raca, int idade, String sexo, int tutorId, int veterinarioId) {
        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.idade = idade;
        this.sexo = sexo;
        this.tutorId = tutorId;
        this.veterinarioId = veterinarioId;
    }

    public Animal() {
    }
    
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEspecie() {
        return especie;
    }

    public String getRaca() {
        return raca;
    }

    public int getIdade() {
        return idade;
    }

    public String getSexo() {
        return sexo;
    }

    public int getTutorId() {
        return tutorId;
    }
    
    public int getVeterinarioId() {
        return veterinarioId;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setTutorId(int tutorId) {
        this.tutorId = tutorId;
    }
    
    public void setVeterinarioId(int veterinarioId) {
        this.veterinarioId = veterinarioId;
    }

    @Override
    public String toString() {
        return nome; //
    }
}  
