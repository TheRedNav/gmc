package br.com.bb.dsc.models;

public class Papel {
    private String id;
    private String nomePapel;
    private  int tipoPapel;
    private String siglaSistema;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomePapel() {
        return nomePapel;
    }

    public void setNomePapel(String nomePapel) {
        this.nomePapel = nomePapel;
    }

    public int getTipoPapel() {
        return tipoPapel;
    }

    public void setTipoPapel(int tipoPapel) {
        this.tipoPapel = tipoPapel;
    }

    public String getSiglaSistema() {
        return siglaSistema;
    }

    public void setSiglaSistema(String siglaSistema) {
        this.siglaSistema = siglaSistema;
    }

}
