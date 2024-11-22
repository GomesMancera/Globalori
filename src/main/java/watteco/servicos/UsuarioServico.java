package watteco.servicos;

import watteco.entidades.Usuario;
import watteco.excecoes.UsuarioNaoEncontradoException;
import watteco.repositorios.UsuarioRepositorio;

import java.util.List;
import java.util.Optional;

public class UsuarioServico {
    private final UsuarioRepositorio usuarioRepositorio = new UsuarioRepositorio();

    public List<Usuario> listar() {
        return usuarioRepositorio.listar();
    }

    public Optional<Usuario> buscarPorId(int id) {
        return usuarioRepositorio.buscarPorId(id);
    }

    public void cadastrar(Usuario usuario) {
        usuarioRepositorio.cadastrar(usuario);
    }

    public void atualizar(Usuario usuario, int id) {
        Optional<Usuario> usuarioExistente = usuarioRepositorio.buscarPorId(id);
        if (usuarioExistente.isPresent()) {
            usuarioRepositorio.atualizar(usuario, id);
        } else {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado com o ID: " + id);
        }
    }


    public void deletar(int id) {
        usuarioRepositorio.remover(id);
    }
}
