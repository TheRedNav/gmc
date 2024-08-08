package br.com.bb.dsc.iib;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import br.com.bb.atc.operacao.consultarDetalhesPapelPeloCodigoV1.bean.requisicao.DadosRequisicaoConsultarDetalhesPapelPeloCodigo;
import br.com.bb.atc.operacao.consultarDetalhesPapelPeloCodigoV1.bean.resposta.DadosRespostaConsultarDetalhesPapelPeloCodigo;
import br.com.bb.dsc.integration.ConsumidorCurio;
import br.com.bb.dsc.integration.IntegracaoIIB;

@RequestScoped
@Traced
@Path("Op2850033v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Op2850033 {

    @Inject
    @RestClient
    ConsumidorCurio consumidor;

    @IntegracaoIIB
public DadosRespostaConsultarDetalhesPapelPeloCodigo consultarPapel(String codigoPapel ){
        DadosRequisicaoConsultarDetalhesPapelPeloCodigo requisicao = new DadosRequisicaoConsultarDetalhesPapelPeloCodigo();
        requisicao.setCodigoPapel(codigoPapel);
        DadosRespostaConsultarDetalhesPapelPeloCodigo resposta = consumidor.executarOp2850033v1(requisicao);
        return resposta;
    }

}
