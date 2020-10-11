package test;

import java.util.List;

import application.db.DBException;
import application.model.dao.UserDaoMySql;
import application.model.entities.User;

public class CrudTest {
	public static void main(String[] args) throws DBException {
		UserDaoMySql userDAO = new UserDaoMySql();
		User user = new User(null, "user", "user", "user@gmail.com");
		
		// getAll test
		List<User> users = userDAO.getAll();
		for(User userReturned: users) {
			System.out.println(userReturned);
		}
		
		// Create test
		user = userDAO.create(user);
		System.out.println(user.getIduser() != null);
	
		
		// update test
		user.setName("User");
		System.out.println(userDAO.update(user));
		
		// getById tests
		System.out.println(userDAO.getById(user.getIduser()));	

		// delete test
		// System.out.println(userDAO.deleteUsingId(user.getIduser()));
		System.out.println(userDAO.getById(user.getIduser()));	
	}
}
