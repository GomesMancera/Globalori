package watteco.servicos;

import watteco.entidades.Energia;
import watteco.repositorios.EnergiaRepositorio;

import java.util.List;
import java.util.Optional;

public class EnergiaServico {
    private final EnergiaRepositorio energiaRepositorio = new EnergiaRepositorio();

    public List<Energia> listar() {
        return energiaRepositorio.listar();
    }

    public Optional<Energia> buscarPorId(int id) {
        return energiaRepositorio.buscarPorId(id);
    }

    public void cadastrar(Energia energia) {
        energiaRepositorio.cadastrar(energia);
    }

    public void atualizar(Energia energia, int id) {
        energiaRepositorio.atualizar(energia, id);
    }

    public void deletar(int id) {
        energiaRepositorio.remover(id);
    }
}
