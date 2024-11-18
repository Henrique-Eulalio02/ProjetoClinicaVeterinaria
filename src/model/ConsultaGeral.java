package model;

public class ConsultaGeral {
    private int id;
    private int animalId;
    private int veterinarioId;
    private String motivo;
    private String diagnostico;

    public ConsultaGeral(int id, int animalId, int veterinarioId, String motivo, String diagnostico) {
        this.id = id;
        this.animalId = animalId;
        this.veterinarioId = veterinarioId;
        this.motivo = motivo;
        this.diagnostico = diagnostico;
    }

    public ConsultaGeral() {
    }
    
    public int getId() {
        return id;
    }
    
    public String getMotivo() {
        return motivo;
    }

    public String getDiagnostico() {
        return diagnostico;
    }
    
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
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
