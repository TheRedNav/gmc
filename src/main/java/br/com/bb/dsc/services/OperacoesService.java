package br.com.bb.dsc.services;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.opentracing.Traced;

import br.com.bb.aqm.operacao.listarEntidadesDominiosCriticosV1.bean.resposta.ListaEntidades;
import br.com.bb.atc.operacao.consultarDetalhesPapelPeloCodigoV1.bean.resposta.DadosRespostaConsultarDetalhesPapelPeloCodigo;
import br.com.bb.atc.operacao.listarCodigosPapeisPesquisadosPeloUsuarioV1.bean.resposta.ListaRegistro;
import br.com.bb.dsc.enums.MetricasEnum;
import br.com.bb.dsc.iib.Op2850033;
import br.com.bb.dsc.iib.Op2867915;
import br.com.bb.dsc.iib.Op8846657;
import br.com.bb.dsc.models.Papel;
import br.com.bb.dsc.models.Unidade;
import br.com.bb.dsc.util.FormatEDC;


@RequestScoped
@Traced
public class OperacoesService {

    @Inject
    Op2867915 op2867915;

    @Inject
    Op2850033 op2850033;

    @Inject
    Op8846657 op8846657;

    @Inject
    FormatEDC formatEDC;

    @Inject
	MetricRegistry registroMetricas;

    public Unidade consultarDadosTabela(Papel papel){
        Unidade unidade = null;

        //Informo o nome da tabela para retuperar o codigo do papel
        String codigoPapel = this.consultarCodigoDoPapel(papel);

        //Se houver codigoPapel então consultar os detalhes
        if(codigoPapel != null){
            unidade = this.consultarDetalhesPapel(codigoPapel);
            unidade.setId(papel.getId());
        }

        return unidade;

    }

    //Recupera o código do papel na op2867915
    public String consultarCodigoDoPapel(Papel papel){
        String resposta = null;
        try{
            if(papel.getNomePapel()!= null && papel.getSiglaSistema()!=null && papel.getTipoPapel()!=0){
                List<ListaRegistro> listaRegistro =
                        op2867915.listarCodigoPapel("F8437435", papel.getNomePapel()).getListaRegistro();
                        
                if(!listaRegistro.isEmpty()){
                    for (ListaRegistro registro : listaRegistro) {
                        
                        if (registro != null) {
                            if(
                                registro.getNomePapel().equals(papel.getNomePapel()) &&
                                formatEDC.convertToTipoPapel(registro.getCodigoPapel()) == papel.getTipoPapel() && 
                                registro.getSiglaSistema().equals(papel.getSiglaSistema())){
                                resposta = registro.getCodigoPapel();
                            }
                        }
                    }
                }
            }
        }catch(Exception e){

            //Incluindo erro as metricas
            registroMetricas.counter(MetricasEnum.GMC_INFORMATICA_ELEMENTOS_ERROS.name()).inc();
            System.err.println("ERRO: Ocorreu um problema na operação op2867915v1 ao consultar ID: "+papel.getId()+" MENSAGEM:"+e.getMessage());
            
        }
      
        return resposta;
    }

    //Recupera os detalhes do papel na op2850033
    public Unidade consultarDetalhesPapel(String codigoPapel){

        Unidade unidade = new Unidade();

        try{

            DadosRespostaConsultarDetalhesPapelPeloCodigo consulta = op2850033.consultarPapel(codigoPapel);
            unidade.setUor(consulta.getCodigoUnidadeOrganizacionalGestorPapel());
            unidade.setNome(consulta.getNomeUnidadeOrganizacionalGestorPapel());
            unidade.setCriticidade(this.formatEDC.getCriticidade(consulta.getCodigoCriticidadePapel()));

        }catch(Exception e){

            //Incluindo erro as metricas
            registroMetricas.counter(MetricasEnum.GMC_INFORMATICA_ELEMENTOS_ERROS.name()).inc();
            System.err.println("ERRO: Ocorreu um problema na operação op2850033v1 ao consultar papel: "+codigoPapel+" MENSAGEM:"+e.getMessage());

        }
        
        return unidade;

    }

    public List<ListaRegistro> getListaRegistro(String nomePapel){

        List<ListaRegistro> retornoListaRegistro = new ArrayList<>();
        
        List<ListaRegistro> listaRegistro = op2867915.listarCodigoPapel("F8437435", nomePapel).getListaRegistro();

        if(!listaRegistro.isEmpty()){
            for (ListaRegistro registro : listaRegistro) {
                if (registro != null) {
                    if(registro.getNomePapel().equals(nomePapel)){
                        retornoListaRegistro.add(registro);
                    }
                }
            }
        }

        return retornoListaRegistro;
        
    }


// inicio dados criticos - Julio

    public ArrayList<ListaEntidades> listaCriticidade(int pagina){        
        return op8846657.listarCriticidade(pagina).getListaEntidades();       

    }




}