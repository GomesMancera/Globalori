package watteco.repositorios;

import watteco.entidades.Endereco;
import watteco.infraestrutura.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EnderecoRepositorio implements _RepositorioCrud<Endereco> {

    @Override
    public void cadastrar(Endereco endereco) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "INSERT INTO ENDERECOS (LOGRADOURO, NUMERO, CIDADE, ESTADO, CEP) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, endereco.getLogradouro());
            stmt.setString(2, endereco.getNumero());
            stmt.setString(3, endereco.getCidade());
            stmt.setString(4, endereco.getEstado());
            stmt.setString(5, endereco.getCep());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void atualizar(Endereco endereco, int id) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "UPDATE ENDERECOS SET LOGRADOURO = ?, NUMERO = ?, CIDADE = ?, ESTADO = ?, CEP = ? WHERE ID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, endereco.getLogradouro());
            stmt.setString(2, endereco.getNumero());
            stmt.setString(3, endereco.getCidade());
            stmt.setString(4, endereco.getEstado());
            stmt.setString(5, endereco.getCep());
            stmt.setInt(6, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remover(int id) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "DELETE FROM ENDERECOS WHERE ID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Endereco> buscarPorId(int id) {
        Endereco endereco = null;
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "SELECT * FROM ENDERECOS WHERE ID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                endereco = new Endereco(rs.getInt("ID"), rs.getString("LOGRADOURO"), rs.getString("NUMERO"), rs.getString("CIDADE"), rs.getString("ESTADO"), rs.getString("CEP"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(endereco);
    }

    @Override
    public List<Endereco> listar() {
        List<Endereco> enderecos = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "SELECT * FROM ENDERECOS";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Endereco endereco = new Endereco(rs.getInt("ID"), rs.getString("LOGRADOURO"), rs.getString("NUMERO"), rs.getString("CIDADE"), rs.getString("ESTADO"), rs.getString("CEP"));
                enderecos.add(endereco);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enderecos;
    }
}
