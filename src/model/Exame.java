package model;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Exame extends Servico {
    private int id;
    private int servicoId;
    private String tipoExame;
    private String resultados;

    public Exame(int id, int servicoId, String tipoExame, String resultados) {
        this.id = id;
        this.servicoId = servicoId;
        this.tipoExame = tipoExame;
        this.resultados = resultados;
    }

    public Exame() {
    }
    
    public int getId() {
        return id;
    }
    
    public int getServicoId() {
        return servicoId;
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
}
