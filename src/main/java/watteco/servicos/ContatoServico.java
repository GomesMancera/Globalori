package watteco.servicos;

import watteco.entidades.Contato;
import watteco.repositorios.ContatoRepositorio;

import java.util.List;
import java.util.Optional;

public class ContatoServico {
    private final ContatoRepositorio contatoRepositorio = new ContatoRepositorio();

    public List<Contato> listar() {
        return contatoRepositorio.listar();
    }

    public Optional<Contato> buscarPorId(int id) {
        return contatoRepositorio.buscarPorId(id);
    }

    public void cadastrar(Contato contato) {
        contatoRepositorio.cadastrar(contato);
    }

    public void atualizar(Contato contato, int id) {
        contatoRepositorio.atualizar(contato, id);
    }

    public void deletar(int id) {
        contatoRepositorio.remover(id);
    }
}
