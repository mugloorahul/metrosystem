package com.metrosystem.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.dao.IMetroUserDao;
import com.metrosystem.dao.beans.MetroUserDTO;
import com.metrosystem.service.IMetroUserService;
import com.metrosystem.service.beans.MetroUserBO;
import com.metrosystem.service.exception.MetroSystemServiceException;
import com.metrosystem.service.utils.BankAccountBoDtoConverter;
import com.metrosystem.service.utils.MetroCardBoDtoConverter;
import com.metrosystem.service.utils.MetroUserBoDtoConverter;

@Component("userService")
@Transactional(readOnly=true,rollbackFor={Exception.class})
public class MetroUserServiceImpl implements IMetroUserService {

	@Autowired
	@Qualifier("userDao")
	private IMetroUserDao userDao;
	
	@Autowired
	@Qualifier("userBoDtoConverter")
	private MetroUserBoDtoConverter userBoDtoConverter;
	
	@Autowired
	@Qualifier("cardBoDtoConverter")
	private MetroCardBoDtoConverter cardBoDtoConverter;
	
	@Autowired
	@Qualifier("accountBoDtoConverter")
	private BankAccountBoDtoConverter accountBoDtoConverter;
	
	@Override
	public Integer createUser(String identifier, String name) throws MetroSystemServiceException {
		
		try{
			MetroUserDTO user = userBoDtoConverter.boToDto(null, identifier, name);
			return userDao.save(user);
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}

	@Override
	public MetroUserBO findUserByIdentifier(String identifier) throws MetroSystemServiceException {
		
		try{
			MetroUserDTO userDTO = userDao.queryUserByIdentifier(identifier);
			if(userDTO == null){
				return null;
			}
			
			MetroUserBO userBO = userBoDtoConverter.dtoToBo(userDTO.getUserId(), 
					                   userDTO.getUniqueIdentifier(), userDTO.getName());

			return userBO;
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}

	@Override
	public List<MetroUserBO> findUsersByName(String name) throws MetroSystemServiceException {
		
		try{
			List<MetroUserDTO> userDTOs = userDao.queryUsersByName(name);
			List<MetroUserBO> userBOs = new ArrayList<MetroUserBO>();
			
			for(MetroUserDTO userDTO : userDTOs){

				MetroUserBO userBO = userBoDtoConverter.dtoToBo(userDTO.getUserId(), 
						                   userDTO.getUniqueIdentifier(), userDTO.getName());
				
				userBOs.add(userBO);
			}
			
			return userBOs;
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
		
	}

}
