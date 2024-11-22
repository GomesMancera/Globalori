package watteco.repositorios;

import watteco.entidades.Contato;
import watteco.infraestrutura.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContatoRepositorio implements _RepositorioCrud<Contato> {

    @Override
    public void cadastrar(Contato contato) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "INSERT INTO T_QF_CONTATOS (NOME, TELEFONE, EMAIL) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getTelefone());
            stmt.setString(3, contato.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void atualizar(Contato contato, int id) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "UPDATE T_QF_CONTATOS SET NOME = ?, TELEFONE = ?, EMAIL = ? WHERE ID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getTelefone());
            stmt.setString(3, contato.getEmail());
            stmt.setInt(4, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remover(int id) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "DELETE FROM T_QF_CONTATOS WHERE ID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Contato> buscarPorId(int id) {
        Contato contato = null;
        try (Connection conn = DatabaseConfig.getConnection()) {
            String query = "SELECT * FROM T_QF_CONTATOS WHERE ID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                contato = new Contato(rs.getInt("ID"), rs.getString("NOME"), rs.getString("TELEFONE"), rs.getString("EMAIL"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(contato);
    }

    @Override
        public List<Contato> listar() {
            List<Contato> contatos = new ArrayList<>();
            try (Connection conn = DatabaseConfig.getConnection()) {
                String query = "SELECT * FROM T_QF_CONTATOS";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        Contato contato = new Contato(
                                rs.getInt("ID"),
                                rs.getString("NOME"),
                                rs.getString("TELEFONE"),
                                rs.getString("EMAIL")
                        );
                        contatos.add(contato);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return contatos;
        }

    }
