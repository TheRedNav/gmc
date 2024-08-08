package br.com.bb.dsc.rest;
import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import br.com.bb.atc.operacao.listarCodigosPapeisPesquisadosPeloUsuarioV1.bean.resposta.ListaRegistro;
import br.com.bb.dsc.models.Papel;
import br.com.bb.dsc.models.Unidade;
import br.com.bb.dsc.services.EdcService;
import br.com.bb.dsc.services.OperacoesService;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class EdcRestTest {

    @InjectMocks
    EdcRest edcRest;

    @Mock
    OperacoesService operacoesService;

    @Mock
    EdcService edcService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAtualizarTodos() throws IOException, InterruptedException {
        
        doNothing().when(edcService).atualizarTodos();

        Response response = edcRest.atualizarTodos();

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        verify(edcService, times(1)).atualizarTodos(); 
    }

    @Test
    void testAtualizarPorId() throws IOException {
        String idsMock = "id1";

        Unidade unidadeMock = new Unidade();
        
        when(edcService.atualizarPorId(idsMock)).thenReturn(unidadeMock);

        Response response = edcRest.atualizarPorId(idsMock);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetBrutos() throws IOException {

        List<Papel> listaPapeisMock = List.of(new Papel(), new Papel());

        when(edcService.getListaPapel(0)).thenReturn(listaPapeisMock);

        Response response = edcRest.getBrutos(0);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

    }

    @Test
    public void testGetTratados() throws IOException {

        List<Papel> listaPapeisMock = List.of(new Papel(), new Papel());
        Unidade unidadeMock = new Unidade();

        when(edcService.getListaPapel(0)).thenReturn(listaPapeisMock);

        when(operacoesService.consultarDadosTabela(any(Papel.class))).thenReturn(unidadeMock);
        
        Response response = edcRest.getTratados(0);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

    }

    @Test
    public void testGetCodigoPapel(){

        List<ListaRegistro> listaListaRegistroMock = List.of(new ListaRegistro(), new ListaRegistro());

        when(edcService.getPapel("teste")).thenReturn(listaListaRegistroMock);

        when(operacoesService.getListaRegistro("teste")).thenReturn(listaListaRegistroMock);
        
        Response response = edcRest.getCodigoPapel("teste");

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

    }

    @Test
    public void testGetDetalhePapel() {

        List<ListaRegistro> listaListaRegistroMock = List.of(new ListaRegistro(), new ListaRegistro());

        when(edcService.getPapel("teste")).thenReturn(listaListaRegistroMock);

        when(operacoesService.getListaRegistro("teste")).thenReturn(listaListaRegistroMock);
        
        Response response = edcRest.getDetalhePapel("teste");

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

    }

}