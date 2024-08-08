package br.com.bb.dsc.integration;

import br.com.bb.aqm.operacao.listarEntidadesDominiosCriticosV1.bean.requisicao.DadosRequisicaoListarEntidadesDominiosCriticos;
import br.com.bb.aqm.operacao.listarEntidadesDominiosCriticosV1.bean.resposta.DadosRespostaListarEntidadesDominiosCriticos;
import br.com.bb.atc.operacao.consultarDetalhesPapelPeloCodigoV1.bean.requisicao.DadosRequisicaoConsultarDetalhesPapelPeloCodigo;
import br.com.bb.atc.operacao.consultarDetalhesPapelPeloCodigoV1.bean.resposta.DadosRespostaConsultarDetalhesPapelPeloCodigo;
import br.com.bb.atc.operacao.listarCodigosPapeisPesquisadosPeloUsuarioV1.bean.requisicao.DadosRequisicaoListarCodigosPapeisPesquisadosPeloUsuario;
import br.com.bb.atc.operacao.listarCodigosPapeisPesquisadosPeloUsuarioV1.bean.resposta.DadosRespostaListarCodigosPapeisPesquisadosPeloUsuario;
import br.com.bb.dev.erros.curio.CurioExceptionMapper;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;



@ApplicationScoped
@RegisterRestClient(configKey = "curio-host")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@RegisterProvider(CurioExceptionMapper.class)
public interface ConsumidorCurio {

  @POST
  @Path("op2867915v1")
  @IntegracaoIIB
  DadosRespostaListarCodigosPapeisPesquisadosPeloUsuario executarOp2867915v1(
  DadosRequisicaoListarCodigosPapeisPesquisadosPeloUsuario requisicao);

  @POST
  @Path("op2850033v1")
  @IntegracaoIIB
  DadosRespostaConsultarDetalhesPapelPeloCodigo executarOp2850033v1(
    DadosRequisicaoConsultarDetalhesPapelPeloCodigo requisicao);
  
  @POST
  @Path("op8846657v1")
  @IntegracaoIIB
  DadosRespostaListarEntidadesDominiosCriticos executarOp8846657v1(
    DadosRequisicaoListarEntidadesDominiosCriticos requisicao);
  

}
