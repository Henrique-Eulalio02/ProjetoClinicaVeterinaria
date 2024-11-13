package model;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Servico {
    private int id;
    private int animalId;
    private int veterinarioId;
    private Timestamp data;
    private double valor;
    private double gasto;

    public Servico(int id, int animalId, int veterinarioId, Timestamp data, double valor, double gasto) {
        this.id = id;
        this.animalId = animalId;
        this.veterinarioId = veterinarioId;
        this.data = data;
        this.valor = valor;
        this.gasto = gasto;
    }

    public Servico() {
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getGasto() {
        return gasto;
    }

    public void setGasto(double gasto) {
        this.gasto = gasto;
    }
    
    
}
