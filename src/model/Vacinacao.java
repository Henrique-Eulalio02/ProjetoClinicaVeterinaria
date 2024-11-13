package model;

import java.sql.Timestamp;

public class Vacinacao extends Servico {
    private int id;
    private int servicoId;
    private int vacinaId;
    private Timestamp dataProximaDose;

    public Vacinacao(int id, int servicoId, int vacinaId, Timestamp dataProximaDose) {
        this.id = id;
        this.servicoId = servicoId;
        this.vacinaId = vacinaId;
        this.dataProximaDose = dataProximaDose;
    }

    public Vacinacao() {
    }
    
    public int getId() {
        return id;
    }
    
    public int getServicoId() {
        return servicoId;
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
}
