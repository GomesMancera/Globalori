package watteco.repositorios;

import watteco.entidades.Usuario;
import watteco.infraestrutura.DatabaseConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioRepositorio implements _RepositorioCrud<Usuario> {

    private static final Logger logger = LogManager.getLogger(UsuarioRepositorio.class);

    @Override
    public void cadastrar(Usuario usuario) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "INSERT INTO T_QF_USUARIOS (NOME, CPF, EMAIL, DATA_NASCIMENTO) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, usuario.getNome());
                stmt.setString(2, usuario.getCpf());
                stmt.setString(3, usuario.getEmail());
                stmt.setDate(4, Date.valueOf(usuario.getDataNascimento()));
                stmt.executeUpdate();
                logger.info("Usuário cadastrado com sucesso: {}", usuario);
            }
        } catch (SQLException e) {
            logger.error("Erro ao cadastrar usuário: ", e);
        }
    }

    @Override
    public void atualizar(Usuario usuario, int id) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "UPDATE T_QF_USUARIOS SET NOME = ?, CPF = ?, EMAIL = ?, DATA_NASCIMENTO = ? WHERE ID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, usuario.getNome());
                stmt.setString(2, usuario.getCpf());
                stmt.setString(3, usuario.getEmail());
                stmt.setDate(4, Date.valueOf(usuario.getDataNascimento()));
                stmt.setInt(5, id);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    logger.info("Usuário com ID {} atualizado com sucesso: {}", id, usuario);
                } else {
                    logger.warn("Nenhum usuário encontrado para atualizar com ID: {}", id);
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao atualizar usuário com ID {}: ", id, e);
        }
    }

    @Override
    public void remover(int id) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "DELETE FROM T_QF_USUARIOS WHERE ID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, id);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    logger.info("Usuário com ID {} removido com sucesso.", id);
                } else {
                    logger.warn("Nenhum usuário encontrado para remover com ID: {}", id);
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao remover usuário com ID {}: ", id, e);
        }
    }

    @Override
    public Optional<Usuario> buscarPorId(int id) {
        Usuario usuario = null;
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "SELECT * FROM T_QF_USUARIOS WHERE ID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, id);
                logger.info("Buscando usuário com ID: {}", id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    usuario = new Usuario(
                            rs.getInt("ID"),
                            rs.getString("NOME"),
                            rs.getString("CPF"),
                            rs.getString("EMAIL"),
                            rs.getDate("DATA_NASCIMENTO").toLocalDate()
                    );
                    logger.info("Usuário encontrado: {}", usuario);
                } else {
                    logger.warn("Nenhum usuário encontrado com ID: {}", id);
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao buscar usuário com ID {}: ", id, e);
        }
        return Optional.ofNullable(usuario);
    }

    @Override
    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "SELECT * FROM T_QF_USUARIOS";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                logger.info("Buscando todos os usuários.");
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Usuario usuario = new Usuario(
                            rs.getInt("ID"),
                            rs.getString("NOME"),
                            rs.getString("CPF"),
                            rs.getString("EMAIL"),
                            rs.getDate("DATA_NASCIMENTO").toLocalDate()
                    );
                    usuarios.add(usuario);
                }
                logger.info("Número de usuários encontrados: {}", usuarios.size());
            }
        } catch (SQLException e) {
            logger.error("Erro ao listar usuários: ", e);
        }
        return usuarios;
    }
}
