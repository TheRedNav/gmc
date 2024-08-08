package br.com.bb.dsc.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.enterprise.context.RequestScoped;

import org.eclipse.microprofile.opentracing.Traced;

import br.com.bb.dsc.models.Papel;

@RequestScoped
@Traced
public class FormatEDC {
    
    private final int HIV = 212;
    private final int DB2 = 242;

    public Papel getPapel(String id){

        Papel papel = new Papel();

        papel.setId(id);

        //Recuperando Tipo papel
        papel.setTipoPapel(this.getTipoPapel(id));

        //Recuperar a Sigla do Sistema
        papel.setSiglaSistema(this.getSiglaSistema(id));

        //Recuperar o Nome do Papel
        papel.setNomePapel(this.getNomePepel(id));

        return papel;

    }

    //Converte o nome de papel em tipo 212 ou 242
    public int convertToTipoPapel(String nomePapel){

        if(nomePapel.startsWith("BG#")){
            return this.HIV;
        }else
        if(nomePapel.startsWith("#BI")){
            return this.DB2;
        }else{
            return 0;
        }

    }

    public String getCriticidade(String criticidade){

        String valor = "";

        switch(criticidade){

            case "$90":
                valor = "A CLASSIFICAR"; 
            break;

            case "$40":
                valor = "#CONFIDENCIAL"; 
            break;

            case "$30":
                valor = "#INTERNA"; 
            break;
            
            case "$20":
                valor = "#INTERNA"; 
            break;
            
            case "$10":
                valor = "#PUBLICA"; 
            break;
            
            default:
                valor = "";
            break;
        
        }
        return valor;

    }
    
    public String getUrl(String id){
        return id.replaceAll(" ", "%20");
    }

    private int getTipoPapel(String input){

        String regex= "^(?:[^/]*/){3}(\\w{3})";

        int result = 0;

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            if ("DB2".equals(matcher.group(1))) {
                result = this.DB2;
            } else if ("HIV".equals(matcher.group(1).toUpperCase())) {
                result = this.HIV;
            }
        }

        return result;
    }

    private String getSiglaSistema(String input){
        
        String regex = "";
        String result = "";
        
        if(this.getTipoPapel(input)==this.DB2){
            regex = "://[^/]+/[^/]{0,3}([^/]{0,3})";
        }
        
        if(this.getTipoPapel(input)==this.HIV){
            regex = "/hive_([^/]{0,3})/";
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            result = matcher.group(1).toUpperCase();
        }
        
       
        
        return result;
    }

    private String getNomePepel(String input){

        String result = "";

        String regex= ".*/([^/]+)$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            if(this.getTipoPapel(input)==this.DB2){
                result = matcher.group(1);
            }else{
                result = "PROD:hive_"+this.getSiglaSistema(input).toLowerCase()+"."+matcher.group(1);
            }
            
        }
        return result;
    }
}
