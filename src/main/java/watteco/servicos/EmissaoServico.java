package watteco.servicos;

import watteco.entidades.Emissao;
import watteco.repositorios.EmissaoRepositorio;

import java.util.List;
import java.util.Optional;

public class EmissaoServico {
    private final EmissaoRepositorio emissaoRepositorio = new EmissaoRepositorio();

    public List<Emissao> listar() {
        return emissaoRepositorio.listar();
    }

    public Optional<Emissao> buscarPorId(int id) {
        return emissaoRepositorio.buscarPorId(id);
    }

    public void cadastrar(Emissao emissao) {
        emissaoRepositorio.cadastrar(emissao);
    }

    public void atualizar(Emissao emissao, int id) {
        emissaoRepositorio.atualizar(emissao, id);
    }

    public void deletar(int id) {
        emissaoRepositorio.remover(id);
    }
}
