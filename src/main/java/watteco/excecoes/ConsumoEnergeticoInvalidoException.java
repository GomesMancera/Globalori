package watteco.excecoes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConsumoEnergeticoInvalidoException extends RuntimeException {
    private static final Logger logger = LogManager.getLogger(ConsumoEnergeticoInvalidoException.class);

    public ConsumoEnergeticoInvalidoException(String message) {
        super(message);
        logger.error("ConsumoEnergeticoInvalidoException lançada: " + message);
    }
}
