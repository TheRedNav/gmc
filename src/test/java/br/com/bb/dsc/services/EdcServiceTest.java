package br.com.bb.dsc.services;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.Timer;
import org.eclipse.microprofile.metrics.Timer.Context;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import br.com.bb.dsc.integration.ComunicacaoEDC;
import br.com.bb.dsc.models.Objeto;
import br.com.bb.dsc.models.Papel;
import br.com.bb.dsc.models.Unidade;
import br.com.bb.dsc.util.Factory;
import br.com.bb.dsc.util.FiltroEDC;
import br.com.bb.dsc.util.FormatEDC;
import io.quarkus.test.junit.QuarkusTest;


@QuarkusTest
public class EdcServiceTest {

    @InjectMocks
    EdcService edcService;
    
    @Mock
    MetricRegistry registroMetrica;

    @Mock
    private OperacoesService operacoesService;

    @Mock
    private ComunicacaoEDC comunicacaoEDC;

    @Mock
    private FiltroEDC filtro;

    @Mock
    private FormatEDC formatEDC;

    @Mock
    Context context;

    @Mock
    Timer timer;

    @Mock
    Counter counter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetListaPapel() {
       String jsonMock = "json de exemplo";
       when(comunicacaoEDC.getCatalogo(0)).thenReturn(jsonMock);

       List<Papel> listaPapeisMock = List.of(new Papel(), new Papel());
       when(filtro.getListaPapeis(jsonMock)).thenReturn(listaPapeisMock);

       List<Papel> result = edcService.getListaPapel(0);

       assertEquals(listaPapeisMock, result);

    }

    @Test
    void testGetListaUnidades() {

        String jsonMock = "json de exemplo";
        when(comunicacaoEDC.getCatalogo(0)).thenReturn(jsonMock);

        List<Papel> listaPapeisMock = List.of(new Papel(), new Papel());
        when(filtro.getListaPapeis(jsonMock)).thenReturn(listaPapeisMock);

        Unidade unidadeMock = Factory.getUnidade();
        when(operacoesService.consultarDadosTabela(any(Papel.class))).thenReturn(unidadeMock);

        List<Unidade> result = edcService.getListaUnidades(0);

        assertEquals(2, result.size());
        assertEquals(unidadeMock, result.get(0));

        //Teste nulo
        Unidade unidadeNulaMock = null;
        when(operacoesService.consultarDadosTabela(any(Papel.class))).thenReturn(unidadeNulaMock);

        List<Unidade> resultNulo = edcService.getListaUnidades(0);
        assertEquals(0, resultNulo.size()); 

    }

    @Test
    void testAtualizarPorId() {
        String id = Factory.getUnidade().getId();

        when(registroMetrica.timer(anyString())).thenReturn(timer);
        when(registroMetrica.counter(anyString())).thenReturn(counter);
        
        Papel papelMock = Factory.getPapel();
        when(formatEDC.getPapel(anyString())).thenReturn(papelMock);

        Unidade unidadeMock = Factory.getUnidade();
        when(operacoesService.consultarDadosTabela(eq(papelMock))).thenReturn(unidadeMock);

        Objeto objetoMock = new Objeto();
        objetoMock.setEtag("123456789");
        List<Unidade> groupUnidade = new ArrayList<>();
        groupUnidade.add(unidadeMock);
        when(comunicacaoEDC.atualizarObjeto(groupUnidade)).thenReturn(objetoMock);

        when(registroMetrica.timer(anyString()).time()).thenReturn(context);

        Unidade resultado = edcService.atualizarPorId(id);

        verify(comunicacaoEDC, times(1)).inserirObjeto(objetoMock);
            
        assertEquals(id, resultado.getId());

        verify(timer, times(1)).time();

    }

    @Test
    void testAtualizarTodos() throws InterruptedException {

        when(registroMetrica.timer(anyString())).thenReturn(timer);
        when(registroMetrica.counter(anyString())).thenReturn(counter);
        
        String jsonMock = "teste";
        when(this.comunicacaoEDC.getCatalogo(0)).thenReturn(jsonMock);

        List<Papel> listaPapelMock = List.of(new Papel(), new Papel());
        when(filtro.getListaPapeis(jsonMock)).thenReturn(listaPapelMock);

        Unidade unidadeMock = Factory.getUnidade();
        when(operacoesService.consultarDadosTabela(Factory.getPapel())).thenReturn(unidadeMock);

        when(registroMetrica.timer(anyString()).time()).thenReturn(context);

        edcService.atualizarTodos();

        verify(timer, times(1)).time();
    }

}
