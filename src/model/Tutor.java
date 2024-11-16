package model;

import java.util.List;

public class Tutor {
    private int id;
    private String nome;
    private String endereco;
    private String cpf;
    private String celular;

    public Tutor(int id, String nome, String endereco, String cpf, String celular) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.cpf = cpf;
        this.celular = celular;
    }

    public Tutor() {
    }
    
    public int getId() {
        return id;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCpf() {
        return cpf;
    }
    
    public String getCelular() {
        return celular;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public void setCelular (String celular) {
        this.celular = celular;
    }
    
    @Override
    public String toString() {
        return nome; 
    }
}
