package model;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Exame {
    private int id;
    private int animalId;
    private int veterinarioId;
    private String tipoExame;
    private String resultados;

    public Exame(int id, int animalId, int veterinarioId, String tipoExame, String resultados) {
        this.id = id;
        this.animalId = animalId;
        this.veterinarioId = veterinarioId;
        this.tipoExame = tipoExame;
        this.resultados = resultados;
    }

    public Exame() {
    }
    
    public int getId() {
        return id;
    }

    public String getTipoExame() {
        return tipoExame;
    }

    public String getResultados() {
        return resultados;
    }

    public void setResultados(String resultados) {
        this.resultados = resultados;
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
