package watteco.repositorios;

import java.util.List;
import java.util.Optional;

public interface _RepositorioCrud<T> {
    void cadastrar(T entidade);
    void atualizar(T entidade, int id);
    void remover(int id);
    Optional<T> buscarPorId(int id);
    List<T> listar();
}
