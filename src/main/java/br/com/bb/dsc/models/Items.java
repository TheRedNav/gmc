package br.com.bb.dsc.models;
import java.util.List;

public class Items {
    
    private String id;
    private List<Facts> facts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Facts> getFacts() {
        return facts;
    }

    public void setFacts(List<Facts> facts) {
        this.facts = facts;
    }
}
