package com.metrosystem.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.dao.IBankAccountDao;
import com.metrosystem.dao.IMetroUserDao;
import com.metrosystem.dao.beans.BankAccountDTO;
import com.metrosystem.dao.beans.MetroUserDTO;
import com.metrosystem.service.IBankAccountService;
import com.metrosystem.service.beans.BankAccountBO;
import com.metrosystem.service.beans.MetroUserBO;
import com.metrosystem.service.exception.MetroSystemServiceException;
import com.metrosystem.service.utils.BankAccountBoDtoConverter;
import com.metrosystem.service.utils.MetroUserBoDtoConverter;

@Component("accountService")
@Transactional(readOnly=true,rollbackFor={Exception.class})
public class BankAccountServiceImpl implements IBankAccountService {

	
	@Autowired
	@Qualifier("userDao")
	private IMetroUserDao userDao;
	
	@Autowired
	@Qualifier("accountDao")
	private IBankAccountDao accountDao;
	
	@Autowired
	@Qualifier("accountBoDtoConverter")
	private BankAccountBoDtoConverter accountBoDtoConverter;
	
	@Autowired
	@Qualifier("userBoDtoConverter")
	private MetroUserBoDtoConverter userBoDtoConverter;
	
	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public Integer openBankAccount(String accountNumber, double balance,String userIdentifier) 
	throws MetroSystemServiceException {
		
		try{
			MetroUserDTO userDTO = userDao.queryUserByIdentifier(userIdentifier);
			if(userDTO == null){
				throw new IllegalArgumentException("No user exists with given identifier: " + userIdentifier);
			}
			BankAccountDTO account = accountBoDtoConverter.boToDtoConverter(null,accountNumber, balance, userDTO);
			return accountDao.save(account);
			
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}

	@Override
	public List<BankAccountBO> getBankAccountsForUser(String userIdentifier) throws MetroSystemServiceException{
		try{
			MetroUserDTO userDTO = userDao.queryUserByIdentifier(userIdentifier);
			if(userDTO == null){
				throw new IllegalArgumentException("No user exists with given identifier: " + userDTO);
			}
			MetroUserBO userBO = userBoDtoConverter.dtoToBo(userDTO.getUserId(), userDTO.getUniqueIdentifier(), userDTO.getName());
			
			
			Set<BankAccountDTO> accountDTOs = userDTO.getBankAccounts();
			//List<BankAccountDTO> accountDTOs = accountDao.queryBankAccountsForUser(userIdentifier);
			List<BankAccountBO> accountBOs = new ArrayList<BankAccountBO>();
			
			for(BankAccountDTO accountDTO : accountDTOs){
				BankAccountBO accountBO = accountBoDtoConverter.dtoToBoConverter(accountDTO.getAccountId(),
						accountDTO.getAccountNumber(), accountDTO.getBalance(), userBO);
				accountBOs.add(accountBO);
			}
			
			return accountBOs;
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}

	@Override
	public BankAccountBO findAccountByNumber(String accountNumber) throws MetroSystemServiceException {
		
		try{
			BankAccountDTO accountDTO = accountDao.queryAccountByNumber(accountNumber);
			if(accountDTO == null){
				return null;
			}
			
			MetroUserDTO userDTO = accountDTO.getUser();
			MetroUserBO userBO = userBoDtoConverter.dtoToBo(userDTO.getUserId(), userDTO.getUniqueIdentifier(), userDTO.getName());
			
			BankAccountBO accountBO = accountBoDtoConverter.
					                   dtoToBoConverter(accountDTO.getAccountId(), accountDTO.getAccountNumber(), 
					                		            accountDTO.getBalance(), userBO);
			
			return accountBO;
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}

	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public void closeBankAccount(String accountNumber) throws MetroSystemServiceException {
		
		try{
			BankAccountDTO account = accountDao.queryAccountByNumber(accountNumber);
			if(account == null){
				throw new IllegalArgumentException("No account exists with given account number: " + accountNumber);
			}
			accountDao.delete(account);
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
		
	}

}
