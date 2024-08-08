package br.com.bb.dsc.util;
import br.com.bb.dsc.models.Facts;
import br.com.bb.dsc.models.Items;
import br.com.bb.dsc.models.Papel;
import br.com.bb.dsc.models.Objeto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
 class FiltroEDCTest {

    @InjectMocks
    private FiltroEDC filtroEDC;

    @Mock
    FormatEDC formatEDC;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testGetListaPapeis() throws IOException {
        String jsonMock = "{\"hits\": [{\"id\": \"id1\"}, {\"id\": \"id2\"}]}";

        Papel papelMock1 = new Papel();
        papelMock1.setId("id1"); // Configurar o ID simulado

        Papel papelMock2 = new Papel();
        papelMock2.setId("id2"); // Configurar o ID simulado

        when(filtroEDC.formatEDC.getPapel(eq("id1"))).thenReturn(papelMock1);
        when(filtroEDC.formatEDC.getPapel(eq("id2"))).thenReturn(papelMock2);

        List<Papel> result = filtroEDC.getListaPapeis(jsonMock);

        assertEquals(2, result.size()); // Verificar se a lista contém 2 papéis

        assertEquals(papelMock1.getId(), result.get(0).getId()); // Verificar se o ID do primeiro papel é o esperado
        assertEquals(papelMock2.getId(), result.get(1).getId()); // Verificar se o ID do segundo papel é o esperado
    


        //ID NULL
        filtroEDC.getListaPapeis(jsonMock);

        //Json NULL
        result = filtroEDC.getListaPapeis(null);
        assertEquals(0, result.size()); // Verificar se a lista contém 2 papéis

    }

    @Test
     void testGetObjeto() throws IOException {
        String etagMock = "etag";
        String jsonMock = "{\"items\": [{\"id\": \"REC_DB2-D3G4_DomInf_Derivados://D1D5J01/DB2D5J/DIM_ENT\","
                +"\"facts\": [{\"attributeId\": \"A\", \"value\": \"B\", \"label\": \"C\", \"description\": \"D\"," 
                +"\"providerId\": \"E\", \"xid\": \"F\", \"modifiedBy\": \"G\", \"projectedFrom\": \"G\",  \"readOnly\": true }]}]}";

        List<Items> items = new ArrayList<>();
        Objeto objetoMock = new Objeto();
        objetoMock.setEtag(etagMock);
        objetoMock.setItems(items);

        Objeto result = filtroEDC.getObjeto(etagMock, jsonMock);

        assertNotNull(result);
        assertEquals(etagMock, result.getEtag());


        // Teste sem o projectedFrom
        String jsonMocksem = "{\"items\": [{\"id\": \"REC_DB2-D3G4_DomInf_Derivados://D1D5J01/DB2D5J/DIM_ENT\","
        +"\"facts\": [{\"attributeId\": \"A\", \"value\": \"B\", \"label\": \"C\", \"description\": \"D\"," 
        +"\"providerId\": \"E\", \"xid\": \"F\", \"modifiedBy\": \"G\",\"modifiedBy\": \"G\", \"readOnly\": true }]}]}";

        result = filtroEDC.getObjeto(etagMock, jsonMocksem);
        
        assertNotNull(result);
        assertEquals(etagMock, result.getEtag());



    }

}
