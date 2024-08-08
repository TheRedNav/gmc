package br.com.bb.dsc.services;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import br.com.bb.atc.operacao.consultarDetalhesPapelPeloCodigoV1.bean.resposta.DadosRespostaConsultarDetalhesPapelPeloCodigo;
import br.com.bb.atc.operacao.listarCodigosPapeisPesquisadosPeloUsuarioV1.bean.resposta.DadosRespostaListarCodigosPapeisPesquisadosPeloUsuario;
import br.com.bb.atc.operacao.listarCodigosPapeisPesquisadosPeloUsuarioV1.bean.resposta.ListaRegistro;
import br.com.bb.dsc.iib.Op2850033;
import br.com.bb.dsc.iib.Op2867915;
import br.com.bb.dsc.models.Papel;
import br.com.bb.dsc.models.Unidade;
import br.com.bb.dsc.util.Factory;
import br.com.bb.dsc.util.FormatEDC;
import io.quarkus.test.junit.QuarkusTest;
@QuarkusTest
 class OperacoesServiceTest {

    @InjectMocks
    OperacoesService operacoesService;

    @Mock
    Op2867915 op2867915;

    @Mock
    Op2850033 op2850033;

    @Mock
    Papel papel;

    @Mock
    Unidade unidade;

    @Mock
    FormatEDC formatEDC;

    @Mock
    DadosRespostaListarCodigosPapeisPesquisadosPeloUsuario dadosRespostaListarCodigosPapeisPesquisadosPeloUsuario;
    
    @Mock
    DadosRespostaConsultarDetalhesPapelPeloCodigo dadosRespostaConsultarDetalhesPapelPeloCodigo;

    @Mock
    ArrayList<ListaRegistro> listaListaRegistro;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        papel = Factory.getPapel();
        unidade = Factory.getUnidade();
        listaListaRegistro = Factory.getListListaRegistro();
        dadosRespostaListarCodigosPapeisPesquisadosPeloUsuario.setListaRegistro(listaListaRegistro);
        dadosRespostaConsultarDetalhesPapelPeloCodigo = Factory.getDadosRespostaConsultarDetalhesPapelPeloCodigo();
        
    }

    @Test
    void testConsultarCodigoDoPapel(){    
        
        when(op2867915.listarCodigoPapel(anyString(), anyString())).thenReturn(dadosRespostaListarCodigosPapeisPesquisadosPeloUsuario); 
        when(op2867915.listarCodigoPapel(anyString(), anyString()).getListaRegistro()).thenReturn(listaListaRegistro); 
        when(formatEDC.convertToTipoPapel(anyString())).thenReturn(papel.getTipoPapel());
        
        String codigoPapel = operacoesService.consultarCodigoDoPapel(papel);
        assert(listaListaRegistro.get(0).getCodigoPapel()).equals(codigoPapel);

    }

    @Test
    void testConsultarDetalhesPapel(){
        when(op2850033.consultarPapel(anyString())).thenReturn(dadosRespostaConsultarDetalhesPapelPeloCodigo);
        when(formatEDC.getCriticidade(anyString())).thenReturn(unidade.getCriticidade());
        Unidade respostaTeste = operacoesService.consultarDetalhesPapel(anyString());
        verify(op2850033).consultarPapel(anyString());
        assert(respostaTeste.getNome()).equals(unidade.getNome());
    }

    @Test
    void testConsultarDadosTabela(){
        
        when(op2867915.listarCodigoPapel(anyString(), anyString())).thenReturn(dadosRespostaListarCodigosPapeisPesquisadosPeloUsuario); 
        when(op2867915.listarCodigoPapel(anyString(), anyString()).getListaRegistro()).thenReturn(listaListaRegistro); 
        when(formatEDC.convertToTipoPapel(anyString())).thenReturn(papel.getTipoPapel());

        when(op2850033.consultarPapel(anyString())).thenReturn(dadosRespostaConsultarDetalhesPapelPeloCodigo);
        when(formatEDC.getCriticidade(anyString())).thenReturn(unidade.getCriticidade());

        Unidade respostaTeste = operacoesService.consultarDadosTabela(papel);

        assert(respostaTeste.getNome()).equals(unidade.getNome());

    } 

    @Test
    void testGetListaRegistro(){
        when(op2867915.listarCodigoPapel(anyString(), anyString())).thenReturn(dadosRespostaListarCodigosPapeisPesquisadosPeloUsuario); 
        when(op2867915.listarCodigoPapel(anyString(), anyString()).getListaRegistro()).thenReturn(listaListaRegistro);
        List<ListaRegistro> retornoListaRegistro = operacoesService.getListaRegistro(listaListaRegistro.get(0).getNomePapel());
        
        assert(retornoListaRegistro.get(0).getSiglaSistema()).equals(listaListaRegistro.get(0).getSiglaSistema());

    }
}
