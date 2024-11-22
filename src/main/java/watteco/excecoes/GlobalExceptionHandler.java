package watteco.excecoes;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<RuntimeException> {
    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @Override
    public Response toResponse(RuntimeException exception) {
        if (exception instanceof UsuarioNaoEncontradoException) {
            logger.warn("Usuário não encontrado: " + exception.getMessage());
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(exception.getMessage())
                    .build();
        } else if (exception instanceof EnergiaNaoEncontradaException) {
            logger.warn("Energia não encontrada: " + exception.getMessage());
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(exception.getMessage())
                    .build();
        } else if (exception instanceof ConsumoEnergeticoInvalidoException || exception instanceof EmissaoInvalidaException) {
            logger.warn("Erro de validação: " + exception.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(exception.getMessage())
                    .build();
        } else if (exception instanceof RequisicaoInvalidaException) {
            logger.warn("Requisição inválida: " + exception.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(exception.getMessage())
                    .build();
        } else {
            logger.error("Erro interno do servidor: ", exception);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno do servidor: " + exception.getMessage())
                    .build();
        }
    }
}
