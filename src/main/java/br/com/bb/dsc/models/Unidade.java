package br.com.bb.dsc.models;

public class Unidade {
    private String id;
    private int uor;
    private String nome;
    private String criticidade;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUor() {
        return uor;
    }

    public void setUor(int uor) {
        this.uor = uor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCriticidade() {
        return criticidade;
    }

    public void setCriticidade(String criticidade) {
        this.criticidade = criticidade;
    }

}
