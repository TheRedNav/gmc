package br.com.bb.dsc.iib;
import br.com.bb.atc.operacao.consultarDetalhesPapelPeloCodigoV1.bean.requisicao.DadosRequisicaoConsultarDetalhesPapelPeloCodigo;
import br.com.bb.atc.operacao.consultarDetalhesPapelPeloCodigoV1.bean.resposta.DadosRespostaConsultarDetalhesPapelPeloCodigo;
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
 class Op2850033Test {
    @Mock
    private ConsumidorCurio consumidorMock;
    @InjectMocks
    private Op2850033 op2850033;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConsultarPapel() {
        String codigoPapelMock = "codigo";

        DadosRequisicaoConsultarDetalhesPapelPeloCodigo requisicaoMock = new DadosRequisicaoConsultarDetalhesPapelPeloCodigo();
        requisicaoMock.setCodigoPapel(codigoPapelMock);

        DadosRespostaConsultarDetalhesPapelPeloCodigo respostaMock = new DadosRespostaConsultarDetalhesPapelPeloCodigo();

        when(consumidorMock.executarOp2850033v1(requisicaoMock)).thenReturn(respostaMock);

        DadosRespostaConsultarDetalhesPapelPeloCodigo result = op2850033.consultarPapel(codigoPapelMock);

    }
}
