package watteco.controladores;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import watteco.entidades.Usuario;
import watteco.excecoes.UsuarioNaoEncontradoException;
import watteco.servicos.UsuarioServico;

import java.util.List;
import java.util.Optional;

@Path("usuario")
public class UsuarioResource {

    private static final Logger logger = LogManager.getLogger(UsuarioResource.class);
    private final UsuarioServico usuarioServico = new UsuarioServico();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarUsuarios() {
        logger.info("Iniciando listagem de usuários.");
        try {
            List<Usuario> usuarios = usuarioServico.listar();
            logger.info("Quantidade de usuários encontrados: {}", usuarios.size());
            return Response.ok(usuarios).build();
        } catch (Exception e) {
            logger.error("Erro ao listar usuários: ", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Erro ao listar usuários.\"}").build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarUsuarioPorId(@PathParam("id") int id) {
        logger.info("Buscando usuário com ID: {}", id);
        try {
            Optional<Usuario> usuario = usuarioServico.buscarPorId(id);
            return usuario.map(value -> {
                        logger.info("Usuário encontrado: {}", value);
                        return Response.ok(value).build();
                    })
                    .orElseGet(() -> {
                        logger.warn("Usuário com ID {} não encontrado.", id);
                        return Response.status(Response.Status.NOT_FOUND)
                                .entity("{\"error\": \"Usuário não encontrado.\", \"id\": " + id + "}").build();
                    });
        } catch (Exception e) {
            logger.error("Erro ao buscar usuário com ID {}: ", id, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Erro ao buscar usuário.\", \"id\": " + id + "}").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrarUsuario(Usuario usuario) {
        logger.info("Requisição para cadastrar novo usuário: {}", usuario);
        try {
            usuarioServico.cadastrar(usuario); // Cadastra o usuário
            logger.info("Usuário cadastrado com sucesso: {}", usuario);

            // Retorna o usuário diretamente no corpo da resposta
            return Response.status(Response.Status.CREATED)
                    .entity(usuario)
                    .build();
        } catch (Exception e) {
            logger.error("Erro ao cadastrar usuário: ", e);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Erro ao cadastrar usuário. Verifique os dados fornecidos.\"}")
                    .build();
        }
    }


    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarUsuario(@PathParam("id") int id, Usuario usuario) {
        logger.info("Requisição para atualizar usuário com ID: {}", id);
        try {
            usuarioServico.atualizar(usuario, id);
            logger.info("Usuário com ID {} atualizado com sucesso.", id);
            return Response.ok("{\"message\": \"Usuário atualizado com sucesso.\", \"id\": " + id + "}").build();
        } catch (UsuarioNaoEncontradoException e) {
            logger.warn("Usuário com ID {} não encontrado para atualização.", id);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Usuário não encontrado para atualização.\", \"id\": " + id + "}").build();
        } catch (Exception e) {
            logger.error("Erro ao atualizar usuário com ID {}: ", id, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Erro ao atualizar usuário.\", \"id\": " + id + "}").build();
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removerUsuario(@PathParam("id") int id) {
        logger.info("Requisição para remover usuário com ID: {}", id);
        try {
            Optional<Usuario> usuario = usuarioServico.buscarPorId(id);
            if (usuario.isPresent()) {
                usuarioServico.deletar(id);
                logger.info("Usuário com ID {} removido com sucesso.", id);
                return Response.ok("{\"message\": \"Usuário removido com sucesso.\", \"id\": " + id + "}").build();
            }
            logger.warn("Usuário com ID {} não encontrado para remoção.", id);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Usuário não encontrado.\", \"id\": " + id + "}").build();
        } catch (Exception e) {
            logger.error("Erro ao remover usuário com ID {}: ", id, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Erro ao processar requisição para remover usuário.\", \"id\": " + id + "}").build();
        }
    }
}
