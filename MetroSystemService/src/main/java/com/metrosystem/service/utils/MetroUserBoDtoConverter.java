package com.metrosystem.service.utils;

import org.springframework.stereotype.Component;

import com.metrosystem.dao.beans.MetroUserDTO;
import com.metrosystem.service.beans.MetroUserBO;

@Component("userBoDtoConverter")
public class MetroUserBoDtoConverter {

	public MetroUserDTO boToDto(Integer id, String identifier,String name)
	{
		
		MetroUserDTO user = new MetroUserDTO(identifier, name);
		user.setUserId(id == null?0:id);
		
		return user;
	}
	
	public MetroUserBO dtoToBo(Integer id, String identifier, String name)
	{
		MetroUserBO user= new MetroUserBO(identifier, name);
		user.setUserId(id == null?0:id);
		
		return user;
	}
	
}
