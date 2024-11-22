package watteco.excecoes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UsuarioNaoEncontradoException extends RuntimeException {
    private static final Logger logger = LogManager.getLogger(UsuarioNaoEncontradoException.class);

    public UsuarioNaoEncontradoException(String message) {
        super(message);
        logger.error("UsuarioNaoEncontradoException lançada: " + message);
    }
}
