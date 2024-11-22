package watteco.repositorios;

import watteco.entidades.Dispositivo;
import watteco.infraestrutura.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DispositivoRepositorio implements _RepositorioCrud<Dispositivo> {

    @Override
    public void cadastrar(Dispositivo dispositivo) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "INSERT INTO DISPOSITIVOS (NOME, TIPO, STATUS) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, dispositivo.getNome());
            stmt.setString(2, dispositivo.getTipo());
            stmt.setString(3, dispositivo.getStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void atualizar(Dispositivo dispositivo, int id) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "UPDATE DISPOSITIVOS SET NOME = ?, TIPO = ?, STATUS = ? WHERE ID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, dispositivo.getNome());
            stmt.setString(2, dispositivo.getTipo());
            stmt.setString(3, dispositivo.getStatus());
            stmt.setInt(4, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remover(int id) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "DELETE FROM DISPOSITIVOS WHERE ID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Dispositivo> buscarPorId(int id) {
        Dispositivo dispositivo = null;
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "SELECT * FROM DISPOSITIVOS WHERE ID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                dispositivo = new Dispositivo(rs.getInt("ID"), rs.getString("NOME"), rs.getString("TIPO"), rs.getString("STATUS"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(dispositivo);
    }

    @Override
    public List<Dispositivo> listar() {
        List<Dispositivo> dispositivos = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "SELECT * FROM DISPOSITIVOS";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Dispositivo dispositivo = new Dispositivo(rs.getInt("ID"), rs.getString("NOME"), rs.getString("TIPO"), rs.getString("STATUS"));
                dispositivos.add(dispositivo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dispositivos;
    }
}
