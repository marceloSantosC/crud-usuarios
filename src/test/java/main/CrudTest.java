package main;

import java.util.List;

import application.model.dao.UserDaoMySql;
import application.model.entities.User;

public class CrudTest {
	public static void main(String[] args) throws Exception {
		UserDaoMySql userDAO = new UserDaoMySql();
		User user = new User(null, "user", "user", "user@gmail.com");
		
		
		// Create test
		user = userDAO.create(user);
		if (user.getIduser() == null) throw new Exception("Retorno inválido, create falhou no teste");
		user = userDAO.getById(user.getIduser());
		if (user == null) throw new Exception("Retorno inválido, create falhou no teste");
		
		
		// getAll test
		List<User> users = userDAO.getAll();
		if (users == null) throw new Exception("Retorno inválido, getAll falhou no teste");
		if (!users.contains(user)) throw new Exception("Retorno inválido, getAll falhou no teste");
		
		// update test
		user.setName("User");
		boolean result = userDAO.update(user);
		if (!result) throw new Exception("Retorno inválido, update falhou no teste");
		User userUpToDate = userDAO.getById(user.getIduser());
		if (!userUpToDate.getName().equals("User"))  throw new Exception("Retorno inválido, update falhou no teste");
		
		// getById test
		User returnedUser = userDAO.getById(user.getIduser());
		if (returnedUser == null) throw new Exception("Retorno inválido, getById falhou no teste");
		if (!returnedUser.equals(user)) throw new Exception("Retorno inválido, getById falhou no teste");


		// delete test
		boolean deleteResult = userDAO.deleteUsingId(user.getIduser());
		if (!deleteResult) throw new Exception("Retorno inválido");
		if (userDAO.getById(user.getIduser()) != null) throw new Exception("Retorno inválido, delete falhou no teste");
	}
}
