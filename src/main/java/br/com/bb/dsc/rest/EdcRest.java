package br.com.bb.dsc.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import br.com.bb.atc.operacao.listarCodigosPapeisPesquisadosPeloUsuarioV1.bean.resposta.ListaRegistro;
import br.com.bb.dsc.models.Papel;
import br.com.bb.dsc.models.Unidade;
import br.com.bb.dsc.services.EdcService;

@Path("/edc-informatica")
@RequestScoped
public class EdcRest {

    @Inject
    private EdcService edcService;

    @POST
    @Path("/edc/todos")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Atualiza os dados das de todas as entidades do EDC", description = "Ao ser usada, a API consulta as operações IIB e atualiza todos os dados do edc")
    public Response atualizarTodos() throws InterruptedException {
        this.edcService.atualizarTodos();
        return Response.noContent().build();
    }

    @POST
    @Path("/edc/id")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Atualiza os dados da entidade do EDC", description = "Ao informar o id da entidade, a API consulta as operações IIB e atualiza o edc")
    @APIResponse(responseCode = "200", description = "Sucesso", content = @Content( mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Unidade.class)))
    public Response atualizarPorId(String id) {
        return Response.status(Response.Status.OK).entity(this.edcService.atualizarPorId(id)).build();
    }

    @GET
    @Path("/edc/dados/brutos/{pagina}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Dados brutos do EDC", description = "Ao informar a pagina que deseja, a API recupera os dados diretamente do EDC")
    @APIResponse(responseCode = "200", description = "Sucesso", content = @Content( mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Papel.class, type = SchemaType.ARRAY)))
    public Response getBrutos(@PathParam("pagina") int pagina) {
        return Response.status(Response.Status.OK).entity(this.edcService.getListaPapel(pagina)).build();
    }

    @GET
    @Path("/edc/dados/tratados/{pagina}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Dados tratados do EDC", description = "Ao informar a pagina que deseja, a API recupera os dados tratados do EDC")
    @APIResponse(responseCode = "200", description = "Sucesso", content = @Content( mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Unidade.class, type = SchemaType.ARRAY)))
    public Response getTratados(@PathParam("pagina") int pagina) {
        return Response.status(Response.Status.OK).entity(this.edcService.getListaUnidades(pagina)).build();
    }

    @GET
    @Path("/op/2867915/{nomePapel}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Recuperar no Código do Papel", description = "Ao informar o nome do Papel, retornará uma lista de papeis com os códigos")
    @APIResponse(responseCode = "200", description = "Sucesso", content = @Content( mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ListaRegistro.class, type = SchemaType.ARRAY)))
    public Response getCodigoPapel(@PathParam("nomePapel") String nomePapel) {
        return Response.status(Response.Status.OK).entity(this.edcService.getPapel(nomePapel)).build();
    }

    @GET
    @Path("/op/2850033/{codigoPapel}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Recuperar detalhes do Papel", description = "Ao informar o nome o codigo do papel, retornará detalhes do papel")
    @APIResponse(responseCode = "200", description = "Sucesso", content = @Content( mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Unidade.class)))
    public Response getDetalhePapel(@PathParam("codigoPapel") String codigoPapel) {
        return Response.status(Response.Status.OK).entity(this.edcService.getDetalhePapel(codigoPapel)).build();
    }

    @POST
    @Path("/desenv/teste")
    public Response teste(String tempoSegundo) throws InterruptedException {
        this.edcService.testesDesenv(Integer.parseInt(tempoSegundo));
        return Response.noContent().build();
    }

}
