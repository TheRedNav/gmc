package br.com.bb.dsc.util;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
 class FormatEDCTest {

    @InjectMocks
    private FormatEDC formatEDC;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private final String input = "REC_DB2-D3G4_DomInf_Derivados://D1D5J01/DB2D5J/DIM_ENT";
    private final String input2 = "REC_HIVE_Dominf_Mestres://Hive%20Metastore/hive_m5d/log_acss_dom_inf";
    private final String input3 = "TESTE_DB2D3G4://BDB2P04/DB2WSI/HST_APLTRACE_D3G4";
    private static final int HIV = 212;
    private static final int DB2 = 242;

    @Test
     void testGetPapel() {

        assertEquals(242, formatEDC.getPapel(input).getTipoPapel());
        assertEquals(212, formatEDC.getPapel(input2).getTipoPapel());
        assertEquals(242, formatEDC.getPapel(input3).getTipoPapel());

    }

    @Test
     void testConvertToTipoPapel() {
        String nomePapel1 = "BG#SFDGSD";
        int result1 = formatEDC.convertToTipoPapel(nomePapel1);
        assertEquals(this.HIV, result1);
        
        String nomePapel2 = "#BIDFSDD"; 
        int result2 = formatEDC.convertToTipoPapel(nomePapel2);
        assertEquals(this.DB2, result2);

        String nomePapel3 = "OutroNome";
        int result3 = formatEDC.convertToTipoPapel(nomePapel3);
        assertEquals(0, result3);
    }

    @Test
     void testGetCriticidade() {

        String criticidade0 = "$90";
        String result0 = formatEDC.getCriticidade(criticidade0);
        assertEquals("A CLASSIFICAR", result0);

        String criticidade1 = "$40";
        String result1 = formatEDC.getCriticidade(criticidade1);
        assertEquals("#CONFIDENCIAL", result1);

        String criticidade2 = "$30";
        String result2 = formatEDC.getCriticidade(criticidade2);
        assertEquals("#INTERNA", result2);

        String criticidade3 = "$20";
        String result3 = formatEDC.getCriticidade(criticidade3);
        assertEquals("#INTERNA", result3);
        
        String criticidade4 = "$10";
        String result4 = formatEDC.getCriticidade(criticidade4);
        assertEquals("#PUBLICA", result4);

        String criticidade5 = "OutroValor";
        String result5 = formatEDC.getCriticidade(criticidade5);
        assertEquals("", result5);
    }


    @Test
    void TestGetUrl(){

        String url ="exemplo.com/teste 1";
        String result5 = formatEDC.getUrl(url);
        assertEquals("exemplo.com/teste%201", result5);
    }

}
