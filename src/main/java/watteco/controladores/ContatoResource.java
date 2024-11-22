package watteco.controladores;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import watteco.entidades.Contato;
import watteco.servicos.ContatoServico;

import java.util.List;
import java.util.Optional;

@Path("contato")
public class ContatoResource {

    private static final Logger logger = LogManager.getLogger(ContatoResource.class);
    private final ContatoServico contatoServico = new ContatoServico();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarContatos() {
        logger.info("Iniciando a listagem de contatos.");
        try {
            List<Contato> contatos = contatoServico.listar();
            logger.info("Quantidade de contatos encontrados: {}", contatos.size());
            return Response.ok(contatos).build();
        } catch (Exception e) {
            logger.error("Erro ao listar contatos: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Erro ao listar contatos.\"}").build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarContatoPorId(@PathParam("id") int id) {
        logger.info("Buscando contato com ID: {}", id);
        try {
            Optional<Contato> contato = contatoServico.buscarPorId(id);
            return contato.map(value -> Response.ok(value).build())
                    .orElseGet(() -> {
                        logger.warn("Contato com ID {} não encontrado.", id);
                        return Response.status(Response.Status.NOT_FOUND)
                                .entity("{\"error\": \"Contato não encontrado.\", \"id\": " + id + "}").build();
                    });
        } catch (Exception e) {
            logger.error("Erro ao buscar contato com ID {}: ", id, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Erro ao buscar contato.\", \"id\": " + id + "}").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrarContato(Contato contato) {
        logger.info("Cadastrando novo contato: {}", contato);
        try {
            contatoServico.cadastrar(contato);
            logger.info("Contato cadastrado com sucesso: {}", contato);
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Contato cadastrado com sucesso.\", \"contato\": " + contato + "}").build();
        } catch (Exception e) {
            logger.error("Erro ao cadastrar contato: ", e);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Erro ao cadastrar contato.\"}").build();
        }
    }


    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarContato(@PathParam("id") int id, Contato contato) {
        logger.info("Atualizando contato com ID: {}", id);
        try {
            Optional<Contato> contatoExistente = contatoServico.buscarPorId(id);
            if (contatoExistente.isPresent()) {
                contatoServico.atualizar(contato, id);
                logger.info("Contato com ID {} atualizado com sucesso.", id);
                return Response.ok("{\"message\": \"Contato atualizado com sucesso.\", \"id\": " + id + "}").build();
            } else {
                logger.warn("Contato com ID {} não encontrado para atualização.", id);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"Contato não encontrado para atualização.\", \"id\": " + id + "}").build();
            }
        } catch (Exception e) {
            logger.error("Erro ao atualizar contato com ID {}: ", id, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Erro ao atualizar contato.\", \"id\": " + id + "}").build();
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removerContato(@PathParam("id") int id) {
        logger.info("Tentando remover contato com ID: {}", id);
        try {
            Optional<Contato> contatoExistente = contatoServico.buscarPorId(id);
            if (contatoExistente.isPresent()) {
                contatoServico.deletar(id);
                logger.info("Contato com ID {} removido com sucesso.", id);
                return Response.ok("{\"message\": \"Contato removido com sucesso.\", \"id\": " + id + "}").build();
            } else {
                logger.warn("Contato com ID {} não encontrado para remoção.", id);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"Contato não encontrado para remoção.\", \"id\": " + id + "}").build();
            }
        } catch (Exception e) {
            logger.error("Erro ao remover contato com ID {}: ", id, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Erro ao remover contato.\", \"id\": " + id + "}").build();
        }
    }
}
