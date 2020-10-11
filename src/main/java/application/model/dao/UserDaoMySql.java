package application.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.db.DBException;
import application.db.ConnectionFactory;
import application.model.entities.User;

public class UserDaoMySql implements Dao<User>{
	private User getUserFromResultSet(ResultSet rs, User user) throws SQLException {
		user.setIduser(rs.getInt("iduser"));
		user.setName(rs.getString("name"));
		user.setUsername(rs.getString("username"));
		user.setEmail(rs.getString("email"));
		
		return user;
	}
	
	// Verifica se um campo unique existe no DB e retorna null se não ou o nome do campo se sim
	private String checkUserExists(User user) throws DBException {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement("SELECT iduser, email, username, name FROM user WHERE iduser = ? OR username = ? OR email = ?");
			if (user.getIduser() != null) {
				statement.setInt(1, user.getIduser());
			} else {
				statement.setInt(1, -1);
			}
			statement.setString(2, user.getUsername());
			statement.setString(3, user.getEmail());
			
			rs = statement.executeQuery();
			if (rs.next()) {
				User userInDb = getUserFromResultSet(rs, new User());
				
				if (userInDb.getEmail().equalsIgnoreCase(user.getEmail())) {
					return "e-mail";
				} else if (userInDb.getUsername().equalsIgnoreCase(user.getUsername())) {
					return "nome de usuário";
				} else {
					return "id";
				}
			} else {
				return null;
			}	
		} catch (Exception e) {
			e.printStackTrace();
			throw new DBException(e.getMessage(), "Ocorreu um problema durante o acesso aos dados");
		} finally {
			ConnectionFactory.closeAll(statement, rs);
		}
	}

	@Override
	public User create(User user) throws DBException {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);
			
			String fieldsInDb = this.checkUserExists(user);
			
			if(fieldsInDb != null) {
				throw new DBException("Já existe um usuário com o " + fieldsInDb + " informado.", "Erro ao cadastrar usuário");
			}
			
			statement = connection.prepareStatement(
				"INSERT INTO user (iduser, name, username, email) VALUES (NULL, ?, ?, ?);",
				Statement.RETURN_GENERATED_KEYS
			);
			statement.setString(1, user.getName());
			statement.setString(2, user.getUsername());
			statement.setString(3, user.getEmail());
			
			if(statement.executeUpdate() > 0) {
				connection.commit();
				rs = statement.getGeneratedKeys();
				rs.next();
				user.setIduser(rs.getInt(1));
				return user;
			} else {
				connection.rollback();
				return user;
			}
		} catch (Exception e) {
			throw new DBException(e.getMessage(), "Ocorreu um problema durante o acesso aos dados");
		} finally {
			ConnectionFactory.closeAll(statement, rs);
		}
		
	}

	@Override
	public List<User> getAll() throws DBException {
		ResultSet rs = null;
		Connection connection = null;
		Statement statement = null;
		List<User> users = new ArrayList<User>();
		try {
			connection = ConnectionFactory.getConnection(); 
			statement = connection.createStatement();
			rs = statement.executeQuery("SELECT iduser, name, username, email FROM user;");

			while(rs.next()) {
				User user = new User();
				this.getUserFromResultSet(rs, user);
				users.add(user);
			}
		} catch (Exception e) {
			throw new DBException(e.getMessage());
		} finally {
			ConnectionFactory.closeAll(statement, rs, connection);
		}
		return users;
	}

	@Override
	public User getById(Integer id) throws DBException {
		
		if (id == null) {
			return null;
		}
		
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement("SELECT iduser, name, username, email FROM user WHERE iduser = ?");
			statement.setInt(1, id);
			rs = statement.executeQuery();
			if (rs.next()) {
				User user = new User();
				return this.getUserFromResultSet(rs, user);
			} else {
				return null;
			}	
		} catch (Exception e) {
			throw new DBException(e.getMessage(), "Ocorreu um problema durante o acesso aos dados");
		} finally {
			ConnectionFactory.closeAll(statement, rs);
		}
	}

	@Override
	public boolean update(User user) throws DBException {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);
			
			this.getById(user.getIduser());
			statement = connection.prepareStatement("UPDATE user SET name = ?, username = ?, email = ? WHERE iduser = ? ;");
			statement.setString(1, user.getName());
			statement.setString(2, user.getUsername());
			statement.setString(3, user.getEmail());
			statement.setInt(4, user.getIduser());
			
			if(statement.executeUpdate() > 0) {
				connection.commit();
				return true;
			} else {
				connection.rollback();
				return false;
			}

		} catch (Exception e) {
			throw new DBException(e.getMessage(), "Ocorreu um problema durante o acesso aos dados");
		} finally {
			ConnectionFactory.closeAll(statement, connection);
		}
	}

	@Override
	public boolean deleteUsingId(Integer id) throws DBException {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = ConnectionFactory.getConnection();
			connection.setAutoCommit(false);
			
			statement = connection.prepareStatement("DELETE FROM user WHERE iduser = ?");
			statement.setInt(1, id);
			
			if(statement.executeUpdate() > 0) {
				connection.commit();
				return true;
			} else {
				connection.rollback();
				return false;
			}

		} catch (Exception e) {
			throw new DBException(e.getMessage(), "Ocorreu um problema durante o acesso aos dados");
		} finally {
			ConnectionFactory.closeStatement(statement);
		}
	}
	
	
}
