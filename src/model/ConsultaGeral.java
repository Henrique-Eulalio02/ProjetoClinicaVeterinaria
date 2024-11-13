package model;

public class ConsultaGeral extends Servico {
    private int id;
    private int servicoId;
    private String motivo;
    private String diagnostico;

    public ConsultaGeral(int id, int servicoId, String motivo, String diagnostico) {
        this.id = id;
        this.servicoId = servicoId;
        this.motivo = motivo;
        this.diagnostico = diagnostico;
    }

    public ConsultaGeral() {
    }
    
    public int getServicoId() {
        return servicoId;
    }
    
    public String getMotivo() {
        return motivo;
    }

    public String getDiagnostico() {
        return diagnostico;
    }
}
