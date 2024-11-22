package watteco.infraestrutura;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import watteco.entidades.*;

import java.util.List;

public class Log4jLogger {

    private final Logger usuarioLogger;
    private final Logger enderecoLogger;
    private final Logger dispositivoLogger;
    private final Logger consumoEnergeticoLogger;
    private final Logger relatorioLogger;

    public Log4jLogger() {
        this.usuarioLogger = LogManager.getLogger(Usuario.class);
        this.enderecoLogger = LogManager.getLogger(Endereco.class);
        this.dispositivoLogger = LogManager.getLogger(Dispositivo.class);
        this.consumoEnergeticoLogger = LogManager.getLogger(ConsumoEnergetico.class);
        this.relatorioLogger = LogManager.getLogger(Relatorio.class);
    }

    public void logUsuarioCadastrado(Usuario usuario) {
        usuarioLogger.info("Cadastrado: " + usuario);
    }

    public void logEnderecoCadastrado(Endereco endereco) {
        enderecoLogger.info("Cadastrado: " + endereco);
    }

    public void logDispositivoCadastrado(Dispositivo dispositivo) {
        dispositivoLogger.info("Cadastrado: " + dispositivo);
    }

    public void logConsumoEnergeticoCadastrado(ConsumoEnergetico consumo) {
        consumoEnergeticoLogger.info("Cadastrado: " + consumo);
    }

    public void logRelatorioGerado(Relatorio relatorio) {
        relatorioLogger.info("Gerado: " + relatorio);
    }

    public void logUsuarioDeletado(Usuario usuario) {
        usuarioLogger.info("Deletado: " + usuario);
    }

    public void logEnderecoDeletado(Endereco endereco) {
        enderecoLogger.info("Deletado: " + endereco);
    }

    public void logDispositivoDeletado(Dispositivo dispositivo) {
        dispositivoLogger.info("Deletado: " + dispositivo);
    }

    public void logConsumoEnergeticoDeletado(ConsumoEnergetico consumo) {
        consumoEnergeticoLogger.info("Deletado: " + consumo);
    }

    public void logUsuarioAtualizado(Usuario usuario) {
        usuarioLogger.info("Atualizado: " + usuario);
    }

    public void logEnderecoAtualizado(Endereco endereco) {
        enderecoLogger.info("Atualizado: " + endereco);
    }

    public void logDispositivoAtualizado(Dispositivo dispositivo) {
        dispositivoLogger.info("Atualizado: " + dispositivo);
    }

    public void logConsumoEnergeticoAtualizado(ConsumoEnergetico consumo) {
        consumoEnergeticoLogger.info("Atualizado: " + consumo);
    }

    public void logUsuariosListados(List<Usuario> listaUsuarios) {
        usuarioLogger.info("Listados: " + listaUsuarios);
    }

    public void logEnderecosListados(List<Endereco> listaEnderecos) {
        enderecoLogger.info("Listados: " + listaEnderecos);
    }

    public void logDispositivosListados(List<Dispositivo> listaDispositivos) {
        dispositivoLogger.info("Listados: " + listaDispositivos);
    }

    public void logConsumosListados(List<ConsumoEnergetico> listaConsumos) {
        consumoEnergeticoLogger.info("Listados: " + listaConsumos);
    }

    public void logUsuarioBuscadoPorId(Usuario usuario) {
        usuarioLogger.info("Buscado: " + usuario);
    }

    public void logEnderecoBuscadoPorId(Endereco endereco) {
        enderecoLogger.info("Buscado: " + endereco);
    }

    public void logDispositivoBuscadoPorId(Dispositivo dispositivo) {
        dispositivoLogger.info("Buscado: " + dispositivo);
    }

    public void logConsumoBuscadoPorId(ConsumoEnergetico consumo) {
        consumoEnergeticoLogger.info("Buscado: " + consumo);
    }
}
