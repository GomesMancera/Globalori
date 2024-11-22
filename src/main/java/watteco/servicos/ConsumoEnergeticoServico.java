package watteco.servicos;

import watteco.entidades.ConsumoEnergetico;
import watteco.repositorios.ConsumoEnergeticoRepositorio;

import java.util.List;
import java.util.Optional;

public class ConsumoEnergeticoServico {

    private final ConsumoEnergeticoRepositorio consumoEnergeticoRepositorio = new ConsumoEnergeticoRepositorio();

    public List<ConsumoEnergetico> listar() {
        return consumoEnergeticoRepositorio.listar();
    }

    public Optional<ConsumoEnergetico> buscarPorId(int id) {
        return consumoEnergeticoRepositorio.buscarPorId(id);
    }

    public void cadastrar(ConsumoEnergetico consumoEnergetico) {
        consumoEnergeticoRepositorio.cadastrar(consumoEnergetico);

    }

    public void atualizar(ConsumoEnergetico consumoEnergetico, int id) {
        consumoEnergeticoRepositorio.atualizar(consumoEnergetico, id);
    }

    public void deletar(int id) {
        consumoEnergeticoRepositorio.remover(id);
    }
}
