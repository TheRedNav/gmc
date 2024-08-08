
// package br.com.bb.dsc.scheduler;

// import javax.enterprise.context.ApplicationScoped;

// import org.eclipse.microprofile.opentracing.Traced;

// @ApplicationScoped
// @Traced
// public class MetricaScheduler {
	
	// @Inject
	// RegistroMetrica publicar;

	// @Scheduled(every = "5s", concurrentExecution = SKIP)
	// public void executaMetricaTempoExecucaoLista() throws Exception { 
	// 	publicar.histograma("GMC_INFORMATICA_TEMPO_EXECUCAO_AO_OBTER_LISTA_UNIDADES", "0", "GMC_INFORMATICA_TEMPO_EXECUCAO_AO_OBTER_LISTA_UNIDADES", "GMC_INFORMATICA_TEMPO_EXECUCAO_AO_OBTER_LISTA_UNIDADES", "Tempo de execucao do processo", 0);
	// }
	// @Scheduled(every = "5s", concurrentExecution = SKIP)
	// public void executaMetricaAtualizadosId() throws Exception { 
	// 	publicar.histograma("GMC_INFORMATICA_QTDE_ELEMENTOS_ATUALIZADOS_AO_ATUALIZAR_POR_ID", "0", "GMC_INFORMATICA_QTDE_ELEMENTOS_ATUALIZADOS_AO_ATUALIZAR_POR_ID", "GMC_INFORMATICA_QTDE_ELEMENTOS_ATUALIZADOS_AO_ATUALIZAR_POR_ID", "Quantidade de registros atualizados", 1);
	// }
	// @Scheduled(every = "5s", concurrentExecution = SKIP)
	// public void executaMetricaTempoExecucaoAtualizaTodos() throws Exception { publicar.histograma("GMC_INFORMATICA_TEMPO_EXECUCAO_PROCESO_AO_ATUALIZAR_POR_ID", "0", "GMC_INFORMATICA_TEMPO_EXECUCAO_PROCESO_AO_ATUALIZAR_POR_ID", "GMC_INFORMATICA_TEMPO_EXECUCAO_PROCESO_AO_ATUALIZAR_POR_ID", "Tempo de execucao do processo", 0);
    // 	publicar.histograma("GMC_INFORMATICA_TEMPO_EXECUCAO_PROCESO_AO_ATUALIZAR_TODOS", "0", "GMC_INFORMATICA_TEMPO_EXECUCAO_PROCESO_AO_ATUALIZAR_TODOS", "GMC_INFORMATICA_TEMPO_EXECUCAO_PROCESO_AO_ATUALIZAR_TODOS", "Tempo de execucao do processo", 0);
	// }
	// @Scheduled(every = "5s", concurrentExecution = SKIP)
	// public void executaMetricaQtdeAtualizadosTodos() throws Exception {
	// 	publicar.histograma("GMC_INFORMATICA_QTDE_ELEMENTOS_ATUALIZADOS_AO_ATUALIZAR_TODOS", "0", "GMC_INFORMATICA_QTDE_ELEMENTOS_ATUALIZADOS_AO_ATUALIZAR_TODOS", "GMC_INFORMATICA_QTDE_ELEMENTOS_ATUALIZADOS_AO_ATUALIZAR_TODOS", "Quantidade de registros atualizados", 0);
	// }
	// @Scheduled(every = "5s", concurrentExecution = SKIP)
	// public void executaMetricaQtdeElementosInderidos() throws Exception { 
	// 	publicar.histograma("GMC_INFORMATICA_QTDE_ELEMENTOS_INSERIDOS_OBJETO", "0", "GMC_INFORMATICA_QTDE_ELEMENTOS_INSERIDOS_OBJETO", "GMC_INFORMATICA_QTDE_ELEMENTOS_INSERIDOS_OBJETO", "Quantidade de registros novos inseridos", 0);
	// }
// }
