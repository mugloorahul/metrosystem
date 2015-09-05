package com.metrosystem.service;

import java.util.List;

import com.metrosystem.service.beans.MetroUserBO;
import com.metrosystem.service.exception.MetroSystemServiceException;

public interface IMetroUserService {

	public Integer createUser(String identifier, String name) throws MetroSystemServiceException;
	
	public MetroUserBO findUserByIdentifier(String identifier) throws MetroSystemServiceException;
	
	public List<MetroUserBO> findUsersByName(String name) throws MetroSystemServiceException;
}
