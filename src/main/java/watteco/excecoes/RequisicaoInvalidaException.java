package watteco.excecoes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RequisicaoInvalidaException extends RuntimeException {
    private static final Logger logger = LogManager.getLogger(RequisicaoInvalidaException.class);

    public RequisicaoInvalidaException(String message) {
        super(message);
        logger.error("RequisicaoInvalidaException lançada: " + message);
    }
}
