package br.com.bb.dsc.integration;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.opentracing.Traced;

import com.google.gson.Gson;

import br.com.bb.dsc.enums.MetricasEnum;
import br.com.bb.dsc.models.Facts;
import br.com.bb.dsc.models.Objeto;
import br.com.bb.dsc.models.Unidade;
import br.com.bb.dsc.util.FiltroEDC;
import br.com.bb.dsc.util.FormatEDC;

@RequestScoped
@Traced
public class ComunicacaoEDC {

    @Inject
    FiltroEDC filtroEDC;

    @Inject
    FormatEDC formatEDC;

    @Inject
	MetricRegistry registroMetricas;

    private final int PAGESIZE = 500;

    private final String username = System.getenv("USERNAME");
    private final String password = System.getenv("PASSWORD");

    public String getCatalogo(int pagina){
    	
        registroMetricas.counter(MetricasEnum.GMC_INFORMATICA_ELEMENTOS_TOTAL.name()).inc(0);

        int offset = this.PAGESIZE*pagina;

        StringBuilder response = new StringBuilder();

        String link = System.getenv("URL_EDC");

        try{

            String params = "/access/2/catalog/data/search"
            +"?defaultFacets=true"
            +"&disableSemanticSearch=false"
            +"&enableLegacySearch=false"
            +"&facet=false"
            +"&highlight=false"
            +"&includeRefObjects=false"
            +"&offset="+offset
            +"&pageSize="+PAGESIZE
            +"&q=core.classType%3A(%22com.infa.ldm.relational.Table%22)";

            String credentials = username + ":" + password;
            String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

            URL url = new URL(link+params);

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            connection.setRequestProperty("Authorization", "Basic " + encodedCredentials);

            connection.setRequestMethod("GET");

            try(BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
                    
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }

        }catch(IOException e){
            
            System.err.println("ERRO: Ocorreu um problema ao recuperar dados do EDC-Informatica página: \""+pagina+"\" MENSAGEM:"+e.getMessage());

            //Incluindo um erro para cada item as metricas
            registroMetricas.counter(MetricasEnum.GMC_INFORMATICA_ELEMENTOS_ERROS.name()).inc();
        }

        return response.toString();
    }

    public void inserirObjeto(Objeto objeto){

        try{
        	
            registroMetricas.counter(MetricasEnum.GMC_INFORMATICA_ELEMENTOS_TOTAL.name()).inc(0);
        	
            String link = System.getenv("URL_EDC");

            String etag = objeto.getEtag();
            String params = "/access/2/catalog/data/objects";
            String requestBody = new Gson().toJson(objeto);

            String credentials = username + ":" + password;
            String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

            URL url = new URL(link+params);

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            connection.setRequestMethod("PUT");

            connection.setRequestProperty("Authorization", "Basic " + encodedCredentials);
            connection.setRequestProperty("If-Match", etag);
            connection.setRequestProperty("Content-Type", "application/json");

            connection.setDoOutput(true);

            // Escrever os dados no corpo da requisição
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            //Realizando o chamando da requisição
            connection.getResponseCode();

            //Registrando nas metricas os dados que funcionaram
            for (var item : objeto.getItems()) {
                System.out.println("INFO: Atualizou: "+item.getId());
                //Incluindo atualizado para cada item nas metricas
                registroMetricas.counter(MetricasEnum.GMC_INFORMATICA_ELEMENTOS_ATUALIZADOS.name()).inc();
            }

        }catch(IOException e){
            System.err.println("ERRO: Ocorreu um problema inserir dados no EDC-Informatica:");
            for (var item : objeto.getItems()) {
                System.err.println("ID: "+item.getId());
                //Incluindo um erro para cada item as metricas
                registroMetricas.counter(MetricasEnum.GMC_INFORMATICA_ELEMENTOS_ERROS.name()).inc();
            }
            System.err.println("MENSAGEM:"+e.getMessage());
        }

    }

    public Objeto getObjeto(List<Unidade> listaUnidade){
    	
        registroMetricas.counter(MetricasEnum.GMC_INFORMATICA_ELEMENTOS_TOTAL.name()).inc(0);

        String etag = "";

        StringBuilder response = new StringBuilder();

        Objeto objeto = new Objeto();

        try{

            String link = System.getenv("URL_EDC");

            StringBuilder params = new StringBuilder();

            params.append("/access/2/catalog/data/objects"
                            +"?includeDstLinks=false"
                            +"&includeRefObjects=false"
                            +"&includeSrcLinks=false"
                            +"&offset=0"
                            +"&pageSize="+listaUnidade.size());

            for (var und : listaUnidade) {
                params.append("&id=").append(formatEDC.getUrl(und.getId()));
            }

            String credentials = username + ":" + password;
            String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());


            URL url = new URL(link+params);

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            connection.setRequestProperty("Authorization", "Basic " + encodedCredentials);

            connection.setRequestMethod("GET");

            //ETag
            etag = connection.getHeaderField("ETag");
            // Obter a dados resposta
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
                
                objeto = this.filtroEDC.getObjeto(etag, response.toString());
            }
            
        }catch(IOException e){
            System.err.println("ERRO: Ocorreu um problema ao recuperar dados do EDC-Informatica:");
            for (var und : listaUnidade) {
                System.err.println("ID: "+und.getId());
                 //Incluindo um erro para cada item as metricas
                registroMetricas.counter(MetricasEnum.GMC_INFORMATICA_ELEMENTOS_ERROS.name()).inc();
            }
            System.err.println("MENSAGEM:"+e.getMessage());
        }

        return objeto;
        
    }

    //Cria um objeto com um lista atualizada de acordo com a lista da Unidade
    public Objeto atualizarObjeto(List<Unidade> listaUnidade){

        //Recupera os dados do Objeto
        Objeto objeto = this.getObjeto(listaUnidade);

        //Atualiza com o da lista da Unidades
        for (var und : listaUnidade) {
            for (var items : objeto.getItems()) {
                if(items.getId().equals(und.getId())){
                    Facts factsUOR = new Facts();
                    factsUOR.setAttributeId(System.getenv("ID_CODIGO_UOR"));
                    factsUOR.setValue(und.getUor()+"");
                    items.getFacts().add(factsUOR);

                    Facts factsNome = new Facts();
                    factsNome.setAttributeId(System.getenv("ID_NOME_UOR"));
                    factsNome.setValue(und.getNome());
                    items.getFacts().add(factsNome);

                    Facts factsCriticidade = new Facts();
                    factsCriticidade.setAttributeId(System.getenv("ID_CRITICIDADE"));
                    factsCriticidade.setValue(und.getCriticidade());
                    items.getFacts().add(factsCriticidade);
                }
            }
        }
        return objeto;
    }

}
