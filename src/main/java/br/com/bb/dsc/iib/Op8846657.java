package br.com.bb.dsc.iib;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import br.com.bb.dsc.integration.ConsumidorCurio;
import br.com.bb.dsc.integration.IntegracaoIIB;

import br.com.bb.aqm.operacao.listarEntidadesDominiosCriticosV1.bean.requisicao.DadosRequisicaoListarEntidadesDominiosCriticos;
import br.com.bb.aqm.operacao.listarEntidadesDominiosCriticosV1.bean.resposta.DadosRespostaListarEntidadesDominiosCriticos;



@RequestScoped
@Traced
@Path("Op8846657v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Op8846657 {

    @Inject
    @RestClient
    ConsumidorCurio consumidor;

    @IntegracaoIIB
    public DadosRespostaListarEntidadesDominiosCriticos listarCriticidade (int numeroPagina){
        DadosRequisicaoListarEntidadesDominiosCriticos requisicao = new DadosRequisicaoListarEntidadesDominiosCriticos();
        requisicao.setNumeroPagina(numeroPagina);
        DadosRespostaListarEntidadesDominiosCriticos resposta = consumidor.executarOp8846657v1(requisicao);
        return resposta;

    }

}