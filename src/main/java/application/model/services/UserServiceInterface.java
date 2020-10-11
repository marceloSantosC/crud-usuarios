package application.model.services;


import application.model.entities.User;

public interface UserServiceInterface {
		public Object saveOrUpdate(User user);
	
		public Object create(User object);

		public Object getAll();

		public Object getById(Integer id);

		public Object update(User user);

		public Object deleteUsingId(Integer id);
}
