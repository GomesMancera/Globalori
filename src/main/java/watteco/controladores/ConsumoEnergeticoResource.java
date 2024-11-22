package watteco.controladores;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import watteco.entidades.ConsumoEnergetico;
import watteco.servicos.ConsumoEnergeticoServico;

import java.util.List;
import java.util.Optional;

@Path("consumoEnergetico")
public class ConsumoEnergeticoResource {
    private static final Logger logger = LogManager.getLogger(ConsumoEnergeticoResource.class);
    private final ConsumoEnergeticoServico consumoEnergeticoServico = new ConsumoEnergeticoServico();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarConsumos() {
        logger.info("Requisição para listar todos os consumos energéticos.");
        try {
            List<ConsumoEnergetico> consumos = consumoEnergeticoServico.listar();
            logger.info("Quantidade de consumos encontrados: {}", consumos.size());
            return Response.ok(consumos).build();
        } catch (Exception e) {
            logger.error("Erro ao listar consumos energéticos: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Erro ao listar consumos energéticos.\"}").build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarConsumoPorId(@PathParam("id") int id) {
        logger.info("Requisição para buscar consumo energético com ID: {}", id);
        try {
            Optional<ConsumoEnergetico> consumo = consumoEnergeticoServico.buscarPorId(id);
            return consumo.map(value -> {
                        logger.info("Consumo encontrado: {}", value);
                        return Response.ok(value).build();
                    })
                    .orElseGet(() -> {
                        logger.warn("Consumo com ID {} não encontrado.", id);
                        return Response.status(Response.Status.NOT_FOUND)
                                .entity("{\"error\": \"Consumo não encontrado.\", \"id\": " + id + "}").build();
                    });
        } catch (Exception e) {
            logger.error("Erro ao buscar consumo energético com ID {}: ", id, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Erro ao processar requisição para buscar consumo.\", \"id\": " + id + "}").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrarConsumo(ConsumoEnergetico consumo) {
        logger.info("Requisição para cadastrar novo consumo: {}", consumo);
        try {
            consumoEnergeticoServico.cadastrar(consumo);
            logger.info("Consumo cadastrado com sucesso: {}", consumo);

            // Criação do JSON manualmente para retornar uma resposta clara
            String responseJson = String.format(
                    "{\"message\": \"Consumo cadastrado com sucesso.\", \"consumo\": {\"id\": %d, \"data\": \"%s\", \"valor\": %.2f, \"usuarioId\": %d}}",
                    consumo.getId(),
                    consumo.getData(),
                    consumo.getValor(),
                    consumo.getUsuarioId()
            );

            return Response.status(Response.Status.CREATED)
                    .entity(responseJson)
                    .build();
        } catch (Exception e) {
            logger.error("Erro ao cadastrar consumo: ", e);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Erro ao cadastrar consumo. Verifique os dados fornecidos.\"}")
                    .build();
        }
    }


    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarConsumo(@PathParam("id") int id, ConsumoEnergetico consumo) {
        logger.info("Requisição para atualizar consumo com ID: {}", id);
        try {
            Optional<ConsumoEnergetico> consumoExistente = consumoEnergeticoServico.buscarPorId(id);
            if (consumoExistente.isPresent()) {
                consumoEnergeticoServico.atualizar(consumo, id);
                logger.info("Consumo com ID {} atualizado com sucesso.", id);
                return Response.ok("{\"message\": \"Consumo atualizado com sucesso.\", \"id\": " + id + "}").build();
            }
            logger.warn("Consumo com ID {} não encontrado para atualização.", id);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Consumo não encontrado para atualização.\", \"id\": " + id + "}").build();
        } catch (Exception e) {
            logger.error("Erro ao atualizar consumo com ID {}: ", id, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Erro ao atualizar consumo.\", \"id\": " + id + "}").build();
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removerConsumo(@PathParam("id") String id) {
        logger.info("Requisição para remover consumo com ID: {}", id);
        try {
            int consumoId = Integer.parseInt(id.trim()); // Sanitização do parâmetro
            Optional<ConsumoEnergetico> consumo = consumoEnergeticoServico.buscarPorId(consumoId);
            if (consumo.isPresent()) {
                consumoEnergeticoServico.deletar(consumoId);
                logger.info("Consumo com ID {} removido com sucesso.", consumoId);
                return Response.ok("{\"message\": \"Consumo removido com sucesso.\", \"id\": " + consumoId + "}").build();
            }
            logger.warn("Consumo com ID {} não encontrado.", consumoId);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Consumo não encontrado.\", \"id\": " + consumoId + "}").build();
        } catch (NumberFormatException e) {
            logger.error("ID inválido fornecido: {}", id, e);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"ID inválido fornecido. Deve ser um número.\", \"id\": \"" + id + "\"}").build();
        } catch (Exception e) {
            logger.error("Erro ao remover consumo com ID {}: ", id, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Erro ao processar requisição para remover consumo.\", \"id\": \"" + id + "\"}").build();
        }
    }
}
