package watteco.excecoes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EnergiaNaoEncontradaException extends RuntimeException {
    private static final Logger logger = LogManager.getLogger(EnergiaNaoEncontradaException.class);

    public EnergiaNaoEncontradaException(String message) {
        super(message);
        logger.error("EnergiaNaoEncontradaException lançada: " + message);
    }
}
