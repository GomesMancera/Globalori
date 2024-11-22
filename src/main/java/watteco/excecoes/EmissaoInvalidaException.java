package watteco.excecoes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EmissaoInvalidaException extends RuntimeException {
    private static final Logger logger = LogManager.getLogger(EmissaoInvalidaException.class);

    public EmissaoInvalidaException(String message) {
        super(message);
        logger.error("EmissaoInvalidaException lançada: " + message);
    }
}
