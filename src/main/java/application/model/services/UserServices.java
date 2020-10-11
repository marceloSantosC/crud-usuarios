package application.model.services;

import application.db.DBException;
import application.model.entities.User;
import application.model.interfaces.Dao;
import application.model.services.interfaces.UserServiceInterface;
import application.views.util.Alerts;
import javafx.scene.control.Alert.AlertType;

public class UserServices implements UserServiceInterface{
	Dao<User> userDao;
	
	public UserServices(Dao<User> userDao) {
		this.userDao = userDao;
	}

	@Override
	public Object create(User object) {

		try {
			User user = userDao.create(object);
			
			if(user.getIduser() != null) {
				return user;
			} else {
				return Alerts.getAlert("Erro", "Erro ao cadastrar usuário", "Não foi possível inserir o usuário", AlertType.ERROR);
			}
		} catch (DBException e) {
			return Alerts.getAlert("Erro", e.getHeader(), e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public Object getAll() {
		try {
			return userDao.getAll();
		} catch (DBException e) {
			return Alerts.getAlert("Erro", e.getHeader(), e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public Object getById(Integer id) {

		try {
			User user = userDao.getById(id);
			
			if(user != null) {
				return user;
			} else {
				return Alerts.getAlert("Erro", "Erro ao recuperar detalhes do usuário", "Não foi possível encontrar o usuário", AlertType.ERROR);
			}
		} catch (DBException e) {
			return Alerts.getAlert("Erro", e.getHeader(), e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public Object update(User user) {
		try {
			boolean update = userDao.update(user);
			
			if(update) {
				return update;
			} else {
				return Alerts.getAlert("Erro", "Erro ao atualizar usuário", "Não foi possível cadastrar o usuário", AlertType.ERROR);
			}
		} catch (DBException e) {
			return Alerts.getAlert("Erro", e.getHeader(), e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public Object deleteUsingId(Integer id) {
		try {
			boolean delete = userDao.deleteUsingId(id);
			
			if(delete) {
				return delete;
			} else {
				return Alerts.getAlert("Erro", "Erro ao excluir usuário", "Não foi possível excluir o usuário.", AlertType.ERROR);
			}
		} catch (DBException e) {
			return Alerts.getAlert("Erro", e.getHeader(), e.getMessage(), AlertType.ERROR);
		}
	}



}
