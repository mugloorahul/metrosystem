package com.metrosystem.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.dao.IMetroUserDao;
import com.metrosystem.dao.beans.MetroUserDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

@Repository("userDao")
@Transactional(readOnly=true,rollbackFor={Exception.class})
public class MetroUserDaoImpl extends MetroSystemDaoImpl<Integer, MetroUserDTO> implements
		IMetroUserDao {

	public MetroUserDaoImpl(){
		super(MetroUserDTO.class);
	}

	@Override
	public MetroUserDTO queryUserByIdentifier(String identifier) throws MetroSystemDaoException {
		
		try{
			String query = "SELECT user "+
		                   " FROM MetroUserDTO user " +
					       " WHERE user.uniqueIdentifier=?";
			
			List<MetroUserDTO> users = this.queryListOfEntities(query, identifier);
			
			if(users == null || users.size() == 0){
				return null;
			}
			
			return users.get(0);
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}
	}

	@Override
	public List<MetroUserDTO> queryUsersByName(String name) throws MetroSystemDaoException {
		
		try{
			String query = "SELECT user "+
		                   " FROM MetroUserDTO user " +
					       " WHERE user.name=?";
			
			List<MetroUserDTO> users = this.queryListOfEntities(query, name);
			
			return users;
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}
	}
	

}
