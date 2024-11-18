package model;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Castracao {
    private int id;
    private int animalId;
    private int veterinarioId;
    private String tipoCastracao;
    private int idadeAnimalNaCastracao;
    private double pesoAnimalNaCastracao;

    public Castracao(int id, int animalId, int veterinarioId, String tipoCastracao, int idadeAnimalNaCastracao, double pesoAnimalNaCastracao) {
        this.id = id;
        this.animalId = animalId;
        this.veterinarioId = veterinarioId;
        this.tipoCastracao = tipoCastracao;
        this.idadeAnimalNaCastracao = idadeAnimalNaCastracao;
        this.pesoAnimalNaCastracao = pesoAnimalNaCastracao;
    }

    public Castracao() {
    }
    
    public int getId() {
        return id;
    }
    
    public String getTipoCastracao() {
        return tipoCastracao;
    }

    public int getIdadeAnimalNaCastracao() {
        return idadeAnimalNaCastracao;
    }

    public double getPesoAnimalNaCastracao() {
        return pesoAnimalNaCastracao;
    }
   
    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    public int getVeterinarioId() {
        return veterinarioId;
    }

    public void setVeterinarioId(int veterinarioId) {
        this.veterinarioId = veterinarioId;
    }
}
