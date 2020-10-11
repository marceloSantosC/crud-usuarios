package application.model.dao;

import java.util.List;

import application.db.DBException;
import application.model.entities.User;


public interface Dao<T> {
	public T create(T object) throws DBException;

	public List<T> getAll() throws DBException;

	public T getById(Integer id) throws DBException;

	public boolean update(User user) throws DBException;

	public boolean deleteUsingId(Integer id) throws DBException;
}
