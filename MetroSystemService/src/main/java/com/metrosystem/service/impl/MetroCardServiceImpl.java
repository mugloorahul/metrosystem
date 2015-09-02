package com.metrosystem.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.dao.IMetroCardDao;
import com.metrosystem.dao.IMetroUserDao;
import com.metrosystem.dao.beans.MetroCardDTO;
import com.metrosystem.dao.beans.MetroUserDTO;
import com.metrosystem.service.IMetroCardService;
import com.metrosystem.service.beans.MetroCardBO;
import com.metrosystem.service.beans.MetroUserBO;
import com.metrosystem.service.exception.MetroSystemServiceException;
import com.metrosystem.service.exception.ServiceValidationException;
import com.metrosystem.service.utils.MetroCardBoDtoConverter;
import com.metrosystem.service.utils.MetroUserBoDtoConverter;

@Component("cardService")
@Transactional(readOnly=true,rollbackFor={Exception.class})
public class MetroCardServiceImpl implements IMetroCardService {

	@Autowired
	@Qualifier("userDao")
	private IMetroUserDao userDao;
	
	@Autowired
	@Qualifier("cardDao")
	private IMetroCardDao cardDao;
	
	@Autowired
	@Qualifier("cardBoDtoConverter")
	private MetroCardBoDtoConverter cardBoDtoConverter;
	
	@Autowired
	@Qualifier("userBoDtoConverter")
	private MetroUserBoDtoConverter userBoDtoConverter;
	
	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public Integer createMetroCard(String cardNumber,double balance, String userIdentifier) throws MetroSystemServiceException {
		
		try{
			MetroUserDTO userDTO = userDao.queryUserByIdentifier(userIdentifier);
			if(userDTO == null){
				throw new ServiceValidationException("No user exists with given identifier: " + userIdentifier);
			}
			//Check if metro card with given card number exists
			MetroCardDTO existingCard = cardDao.queryCardByNumber(cardNumber);
			if(existingCard != null){
				throw new ServiceValidationException("Metro card with number " + cardNumber + " already exists");
			}
			
			MetroCardDTO cardDTO = cardBoDtoConverter.boToDto(null,cardNumber, userDTO, balance);
			
			return cardDao.save(cardDTO);
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}

	@Override
	public List<MetroCardBO> findCardsForUser(String userIdentifier) throws MetroSystemServiceException {
		
		try{
			MetroUserDTO userDTO = userDao.queryUserByIdentifier(userIdentifier);
			if(userDTO == null){
				throw new ServiceValidationException("No user exists with given identifier: " + userDTO);
			}
			
			MetroUserBO userBO = userBoDtoConverter.dtoToBo(userDTO.getUserId(), userDTO.getUniqueIdentifier(), userDTO.getName());
			Set<MetroCardDTO> cardDTOs = userDTO.getMetroCards();
			//List<MetroCardDTO> cardDTOs = cardDao.queryCardsForUser(userIdentifier);
			List<MetroCardBO> cardBOs = new ArrayList<MetroCardBO>();
			
			for(MetroCardDTO cardDTO: cardDTOs){
				MetroCardBO cardBO = cardBoDtoConverter.dtoToBo(cardDTO.getCardId(), cardDTO.getCardNumber(),
						userBO, cardDTO.getBalance());
				cardBOs.add(cardBO);
			}
			
			return cardBOs;
			
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}

	@Override
	public MetroCardBO findCardByNumber(String cardNumber) throws MetroSystemServiceException {
		
		try{
		    MetroCardDTO cardDTO = cardDao.queryCardByNumber(cardNumber);
		    if(cardDTO == null){
		    	return null;
		    }
		    
		    MetroUserDTO userDTO = cardDTO.getUser();
		    MetroUserBO userBO= userBoDtoConverter.dtoToBo(userDTO.getUserId(), 
		    		userDTO.getUniqueIdentifier(), userDTO.getName());
		    
		    MetroCardBO cardBO = cardBoDtoConverter.
		    		     dtoToBo(cardDTO.getCardId(),cardDTO.getCardNumber(), 
		    		    		 userBO, cardDTO.getBalance());
		    
		    return cardBO;
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}

	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public void deleteCard(String cardNumber) throws MetroSystemServiceException {
		
		try{
			MetroCardDTO card = cardDao.queryCardByNumber(cardNumber);
			if(card == null){
				throw new IllegalAccessException("No metro card exists with given number: " + cardNumber);
			}
			
			cardDao.delete(card);
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
		
	}

}
