package watteco.infraestrutura;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    private static final Logger logger = LogManager.getLogger(DatabaseConfig.class);

    private static String USER = "RM555427";
    private static String PASSWORD = "060104";
    private static String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCl";

    public static Connection getConnection() throws SQLException {
        try {
            logger.info("Tentando abrir uma conexão com o banco de dados...");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            logger.info("Conexão com o banco de dados aberta com sucesso: " + connection);
            return connection;
        } catch (SQLException e) {
            logger.error("Erro ao abrir conexão com o banco de dados.", e);
            throw e; // Relança a exceção para que o código chamador possa tratá-la
        }
    }
}
