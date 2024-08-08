package br.com.bb.dsc.iib;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import br.com.bb.atc.operacao.listarCodigosPapeisPesquisadosPeloUsuarioV1.bean.requisicao.DadosRequisicaoListarCodigosPapeisPesquisadosPeloUsuario;
import br.com.bb.atc.operacao.listarCodigosPapeisPesquisadosPeloUsuarioV1.bean.resposta.DadosRespostaListarCodigosPapeisPesquisadosPeloUsuario;
import br.com.bb.dsc.integration.ConsumidorCurio;
import br.com.bb.dsc.integration.IntegracaoIIB;


@RequestScoped
@Traced
@Path("Op2867915v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Op2867915 {

    @Inject
    @RestClient
    ConsumidorCurio consumidor;

    @IntegracaoIIB
    public DadosRespostaListarCodigosPapeisPesquisadosPeloUsuario listarCodigoPapel(String codigoIdentificacaoDigital, String textoPesquisa) {
        DadosRequisicaoListarCodigosPapeisPesquisadosPeloUsuario requisicao = new DadosRequisicaoListarCodigosPapeisPesquisadosPeloUsuario();
        requisicao.setCodigoIdentificacaoDigital(codigoIdentificacaoDigital);
        requisicao.setTextoPesquisa(textoPesquisa);
        DadosRespostaListarCodigosPapeisPesquisadosPeloUsuario resposta = consumidor.executarOp2867915v1(requisicao);
        return resposta;
    }

}