package com.metrosystem.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.dao.IBankAccountDao;
import com.metrosystem.dao.ICreditCardDao;
import com.metrosystem.dao.IDebitCardDao;
import com.metrosystem.dao.IMetroUserDao;
import com.metrosystem.dao.beans.BankAccountDTO;
import com.metrosystem.dao.beans.CreditCardDTO;
import com.metrosystem.dao.beans.DebitCardDTO;
import com.metrosystem.dao.beans.MetroUserDTO;
import com.metrosystem.service.IPaymentMethodService;
import com.metrosystem.service.beans.BankAccountBO;
import com.metrosystem.service.beans.CreditCardBO;
import com.metrosystem.service.beans.DebitCardBO;
import com.metrosystem.service.beans.MetroUserBO;
import com.metrosystem.service.exception.MetroSystemServiceException;
import com.metrosystem.service.utils.BankAccountBoDtoConverter;
import com.metrosystem.service.utils.CreditCardBoDtoConverter;
import com.metrosystem.service.utils.DebitCardBoDtoConverter;
import com.metrosystem.service.utils.MetroUserBoDtoConverter;
import com.metrosystem.service.utils.NetBankingBoDtoConverter;

@Component("paymentMethodService")
@Transactional(readOnly=true,rollbackFor={Exception.class})
public class PaymentMethodServiceImpl implements IPaymentMethodService{

	@Autowired
	@Qualifier("creditCardDao")
	private ICreditCardDao creditCardDao;
	
	@Autowired
	@Qualifier("debitCardDao")
	private IDebitCardDao debitCardDao;
	
	@Autowired
	@Qualifier("accountDao")
	private IBankAccountDao accountDao;
	
	@Autowired
	@Qualifier("userDao")
	private IMetroUserDao userDao;
	
	@Autowired
	@Qualifier("creditCardBoDtoConverter")
	private CreditCardBoDtoConverter creditCardConverter;
	
	@Autowired
	@Qualifier("debitCardBoDtoConverter")
	private DebitCardBoDtoConverter debitCardConverter;
	
	@Autowired
	@Qualifier("netBankingBoDtoConverter")
	private NetBankingBoDtoConverter netBankingConverter;
	
	@Autowired
	@Qualifier("accountBoDtoConverter")
	private BankAccountBoDtoConverter accountBoDtoConverter;
	
	@Autowired
	@Qualifier("userBoDtoConverter")
	private MetroUserBoDtoConverter userBoDtoConverter;
	
	@Override
	public CreditCardBO findCreditCardByNumber(String creditCardNumber) throws MetroSystemServiceException {
		
		try{
			CreditCardDTO cardDTO = creditCardDao.queryCardByNumber(creditCardNumber);
			if(cardDTO == null){
				return null;
			}
			
			BankAccountDTO accountDTO = cardDTO.getAccount();
			MetroUserDTO userDTO = accountDTO.getUser();
			MetroUserBO userBO = userBoDtoConverter.dtoToBo(userDTO.getUserId(), 
					                                   userDTO.getUniqueIdentifier(), 
					                                   userDTO.getName());
			BankAccountBO accountBO = accountBoDtoConverter.
					                   dtoToBoConverter(accountDTO.getAccountId(), 
					                		   accountDTO.getAccountNumber(), 
					                		   accountDTO.getBalance(), userBO);
			
			CreditCardBO cardBO = creditCardConverter.
					dtoToBo(cardDTO.getId(), 
							cardDTO.getPaymentId(), 
							cardDTO.getCvvNumber(), 
							cardDTO.getExpiryMonth(), 
							cardDTO.getExpiryYear(), 
							cardDTO.getCreditLimit(), 
							accountBO);
			
			return cardBO;
			
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}

	@Override
	public List<CreditCardBO> findCreditCardsByUser(String userIdentifier) throws MetroSystemServiceException {
		
		try{
			MetroUserDTO userDTO = userDao.queryUserByIdentifier(userIdentifier);
			if(userDTO == null){
				throw new IllegalArgumentException("No user exists with given user identifier: " + userIdentifier);
			}
			
			List<CreditCardDTO> cardDTOs = creditCardDao.queryCardsByUser(userIdentifier);
			List<CreditCardBO> cardBOs = new ArrayList<CreditCardBO>();
			if(cardDTOs == null || cardDTOs.size() == 0){
				return cardBOs;
			}
			
			MetroUserBO userBO = userBoDtoConverter.dtoToBo(userDTO.getUserId(), 
                    userDTO.getUniqueIdentifier(), 
                    userDTO.getName());
			
			for(CreditCardDTO cardDTO : cardDTOs){
				BankAccountDTO accountDTO = cardDTO.getAccount();
				BankAccountBO accountBO = accountBoDtoConverter.dtoToBoConverter(accountDTO.getAccountId(), accountDTO.getAccountNumber(), 
						                                                         accountDTO.getBalance(), userBO);
				
				CreditCardBO cardBO = creditCardConverter.
		                 dtoToBo(cardDTO.getId(), cardDTO.getPaymentId(), 
		                		 cardDTO.getCvvNumber(), cardDTO.getExpiryMonth(), 
		                		 cardDTO.getExpiryYear(), cardDTO.getCreditLimit(), accountBO);

                cardBOs.add(cardBO);
				
			}
			
			return cardBOs;
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}

	@Override
	public List<CreditCardBO> findCreditCardsByAccountNumber(String accountNumber) throws MetroSystemServiceException {
		
		try{
			BankAccountDTO accountDTO = accountDao.queryAccountByNumber(accountNumber);
			if(accountDTO == null){
				throw new IllegalArgumentException("No account exists with given account number: " + accountNumber);
			}
			
			List<CreditCardDTO> cardDTOs = creditCardDao.queryCardByAccount(accountNumber);
			
			List<CreditCardBO> cardBOs = new ArrayList<CreditCardBO>();
			if(cardDTOs == null || cardDTOs.size() == 0){
				return cardBOs;
			}
			
			MetroUserDTO userDTO = accountDTO.getUser();
			MetroUserBO userBO = userBoDtoConverter.dtoToBo(userDTO.getUserId(), userDTO.getUniqueIdentifier(), userDTO.getName());
			BankAccountBO accountBO = accountBoDtoConverter.dtoToBoConverter(accountDTO.getAccountId(), 
					                                                         accountDTO.getAccountNumber(), accountDTO.getBalance(), userBO);
			
			for(CreditCardDTO cardDTO: cardDTOs){
				CreditCardBO cardBO = creditCardConverter.
						                 dtoToBo(cardDTO.getId(), cardDTO.getPaymentId(), 
						                		 cardDTO.getCvvNumber(), cardDTO.getExpiryMonth(), 
						                		 cardDTO.getExpiryYear(), cardDTO.getCreditLimit(), accountBO);
				
				cardBOs.add(cardBO);
			}
			
			return cardBOs;
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}

	}

	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public void deleteCreditCard(String creditCardNumber) throws MetroSystemServiceException {
				
		try{
			CreditCardDTO card = creditCardDao.queryCardByNumber(creditCardNumber);
			if(card == null){
				throw new IllegalArgumentException("No credit card exists with given number: " + creditCardNumber);
			}
			
			creditCardDao.delete(card);
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}

	}

	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public Integer createCreditCard(CreditCardBO creditCard,String accountNumber) throws MetroSystemServiceException {
		
		
		try{
			BankAccountDTO accountDTO = accountDao.queryAccountByNumber(accountNumber);
			if(accountDTO == null){
				throw new IllegalArgumentException("No account exists with given account number: " + accountNumber);
			}
						
			CreditCardDTO cardDTO = creditCardConverter.
					                   boToDto(null, 
					                		   creditCard.getPaymentId(), 
					                		   creditCard.getCvvNumber(), 
					                		   creditCard.getExpiryMonth(), 
					                		   creditCard.getExpiryYear(), 
					                		   creditCard.getCreditLimit(), accountDTO);
			
			return creditCardDao.save(cardDTO);
			
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}

	}

	@Override
	public DebitCardBO findDebitCardByNumber(String debitCardNumber) throws MetroSystemServiceException {
		
		try{
			DebitCardDTO cardDTO = debitCardDao.queryCardByNumber(debitCardNumber);
			if(cardDTO== null){
				return null;
			}
			
			BankAccountDTO accountDTO = cardDTO.getAccount();
			MetroUserDTO userDTO = accountDTO.getUser();
			MetroUserBO userBO = userBoDtoConverter.dtoToBo(userDTO.getUserId(), 
					                                        userDTO.getUniqueIdentifier(), 
					                                        userDTO.getName());
			
			BankAccountBO accountBO = accountBoDtoConverter.
					                   dtoToBoConverter(accountDTO.getAccountId(), 
					                		            accountDTO.getAccountNumber(), 
					                		            accountDTO.getBalance(), userBO);
			
			DebitCardBO cardBO = debitCardConverter.
					                dtoToBo(cardDTO.getId(), 
					                		cardDTO.getPaymentId(), 
					                		cardDTO.getCvvNumber(), 
					                		cardDTO.getExpiryMonth(), 
					                		cardDTO.getExpiryYear(), 
					                		accountBO);
			
			return cardBO;
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}

	@Override
	public List<DebitCardBO> findDebitCardsByUser(String userIdentifier) throws MetroSystemServiceException {
		
		try{
		    MetroUserDTO userDTO = userDao.queryUserByIdentifier(userIdentifier);
		    if(userDTO == null){
		    	throw new IllegalArgumentException("No user exists with given identifier: "+userIdentifier);
		    }
		    List<DebitCardDTO> cardDTOs = debitCardDao.queryCardsByUser(userIdentifier);
		    List<DebitCardBO> cardBOs = new ArrayList<DebitCardBO>();
		    if(cardDTOs == null || cardDTOs.size() ==0){
		    	return cardBOs;
		    }
		    
		    MetroUserBO userBO = userBoDtoConverter.dtoToBo(userDTO.getUserId(), 
		    		                                        userDTO.getUniqueIdentifier(), 
		    		                                        userDTO.getName());
		    
		    for(DebitCardDTO cardDTO : cardDTOs){
		    	BankAccountDTO accountDTO = cardDTO.getAccount();
		    	BankAccountBO accountBO = accountBoDtoConverter.
		    			                   dtoToBoConverter(accountDTO.getAccountId(), 
		    			                		            accountDTO.getAccountNumber(), 
		    			                		            accountDTO.getBalance(), 
		    			                		            userBO);
		    	
		    	DebitCardBO cardBO = debitCardConverter.dtoToBo(cardDTO.getId(), 
		    			                                        cardDTO.getPaymentId(), 
		    			                                        cardDTO.getCvvNumber(), 
		    			                                        cardDTO.getExpiryMonth(), 
		    			                                        cardDTO.getExpiryYear(), 
		    			                                        accountBO);
		    	
		    	cardBOs.add(cardBO);
		    }
		    
		    return cardBOs;
		    
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}

	@Override
	public List<DebitCardBO> findDebitCardsByAccountNumber(String accountNumber) throws MetroSystemServiceException {
		
		try{
			BankAccountDTO accountDTO = accountDao.queryAccountByNumber(accountNumber);
			if(accountDTO == null){
				throw new IllegalArgumentException("No account exists with given account number: " + accountNumber);
			}
			List<DebitCardDTO> cardDTOs = debitCardDao.queryCardByAccount(accountNumber);
			List<DebitCardBO> cardBOs = new ArrayList<DebitCardBO>();
			if(cardBOs == null || cardBOs.size() ==0){
				return cardBOs;
			}
			
			MetroUserDTO userDTO = accountDTO.getUser();
			MetroUserBO userBO = userBoDtoConverter.dtoToBo(userDTO.getUserId(), 
					                                        userDTO.getUniqueIdentifier(), 
					                                        userDTO.getName());
			BankAccountBO accountBO = accountBoDtoConverter.dtoToBoConverter(accountDTO.getAccountId(), 
					                                                         accountDTO.getAccountNumber(), 
					                                                         accountDTO.getBalance(), userBO);
			
			for(DebitCardDTO cardDTO : cardDTOs){
				DebitCardBO cardBO = debitCardConverter.dtoToBo(cardDTO.getId(), 
						                                        cardDTO.getPaymentId(), 
						                                        cardDTO.getCvvNumber(), 
						                                        cardDTO.getExpiryMonth(), 
						                                        cardDTO.getExpiryYear(), 
						                                        accountBO);
				cardBOs.add(cardBO);
			}
			
			return cardBOs;
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}

	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public void deleteDebitCard(String debitCardNumber) throws MetroSystemServiceException {
		
		try{
			DebitCardDTO card = debitCardDao.queryCardByNumber(debitCardNumber);
			if(card ==null){
				throw new IllegalArgumentException("No debit card exists with given number: " + debitCardNumber);
			}
			debitCardDao.delete(card);
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
		
	}

	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public Integer createDebitCard(DebitCardBO debitCard, String accountNumber) throws MetroSystemServiceException {
		
		try{
			BankAccountDTO account = accountDao.queryAccountByNumber(accountNumber);
			if(account == null){
				throw new IllegalArgumentException("No account exists with given account number: " + accountNumber);
			}
			DebitCardDTO cardDTO = debitCardConverter.boToDto(null, debitCard.getPaymentId(), debitCard.getCvvNumber(), 
					                                          debitCard.getExpiryMonth(), debitCard.getExpiryYear(), account);
			
			return debitCardDao.save(cardDTO);
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}

}
