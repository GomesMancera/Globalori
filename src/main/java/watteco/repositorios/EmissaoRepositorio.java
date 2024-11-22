package watteco.repositorios;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import watteco.entidades.Emissao;
import watteco.infraestrutura.DatabaseConfig;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmissaoRepositorio implements _RepositorioCrud<Emissao> {

    private static final Logger logger = LogManager.getLogger(EmissaoRepositorio.class);

    @Override
    public void cadastrar(Emissao entidade) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "INSERT INTO T_QF_EMISSAO (VALOR, TIPO, DATA) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setDouble(1, entidade.getValor());
                stmt.setString(2, entidade.getTipo());
                stmt.setDate(3, Date.valueOf(entidade.getData()));
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            logger.error("Erro ao cadastrar Emissao: ", e);
            throw new RuntimeException("Falha ao cadastrar emissão no banco de dados.", e);
        }
    }

    @Override
    public void atualizar(Emissao entidade, int id) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "UPDATE T_QF_EMISSAO SET VALOR = ?, TIPO = ?, DATA = ? WHERE ID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setDouble(1, entidade.getValor());
                stmt.setString(2, entidade.getTipo());
                stmt.setDate(3, Date.valueOf(entidade.getData()));
                stmt.setInt(4, id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            logger.error("Erro ao atualizar Emissao: ", e);
            throw new RuntimeException("Falha ao atualizar emissão no banco de dados.", e);
        }
    }

    @Override
    public void remover(int id) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "DELETE FROM T_QF_EMISSAO WHERE ID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            logger.error("Erro ao remover Emissao: ", e);
            throw new RuntimeException("Falha ao remover emissão do banco de dados.", e);
        }
    }

    @Override
    public Optional<Emissao> buscarPorId(int id) {
        Optional<Emissao> emissao = Optional.empty();
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "SELECT * FROM T_QF_EMISSAO WHERE ID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    double valor = rs.getDouble("VALOR");
                    String tipo = rs.getString("TIPO");
                    Date data = rs.getDate("DATA");
                    emissao = Optional.of(new Emissao(id, valor, tipo, data.toLocalDate()));
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao buscar Emissao por ID: ", e);
        }
        return emissao;
    }

    @Override
    public List<Emissao> listar() {
        List<Emissao> emissoes = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "SELECT * FROM T_QF_EMISSAO";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    double valor = rs.getDouble("VALOR");
                    String tipo = rs.getString("TIPO");
                    Date data = rs.getDate("DATA");
                    emissoes.add(new Emissao(id, valor, tipo, data.toLocalDate()));
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao listar Emissao: ", e);
        }
        return emissoes;
    }
}
