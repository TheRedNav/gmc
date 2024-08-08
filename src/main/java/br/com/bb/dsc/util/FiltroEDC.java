package br.com.bb.dsc.util;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.opentracing.Traced;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.bb.dsc.enums.MetricasEnum;
import br.com.bb.dsc.models.Facts;
import br.com.bb.dsc.models.Items;
import br.com.bb.dsc.models.Objeto;
import br.com.bb.dsc.models.Papel;

@RequestScoped
@Traced
public class FiltroEDC {

    @Inject
    FormatEDC formatEDC;

    @Inject
	MetricRegistry registroMetricas;

    public List<Papel> getListaPapeis(String json){

        List<Papel> listaPapel = new ArrayList<>();

        try{
            if (json != null) {

                ObjectMapper objectMapper = new ObjectMapper();

                //Acessando o nó principal
                JsonNode rootNode = objectMapper.readTree(json);
                
                //Acessando o nó "hits"
                JsonNode hitsNode = rootNode.get("hits");

                for (JsonNode itemNode : hitsNode) {
                    String id = itemNode.get("id").asText();
                    if(!id.isEmpty()){
                        listaPapel.add(this.formatEDC.getPapel(id));
                    }
                }
            }
        }catch(JsonProcessingException e){
  
            System.err.println("ERRO: Ocorreu um problema no processamento de conversão de JSON para Lista de Papel. MENSAGEM:"+e.getMessage());

            //Incluindo um erro para cada item as metricas
            registroMetricas.counter(MetricasEnum.GMC_INFORMATICA_ELEMENTOS_ERROS.name()).inc();
            
        }
        return listaPapel;
    }

    
    public Objeto getObjeto(String etag, String json){

        Objeto objeto = new Objeto();

        try{

            objeto.setEtag(etag);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(json);

            List<Items> items = new ArrayList<>();
            JsonNode itemsNode = rootNode.get("items");

            if (itemsNode.isArray()) {
                for (JsonNode itemNode : itemsNode) {
                    Items item = new Items();
                    item.setId(itemNode.get("id").asText());
                    
                    List<Facts> facts = new ArrayList<>();
                    JsonNode factsNode = itemNode.get("facts");
                    for (JsonNode factNode : factsNode) {
                        Facts fact = new Facts();

                        fact.setAttributeId(factNode.get("attributeId").asText());
                        fact.setValue(factNode.get("value").asText());
                        fact.setLabel(factNode.get("label").asText());
                        fact.setDescription(factNode.get("description").asText());
                        fact.setProviderId(factNode.get("providerId").asText());
                        fact.setXid(factNode.get("xid").asText());
                        fact.setModifiedBy(factNode.get("modifiedBy").asText());
                        if(factNode.get("projectedFrom") != null){
                            fact.setProjectedFrom(factNode.get("projectedFrom").asText());
                        }
                        fact.setReadOnly(factNode.get("readOnly").asBoolean());
                        
                        facts.add(fact);
                    }
                    item.setFacts(facts);
                    items.add(item);
                }
            }

            objeto.setItems(items);
        }catch(JsonProcessingException e){
  
            System.err.println("ERRO: Ocorreu um problema no processamento de conversão de JSON para Objeto. MENSAGEM:"+e.getMessage());

            //Incluindo um erro para cada item as metricas
            registroMetricas.counter(MetricasEnum.GMC_INFORMATICA_ELEMENTOS_ERROS.name()).inc();
            
        }

        return objeto;
    }

}
