package watteco.repositorios;

import watteco.entidades.Energia;
import watteco.infraestrutura.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EnergiaRepositorio implements _RepositorioCrud<Energia> {

    @Override
    public void cadastrar(Energia energia) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "INSERT INTO T_QF_ENERGIAS (TIPO, POTENCIA) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, energia.getTipo());
                stmt.setDouble(2, energia.getPotencia());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao cadastrar Energia no banco de dados.", e);
        }
    }

    @Override
    public void atualizar(Energia energia, int id) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "UPDATE T_QF_ENERGIAS SET TIPO = ?, POTENCIA = ? WHERE ID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, energia.getTipo());
                stmt.setDouble(2, energia.getPotencia());
                stmt.setInt(3, id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar Energia no banco de dados.", e);
        }
    }

    @Override
    public void remover(int id) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "DELETE FROM T_QF_ENERGIAS WHERE ID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao remover Energia no banco de dados.", e);
        }
    }

    @Override
    public Optional<Energia> buscarPorId(int id) {
        Energia energia = null;
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "SELECT * FROM T_QF_ENERGIAS WHERE ID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    energia = new Energia(rs.getInt("ID"), rs.getString("TIPO"), rs.getDouble("POTENCIA"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar Energia por ID no banco de dados.", e);
        }
        return Optional.ofNullable(energia);
    }

    @Override
    public List<Energia> listar() {
        List<Energia> energias = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "SELECT * FROM T_QF_ENERGIAS";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Energia energia = new Energia(rs.getInt("ID"), rs.getString("TIPO"), rs.getDouble("POTENCIA"));
                    energias.add(energia);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar Energias no banco de dados.", e);
        }
        return energias;
    }
}
