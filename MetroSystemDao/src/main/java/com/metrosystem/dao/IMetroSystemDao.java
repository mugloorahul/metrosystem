package com.metrosystem.dao;

import java.io.Serializable;

import com.metrosystem.dao.exception.MetroSystemDaoException;

public interface IMetroSystemDao<K extends Serializable,T extends Serializable> {

	public K save(T entity) throws MetroSystemDaoException;
	
	public void delete(T entity) throws MetroSystemDaoException;
	
	public void deleteById(K identifier) throws MetroSystemDaoException;
	
	public void update(T entity) throws MetroSystemDaoException;
	
	public T queryById(K identifier) throws MetroSystemDaoException;
	
	
}
