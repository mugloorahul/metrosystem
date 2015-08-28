package com.metrosystem.dao;

import java.util.List;

import com.metrosystem.dao.beans.MetroUserDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

public interface IMetroUserDao extends IMetroSystemDao<Integer, MetroUserDTO> {

	public MetroUserDTO queryUserByIdentifier(String identifier) throws MetroSystemDaoException;
	
	public List<MetroUserDTO> queryUsersByName(String name) throws MetroSystemDaoException;
}
