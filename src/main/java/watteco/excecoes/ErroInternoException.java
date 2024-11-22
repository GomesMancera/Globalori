package watteco.excecoes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ErroInternoException extends RuntimeException {
    private static final Logger logger = LogManager.getLogger(ErroInternoException.class);

    public ErroInternoException(String message) {
        super(message);
        logger.error("ErroInternoException lançada: " + message);
    }

    public ErroInternoException(String message, Throwable cause) {
        super(message, cause);
        logger.error("ErroInternoException lançada com causa: " + message, cause);
    }
}
