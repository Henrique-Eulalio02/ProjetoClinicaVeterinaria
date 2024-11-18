package model;

import java.sql.Timestamp;

public class Vacinacao {
    private int id;
    private int animalId;
    private int veterinarioId;
    private int vacinaId;
    private Timestamp dataProximaDose;
    private Timestamp data;

    public Vacinacao(int id, int animalId, int veterinarioId, int vacinaId, Timestamp data, Timestamp dataProximaDose) {
        this.id = id;
        this.animalId = animalId;
        this.veterinarioId = veterinarioId;
        this.vacinaId = vacinaId;
        this.data = data;
        this.dataProximaDose = dataProximaDose;
    }

    public Vacinacao() {
    }
    
    public int getId() {
        return id;
    }
    
    public int getVacinaId() {
        return vacinaId;
    }

    public Timestamp getDataProximaDose() {
        return dataProximaDose;
    }

    public void setDataProximaDose(Timestamp dataProximaDose) {
        this.dataProximaDose = dataProximaDose;
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
}
