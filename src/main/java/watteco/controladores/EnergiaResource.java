package watteco.controladores;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import watteco.entidades.Energia;
import watteco.servicos.EnergiaServico;

import java.util.List;
import java.util.Optional;

@Path("energia")
public class EnergiaResource {
    private static final Logger logger = LogManager.getLogger(EnergiaResource.class);
    private final EnergiaServico energiaServico = new EnergiaServico();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarEnergias() {
        logger.info("Requisição para listar todas as energias.");
        try {
            List<Energia> energias = energiaServico.listar();
            logger.info("Quantidade de energias encontradas: {}", energias.size());
            return Response.ok(energias).build();
        } catch (Exception e) {
            logger.error("Erro ao listar energias: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Erro ao listar energias.\"}").build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarEnergiaPorId(@PathParam("id") int id) {
        logger.info("Requisição para buscar energia com ID: {}", id);
        try {
            Optional<Energia> energia = energiaServico.buscarPorId(id);
            return energia.map(value -> {
                        logger.info("Energia encontrada: {}", value);
                        return Response.ok(value).build();
                    })
                    .orElseGet(() -> {
                        logger.warn("Energia com ID {} não encontrada.", id);
                        return Response.status(Response.Status.NOT_FOUND)
                                .entity("{\"error\": \"Energia não encontrada.\", \"id\": " + id + "}").build();
                    });
        } catch (Exception e) {
            logger.error("Erro ao buscar energia com ID {}: ", id, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Erro ao buscar energia.\", \"id\": " + id + "}").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrarEnergia(Energia energia) {
        logger.info("Requisição para cadastrar nova energia: {}", energia);
        try {
            energiaServico.cadastrar(energia);
            logger.info("Energia cadastrada com sucesso: {}", energia);
            return Response.status(Response.Status.CREATED)
                    .entity(energia)
                    .build();

        } catch (Exception e) {
            logger.error("Erro ao cadastrar energia: ", e);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Erro ao cadastrar energia. Verifique os dados fornecidos.\"}").build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarEnergia(@PathParam("id") int id, Energia energia) {
        logger.info("Requisição para atualizar energia com ID: {}", id);
        try {
            Optional<Energia> energiaExistente = energiaServico.buscarPorId(id);
            if (energiaExistente.isPresent()) {
                energiaServico.atualizar(energia, id);
                logger.info("Energia com ID {} atualizada com sucesso.", id);
                return Response.ok("{\"message\": \"Energia atualizada com sucesso.\", \"id\": " + id + "}").build();
            }
            logger.warn("Energia com ID {} não encontrada para atualização.", id);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Energia não encontrada para atualização.\", \"id\": " + id + "}").build();
        } catch (Exception e) {
            logger.error("Erro ao atualizar energia com ID {}: ", id, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Erro ao atualizar energia.\", \"id\": " + id + "}").build();
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removerEnergia(@PathParam("id") int id) {
        logger.info("Requisição para remover energia com ID: {}", id);
        try {
            Optional<Energia> energiaExistente = energiaServico.buscarPorId(id);
            if (energiaExistente.isPresent()) {
                energiaServico.deletar(id);
                logger.info("Energia com ID {} removida com sucesso.", id);
                return Response.ok("{\"message\": \"Energia removida com sucesso.\", \"id\": " + id + "}").build();
            }
            logger.warn("Energia com ID {} não encontrada para remoção.", id);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Energia não encontrada.\", \"id\": " + id + "}").build();
        } catch (Exception e) {
            logger.error("Erro ao remover energia com ID {}: ", id, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Erro ao processar requisição para remover energia.\", \"id\": " + id + "}").build();
        }
    }
}
