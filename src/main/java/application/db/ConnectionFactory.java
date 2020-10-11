package application.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConnectionFactory {
	public static Connection getConnection() throws DBException, IOException {
		try {
			Properties props = DBProperties.loadProperties();
			String dburl = props.getProperty("dburl");
			Connection connection = DriverManager.getConnection(dburl, props);
			return connection;
		} catch (SQLException | IOException e) {
			throw new DBException(e.getMessage());
		}
	}

	public static void closeConnection(Connection connection) throws DBException {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
	}

	public static void closeStatement(Statement st) throws DBException {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}

	public static void closeResultSet(ResultSet rs) throws DBException {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}

	public static void closeAll(Statement st, ResultSet rs, Connection connection) throws DBException {
		ConnectionFactory.closeResultSet(rs);
		ConnectionFactory.closeStatement(st);
		ConnectionFactory.closeConnection(connection);
	}

	public static void closeAll(Statement st, ResultSet rs) throws DBException {
		ConnectionFactory.closeResultSet(rs);
		ConnectionFactory.closeStatement(st);
	}

	public static void closeAll(PreparedStatement statement, Connection connection) throws DBException {
		ConnectionFactory.closeStatement(statement);
		ConnectionFactory.closeConnection(connection);
	}
}
