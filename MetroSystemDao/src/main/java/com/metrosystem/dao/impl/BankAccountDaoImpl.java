package com.metrosystem.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.dao.IBankAccountDao;
import com.metrosystem.dao.beans.BankAccountDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

@Repository("accountDao")
@Transactional(readOnly=true,rollbackFor={Exception.class})
public class BankAccountDaoImpl extends MetroSystemDaoImpl<Integer, BankAccountDTO> 
implements IBankAccountDao {

	
	public BankAccountDaoImpl(){
		super(BankAccountDTO.class);
	}

	@Override
	public List<BankAccountDTO> queryBankAccountsForUser(String userIdentifier) throws MetroSystemDaoException {
		
		try{
			String query = "SELECT account FROM BankAccountDTO account " +
		                   " WHERE account.user.uniqueIdentifier = ? " +
					       "  AND account.deleted != ?";
			
			return this.queryListOfEntities(query, userIdentifier,"Y");
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}
	}

	@Override
	public BankAccountDTO queryAccountByNumber(String accountNumber) throws MetroSystemDaoException {
		
		try{
			String query = "FROM BankAccountDTO where accountNumber = ? AND deleted != ?";
			List<BankAccountDTO> accounts = this.queryListOfEntities(query, accountNumber,"Y");
			if(accounts == null || accounts.size() == 0){
				return null;
			}
			
			return accounts.get(0);
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}
	}
}
