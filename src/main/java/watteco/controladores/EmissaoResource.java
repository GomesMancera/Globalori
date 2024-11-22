package watteco.controladores;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import watteco.entidades.Emissao;
import watteco.servicos.EmissaoServico;

import java.util.List;
import java.util.Optional;

@Path("emissao")
public class EmissaoResource {
    private final EmissaoServico emissaoServico = new EmissaoServico();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Emissao> listarEmissoes() {
        return emissaoServico.listar();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarEmissaoPorId(@PathParam("id") int id) {
        Optional<Emissao> emissao = emissaoServico.buscarPorId(id);
        return emissao.map(value -> Response.ok(value).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Emissão não encontrada.\", \"id\": " + id + "}").build());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrarEmissao(Emissao emissao) {
        try {
            emissaoServico.cadastrar(emissao);
            String responseJson = String.format(
                    "{\"message\": \"Emissão cadastrada com sucesso.\", \"emissao\": {\"id\": %d, \"tipo\": \"%s\", \"valor\": %.2f, \"data\": \"%s\"}}",
                    emissao.getId(),
                    emissao.getTipo(),
                    emissao.getValor(),
                    emissao.getData()
            );

            return Response.status(Response.Status.CREATED)
                    .entity(responseJson)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Erro ao cadastrar emissão.\"}")
                    .build();
        }
    }


    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarEmissao(@PathParam("id") int id, Emissao emissao) {
        Optional<Emissao> _emissao = emissaoServico.buscarPorId(id);
        if (_emissao.isPresent()) {
            emissaoServico.atualizar(emissao, id);
            return Response.ok("{\"message\": \"Emissão atualizada com sucesso.\", \"id\": " + id + "}").build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"message\": \"Emissão não encontrada para atualização.\", \"id\": " + id + "}").build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removerEmissao(@PathParam("id") String id) {
        try {
            int emissaoId = Integer.parseInt(id.trim()); // Valida e converte o ID para inteiro
            Optional<Emissao> emissao = emissaoServico.buscarPorId(emissaoId);
            if (emissao.isPresent()) {
                emissaoServico.deletar(emissaoId);
                return Response.ok("{\"message\": \"Emissão removida com sucesso.\", \"id\": " + emissaoId + "}").build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Emissão não encontrada para remoção.\", \"id\": " + emissaoId + "}").build();
        } catch (NumberFormatException e) {
            // Retorna erro 400 Bad Request se o ID não for válido
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"ID inválido fornecido. Deve ser um número.\", \"id\": \"" + id + "\"}").build();
        } catch (Exception e) {
            // Trata outros erros inesperados
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Erro ao processar remoção de emissão.\", \"id\": \"" + id + "\"}").build();
        }
    }

}
