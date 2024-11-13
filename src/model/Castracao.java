package model;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Castracao extends Servico {
    private int id;
    private int servicoId;
    private String tipoCastracao;
    private int idadeAnimalNaCastracao;
    private double pesoAnimalNaCastracao;

    public Castracao(int id, int servicoId, String tipoCastracao, int idadeAnimalNaCastracao, double pesoAnimalNaCastracao) {
        this.id = id;
        this.servicoId = servicoId;
        this.tipoCastracao = tipoCastracao;
        this.idadeAnimalNaCastracao = idadeAnimalNaCastracao;
        this.pesoAnimalNaCastracao = pesoAnimalNaCastracao;
    }

    public Castracao() {
    }
    
    public int getId() {
        return id;
    }
    
    public int getServicoId() {
        return servicoId;
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
}
