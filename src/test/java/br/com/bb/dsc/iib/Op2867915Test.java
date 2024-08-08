package br.com.bb.dsc.iib;
import br.com.bb.atc.operacao.listarCodigosPapeisPesquisadosPeloUsuarioV1.bean.requisicao.DadosRequisicaoListarCodigosPapeisPesquisadosPeloUsuario;
import br.com.bb.atc.operacao.listarCodigosPapeisPesquisadosPeloUsuarioV1.bean.resposta.DadosRespostaListarCodigosPapeisPesquisadosPeloUsuario;
import br.com.bb.dsc.integration.ConsumidorCurio;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
 class Op2867915Test {

    @Mock
    private ConsumidorCurio consumidorMock;
    @InjectMocks
    private Op2867915 op2867915;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarCodigoPapel() {
        String codigoIdentificacaoDigitalMock = "codigo";
        String textoPesquisaMock = "pesquisa";

        DadosRequisicaoListarCodigosPapeisPesquisadosPeloUsuario requisicaoMock = new DadosRequisicaoListarCodigosPapeisPesquisadosPeloUsuario();
        requisicaoMock.setCodigoIdentificacaoDigital(codigoIdentificacaoDigitalMock);
        requisicaoMock.setTextoPesquisa(textoPesquisaMock);

        DadosRespostaListarCodigosPapeisPesquisadosPeloUsuario respostaMock = new DadosRespostaListarCodigosPapeisPesquisadosPeloUsuario();

        when(consumidorMock.executarOp2867915v1(eq(requisicaoMock))).thenReturn(respostaMock);

        DadosRespostaListarCodigosPapeisPesquisadosPeloUsuario result = op2867915.listarCodigoPapel(codigoIdentificacaoDigitalMock, textoPesquisaMock); 
    }

}
