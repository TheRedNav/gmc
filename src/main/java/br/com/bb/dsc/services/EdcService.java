package br.com.bb.dsc.services;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.Timer;
import org.eclipse.microprofile.opentracing.Traced;

import br.com.bb.atc.operacao.listarCodigosPapeisPesquisadosPeloUsuarioV1.bean.resposta.ListaRegistro;
import br.com.bb.dsc.enums.MetricasEnum;
import br.com.bb.dsc.integration.ComunicacaoEDC;
import br.com.bb.dsc.models.Objeto;
import br.com.bb.dsc.models.Papel;
import br.com.bb.dsc.models.Unidade;
import br.com.bb.dsc.util.FiltroEDC;
import br.com.bb.dsc.util.FormatEDC;

@RequestScoped
@Traced
public class EdcService {

    @Inject
    private OperacoesService operacoesService;

    @Inject
    private ComunicacaoEDC comunicacaoEDC;

    @Inject
    private FiltroEDC filtro;

    @Inject
    FormatEDC formatEDC;

    @Inject
	MetricRegistry registroMetricas;

    final int TIMESLEEP = 1000;

    public List<Papel> getListaPapel(int pagina) {
        String json = this.comunicacaoEDC.getCatalogo(pagina);
        return this.filtro.getListaPapeis(json);
    }

    public List<Unidade> getListaUnidades(int pagina) {

        List<Papel> listPapel = this.getListaPapel(pagina);

        List<Unidade> listaUnidade = new ArrayList<>();

        for (var papel : listPapel) {
            //Recuperando os 3 valores de cada papel nas operações
            Unidade unidade = this.operacoesService.consultarDadosTabela(papel);
            if(unidade != null){
                listaUnidade.add(unidade);
            }
        }

        return listaUnidade;
    }
    
    public Unidade atualizarPorId(String id){

        //Removendo Metricas para restartar metricas assim salvando sempre os ultimos dados
        registroMetricas.remove(MetricasEnum.GMC_INFORMATICA_TEMPO_EXECUCAO.name());
        registroMetricas.remove(MetricasEnum.GMC_INFORMATICA_ELEMENTOS_TOTAL.name());
        registroMetricas.remove(MetricasEnum.GMC_INFORMATICA_ELEMENTOS_ATUALIZADOS.name());
        registroMetricas.remove(MetricasEnum.GMC_INFORMATICA_ELEMENTOS_ERROS.name());

        //Iniciando metrica que registra o tempo de execução
        Timer.Context contextMetricaTempo = registroMetricas.timer(MetricasEnum.GMC_INFORMATICA_TEMPO_EXECUCAO.name()).time();

        //Formatando os dados do ID
        Papel papel = this.formatEDC.getPapel(id);

        //Recupera a Unidade Consultando nas operações
        Unidade unidade = this.operacoesService.consultarDadosTabela(papel);

        if(unidade != null){

            //Agrupando um unica unidade
            List<Unidade> groupUnidade = new ArrayList<>();
            groupUnidade.add(unidade);

            //Formatando as unidades em um objeto
            Objeto objeto = this.comunicacaoEDC.atualizarObjeto(groupUnidade);

            //Insere o objeto
            this.comunicacaoEDC.inserirObjeto(objeto);

        }
        
        //Total de itens
        System.out.println("INFO: Finalizou, total de elementos: 1");

        //Registrando o total de itens atualizados
        registroMetricas.counter(MetricasEnum.GMC_INFORMATICA_ELEMENTOS_TOTAL.name()).inc();
         
        //Finalizando o processamento e salvando o registro de metrica do tempo
        contextMetricaTempo.stop();

        return unidade;
    }
    
    public void atualizarTodos() throws InterruptedException{

        //Removendo Metricas para restartar metricas assim salvando sempre os ultimos dados
        registroMetricas.remove(MetricasEnum.GMC_INFORMATICA_TEMPO_EXECUCAO.name());
        registroMetricas.remove(MetricasEnum.GMC_INFORMATICA_ELEMENTOS_TOTAL.name());
        registroMetricas.remove(MetricasEnum.GMC_INFORMATICA_ELEMENTOS_ATUALIZADOS.name());
        registroMetricas.remove(MetricasEnum.GMC_INFORMATICA_ELEMENTOS_ERROS.name());

        //Iniciando metrica que registra o tempo de execução
        Timer.Context contextMetricaTempo = registroMetricas.timer(MetricasEnum.GMC_INFORMATICA_TEMPO_EXECUCAO.name()).time();

        int pagina = 0;
        
        boolean existItens = true;

        int countUptade = 0;

        while(existItens){

            //Recuperando Json com os dados do EDC
            String json = this.comunicacaoEDC.getCatalogo(pagina);

            //Filtrando o Json para recuperar a lista de papeis
            List<Papel> lp = this.filtro.getListaPapeis(json);

            List<Unidade> listaUnidade = new ArrayList<>();

            if(!lp.isEmpty()){
                for (Papel papel : lp) {

                    //Recuperando os 3 valores de cada papel nas operações
                    Unidade unidade = this.operacoesService.consultarDadosTabela(papel);

                    //Adicionando as unidades não nulas na lista
                    if(unidade != null) {
                        listaUnidade.add(unidade);
                    }
                }
                
                //Inserindo as unidades do EDC
                this.inserirObjeto(listaUnidade);
                
                countUptade = countUptade + listaUnidade.size();

                listaUnidade.clear();
                pagina++;
            }else{
                pagina = 0;
                listaUnidade.clear();
                existItens = false;
                break;
            }
        }

        //Total de itens
        System.out.println("INFO: Finalizou, total de elementos: "+countUptade);
        
        //Registrando o total de itens atualizados
        registroMetricas.counter(MetricasEnum.GMC_INFORMATICA_ELEMENTOS_TOTAL.name()).inc(countUptade);
        
        //Registrando historico de metricas
        registroMetricas.counter(MetricasEnum.GMC_INFORMATICA_HISTORICO_TOTAL.name()).inc(countUptade);
        registroMetricas.counter(MetricasEnum.GMC_INFORMATICA_HISTORICO_ATUALIZADOS.name()).inc(registroMetricas.counter(MetricasEnum.GMC_INFORMATICA_ELEMENTOS_ATUALIZADOS.name()).getCount());
        registroMetricas.counter(MetricasEnum.GMC_INFORMATICA_HISTORICO_ERROS.name()).inc(registroMetricas.counter(MetricasEnum.GMC_INFORMATICA_ELEMENTOS_ERROS.name()).getCount());

        //Finalizando o processamento e salvando o registro metrica, salvando o tempo de excução
        contextMetricaTempo.stop();
    }

    private void inserirObjeto(List<Unidade> listaUnidade) throws InterruptedException{

        final int GROUP_SIZE = 5;
        int dataSize = listaUnidade.size();

        for (int startIndex = 0; startIndex < dataSize; startIndex += GROUP_SIZE) {
            int endIndex = Math.min(startIndex + GROUP_SIZE, dataSize);

            List<Unidade> groupUnidade = listaUnidade.subList(startIndex, endIndex);

            //Atualiza o objeto
            Objeto objeto = this.comunicacaoEDC.atualizarObjeto(groupUnidade);
            Thread.sleep(TIMESLEEP);

            //Insere o objeto no EDC
            this.comunicacaoEDC.inserirObjeto(objeto);
            Thread.sleep(TIMESLEEP);
        }
    }

    //Operações
    public List<ListaRegistro> getPapel(String nomePapel){
       return operacoesService.getListaRegistro(nomePapel);
    }

    public Unidade getDetalhePapel(String codigoPapel){
        return operacoesService.consultarDetalhesPapel(codigoPapel);
    }

    public void testesDesenv(int tempoExecucao) throws InterruptedException {

        //Metrica que registra o tempo de execução
        registroMetricas.remove(MetricasEnum.GMC_INFORMATICA_TEMPO_EXECUCAO.name());
        Timer.Context contextMetrica = registroMetricas.timer(MetricasEnum.GMC_INFORMATICA_TEMPO_EXECUCAO.name()).time();

        Thread.sleep((long) (tempoExecucao * 1000));

        //Definindo o final do processamento
        contextMetrica.stop();

    }

}
