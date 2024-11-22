package watteco;

import org.glassfish.jersey.server.ResourceConfig;

import jakarta.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class WattecoApplication extends ResourceConfig {
    public WattecoApplication() {
        // Escaneando pacotes para localizar as classes de recursos e outros componentes
        packages("watteco.controladores", "watteco.excecoes", "watteco.filter");

        // Registro explícito de todas as classes de recursos
        register(watteco.controladores.ContatoResource.class);
        register(watteco.controladores.ConsumoEnergeticoResource.class);
        register(watteco.controladores.EmissaoResource.class);
        register(watteco.controladores.EnergiaResource.class);
        register(watteco.controladores.UsuarioResource.class);

        // Registro de manipuladores de exceções
        register(watteco.excecoes.GlobalExceptionHandler.class);

        // Configuração para suporte a JSON usando Jackson
        register(org.glassfish.jersey.jackson.JacksonFeature.class);

        // Outros registros ou configurações de inicialização podem ser adicionados aqui
    }
}
