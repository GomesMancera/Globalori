package watteco.repositorios;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import watteco.entidades.ConsumoEnergetico;
import watteco.infraestrutura.DatabaseConfig;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConsumoEnergeticoRepositorio implements _RepositorioCrud<ConsumoEnergetico> {

    private static final Logger logger = LogManager.getLogger(ConsumoEnergeticoRepositorio.class);

    @Override
    public void cadastrar(ConsumoEnergetico entidade) {
        logger.info("Tentando cadastrar consumo energético: {}", entidade);
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "INSERT INTO T_QF_CONSUMO_ENERGETICO (DATA, VALOR, ID_USUARIO) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setDate(1, Date.valueOf(entidade.getData()));
                stmt.setDouble(2, entidade.getValor());
                stmt.setInt(3, entidade.getUsuarioId());
                stmt.executeUpdate();
                logger.info("Consumo energético cadastrado com sucesso: {}", entidade);
            }
        } catch (SQLException e) {
            logger.error("Erro ao cadastrar consumo energético: ", e);
            throw new RuntimeException("Erro ao cadastrar consumo no banco de dados.", e);
        }
    }


    @Override
    public void atualizar(ConsumoEnergetico entidade, int id) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "UPDATE T_QF_CONSUMO_ENERGETICO SET DATA = ?, VALOR = ?, ID_USUARIO = ? WHERE ID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setDate(1, Date.valueOf(entidade.getData()));
                stmt.setDouble(2, entidade.getValor());
                stmt.setInt(3, entidade.getUsuarioId());
                stmt.setInt(4, id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            logger.error("Erro ao atualizar ConsumoEnergetico: ", e);
            throw new RuntimeException("Falha ao atualizar consumo no banco de dados.", e);
        }
    }

    @Override
    public void remover(int id) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "DELETE FROM T_QF_CONSUMO_ENERGETICO WHERE ID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            logger.error("Erro ao remover ConsumoEnergetico: ", e);
            throw new RuntimeException("Falha ao remover consumo do banco de dados.", e);
        }
    }

    @Override
    public Optional<ConsumoEnergetico> buscarPorId(int id) {
        Optional<ConsumoEnergetico> consumo = Optional.empty();
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "SELECT * FROM T_QF_CONSUMO_ENERGETICO WHERE ID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    Date data = rs.getDate("DATA");
                    double valor = rs.getDouble("VALOR");
                    int usuarioId = rs.getInt("ID_USUARIO");
                    consumo = Optional.of(new ConsumoEnergetico(id, data.toLocalDate(), valor, usuarioId));
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao buscar ConsumoEnergetico por ID: ", e);
        }
        return consumo;
    }

    @Override
    public List<ConsumoEnergetico> listar() {
        List<ConsumoEnergetico> consumos = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "SELECT * FROM T_QF_CONSUMO_ENERGETICO";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    Date data = rs.getDate("DATA");
                    double valor = rs.getDouble("VALOR");
                    int usuarioId = rs.getInt("ID_USUARIO");
                    consumos.add(new ConsumoEnergetico(id, data.toLocalDate(), valor, usuarioId));
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao listar ConsumoEnergetico: ", e);
        }
        return consumos;
    }
}
