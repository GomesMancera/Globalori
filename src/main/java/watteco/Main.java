package watteco;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jsonb.JsonBindingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import watteco.filter.CorsFilter;

import java.io.IOException;
import java.net.URI;

/**
 * Classe principal do sistema Watteco.
 */
public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    // URI base que o servidor HTTP Grizzly irá escutar
    public static final String BASE_URI = "http://localhost:8080/api/";

    /**
     * Inicia o servidor HTTP Grizzly expondo os recursos JAX-RS definidos nesta aplicação.
     * @return Servidor HTTP Grizzly.
     */
    public static HttpServer startServer() {
        // Cria uma configuração de recurso que escaneia por recursos JAX-RS e provedores
        // no pacote watteco
        final ResourceConfig rc = new ResourceConfig()
                .packages("watteco.controladores", "watteco.excecoes", "watteco.filter");
        rc.register(JsonBindingFeature.class);
        rc.register(CorsFilter.class);

        // Cria e inicia uma nova instância do servidor HTTP Grizzly
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Método principal.
     * @param args Argumentos de linha de comando
     * @throws IOException Em caso de erro ao iniciar o servidor
     */
    public static void main(String[] args) throws IOException {
        try {
            logger.info("Sistema Watteco iniciando...");
            final HttpServer server = startServer();
            System.out.println(String.format("Aplicação Jersey iniciada com endpoints disponíveis em %s%nPressione Ctrl+C para encerrar...", BASE_URI));
            System.in.read();
            server.shutdownNow();
            logger.info("Sistema Watteco encerrado.");
        } catch (Exception e) {
            logger.error("Erro inesperado: ", e);
        }
    }
}
