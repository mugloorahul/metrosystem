package com.metrosystem.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.dao.IBankAccountDao;
import com.metrosystem.dao.ICreditCardDao;
import com.metrosystem.dao.IDebitCardDao;
import com.metrosystem.dao.IMetroUserDao;
import com.metrosystem.dao.INetBankingDao;
import com.metrosystem.dao.beans.BankAccountDTO;
import com.metrosystem.dao.beans.CreditCardDTO;
import com.metrosystem.dao.beans.DebitCardDTO;
import com.metrosystem.dao.beans.MetroUserDTO;
import com.metrosystem.dao.beans.NetBankingDTO;
import com.metrosystem.service.IBankService;
import com.metrosystem.service.beans.BankAccountBO;
import com.metrosystem.service.beans.CreditCardBO;
import com.metrosystem.service.beans.DebitCardBO;
import com.metrosystem.service.beans.MetroUserBO;
import com.metrosystem.service.beans.NetBankingBO;
import com.metrosystem.service.exception.MetroSystemServiceException;
import com.metrosystem.service.exception.ServiceValidationException;
import com.metrosystem.service.utils.BankAccountBoDtoConverter;
import com.metrosystem.service.utils.CreditCardBoDtoConverter;
import com.metrosystem.service.utils.DebitCardBoDtoConverter;
import com.metrosystem.service.utils.MetroUserBoDtoConverter;
import com.metrosystem.service.utils.NetBankingBoDtoConverter;

@Component("bankService")
@Transactional(readOnly=true,rollbackFor={Exception.class})
public class BankServiceImpl implements IBankService {

	
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
	
	@Autowired
	@Qualifier("creditCardDao")
	private ICreditCardDao creditCardDao;
	
	@Autowired
	@Qualifier("debitCardDao")
	private IDebitCardDao debitCardDao;
	
	@Autowired
	@Qualifier("netBankingDao")
	private INetBankingDao netBankingDao;
	
	@Autowired
	@Qualifier("creditCardBoDtoConverter")
	private CreditCardBoDtoConverter creditCardConverter;
	
	@Autowired
	@Qualifier("debitCardBoDtoConverter")
	private DebitCardBoDtoConverter debitCardConverter;
	
	@Autowired
	@Qualifier("netBankingBoDtoConverter")
	private NetBankingBoDtoConverter netBankingConverter;
	
	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public Integer openBankAccount(String accountNumber, double balance,String userIdentifier) 
	throws MetroSystemServiceException {
		
		try{
			//Check if bank account  with given number exists
			BankAccountDTO existingAccount = accountDao.queryBankAccountByNumber(accountNumber);
			if(existingAccount != null){
				throw new ServiceValidationException("Bank account with given number" + accountNumber + " already exists.");
			}
			
			MetroUserDTO userDTO = userDao.queryUserByIdentifier(userIdentifier);
			if(userDTO == null){
				throw new ServiceValidationException("No user exists with given identifier: " + userIdentifier);
			}
			BankAccountDTO account = accountBoDtoConverter.boToDtoConverter(null,accountNumber, balance, userDTO);
			return accountDao.save(account);
			
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}

	@Override
	public List<BankAccountBO> getActiveBankAccountsForUser(String userIdentifier) throws MetroSystemServiceException{
		try{
			MetroUserDTO userDTO = userDao.queryUserByIdentifier(userIdentifier);
			if(userDTO == null){
				throw new ServiceValidationException("No user exists with given identifier: " + userDTO);
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
	public BankAccountBO findActiveBankAccountByNumber(String accountNumber) throws MetroSystemServiceException {
		
		try{
			BankAccountDTO accountDTO = accountDao.queryBankAccountByNumber(accountNumber);
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
			BankAccountDTO account = accountDao.queryBankAccountByNumber(accountNumber);
			if(account == null){
				throw new ServiceValidationException("No account exists with given account number: " + accountNumber);
			}
			accountDao.delete(account);
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
		
	}
	
	
	@Override
	public CreditCardBO findActiveCreditCardByNumber(String creditCardNumber) throws MetroSystemServiceException {
		
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
	public List<CreditCardBO> findActiveCreditCardsByUser(String userIdentifier) throws MetroSystemServiceException {
		
		try{
			MetroUserDTO userDTO = userDao.queryUserByIdentifier(userIdentifier);
			if(userDTO == null){
				throw new ServiceValidationException("No user exists with given user identifier: " + userIdentifier);
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
	public List<CreditCardBO> findActiveCreditCardsByAccountNumber(String accountNumber) throws MetroSystemServiceException {
		
		try{
			BankAccountDTO accountDTO = accountDao.queryBankAccountByNumber(accountNumber);
			if(accountDTO == null){
				throw new ServiceValidationException("No account exists with given account number: " + accountNumber);
			}
			
			List<CreditCardDTO> cardDTOs = creditCardDao.queryCardsByAccount(accountNumber);
			
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
				throw new ServiceValidationException("No credit card exists with given number: " + creditCardNumber);
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
			BankAccountDTO accountDTO = accountDao.queryBankAccountByNumber(accountNumber);
			if(accountDTO == null){
				throw new ServiceValidationException("No account exists with given account number: " + accountNumber);
			}
			//Check if credit card with given number exists
			CreditCardDTO existingCard = creditCardDao.queryCardByNumber(creditCard.getPaymentId());
			if(existingCard != null){
				throw new ServiceValidationException("Credit card with number " + creditCard.getPaymentId() + " already exists.");
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
	public DebitCardBO findActiveDebitCardByNumber(String debitCardNumber) throws MetroSystemServiceException {
		
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
	public List<DebitCardBO> findActiveDebitCardsByUser(String userIdentifier) throws MetroSystemServiceException {
		
		try{
		    MetroUserDTO userDTO = userDao.queryUserByIdentifier(userIdentifier);
		    if(userDTO == null){
		    	throw new ServiceValidationException("No user exists with given identifier: "+userIdentifier);
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
	public List<DebitCardBO> findActiveDebitCardsByAccountNumber(String accountNumber) throws MetroSystemServiceException {
		
		try{
			BankAccountDTO accountDTO = accountDao.queryBankAccountByNumber(accountNumber);
			if(accountDTO == null){
				throw new ServiceValidationException("No account exists with given account number: " + accountNumber);
			}
			List<DebitCardDTO> cardDTOs = debitCardDao.queryCardsByAccount(accountNumber);
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
				throw new ServiceValidationException("No debit card exists with given number: " + debitCardNumber);
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
			BankAccountDTO account = accountDao.queryBankAccountByNumber(accountNumber);
			if(account == null){
				throw new ServiceValidationException("No account exists with given account number: " + accountNumber);
			}
			
			DebitCardDTO existingCard = debitCardDao.queryCardByNumber(debitCard.getPaymentId());
			if(existingCard != null){
				throw new ServiceValidationException("Debit card with number " + debitCard.getPaymentId() + " already exists.");
			}
			DebitCardDTO cardDTO = debitCardConverter.boToDto(null, debitCard.getPaymentId(), debitCard.getCvvNumber(), 
					                                          debitCard.getExpiryMonth(), debitCard.getExpiryYear(), account);
			
			return debitCardDao.save(cardDTO);
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}

	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public Integer activateNetBanking(NetBankingBO netBanking,String accountNumber) throws MetroSystemServiceException {
		
		try{
			BankAccountDTO account = accountDao.queryBankAccountByNumber(accountNumber);
			if(account == null){
				throw new ServiceValidationException("No bank account exists with given number: " + accountNumber);
			}
			//Check if customer id already exists
			NetBankingDTO existingNB = netBankingDao.queryByCustomerId(netBanking.getPaymentId());
			if(existingNB != null){
				throw new ServiceValidationException("Customer id " + netBanking.getPaymentId() + " already exists");
			}
			
			NetBankingDTO dto = netBankingConverter.boToDto(null, netBanking.getPaymentId(), netBanking.getPassword(), account);
			
			return netBankingDao.save(dto);
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}

	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public void deactivateNetBanking(String customerId) throws MetroSystemServiceException {
		
		try{
			NetBankingDTO nb = netBankingDao.queryByCustomerId(customerId);
			if(nb == null){
				throw new ServiceValidationException("No net banking exists for given customer id: " + customerId);
			}
			
			netBankingDao.delete(nb);
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
		
	}

	@Override
	public NetBankingBO findActiveNetBankingByCustomerId(String customerId) throws MetroSystemServiceException {
		
		try{
			NetBankingDTO nbDTO = netBankingDao.queryByCustomerId(customerId);
			if(nbDTO == null){
				return null;
			}
			BankAccountDTO accountDTO = nbDTO.getAccount();
			MetroUserDTO userDTO = accountDTO.getUser();
			
			MetroUserBO userBO = userBoDtoConverter.dtoToBo(userDTO.getUserId(), 
                                                            userDTO.getUniqueIdentifier(), 
                                                            userDTO.getName());
			
           BankAccountBO accountBO = accountBoDtoConverter.
                                       dtoToBoConverter(accountDTO.getAccountId(), 
 		                                                accountDTO.getAccountNumber(), 
 		                                                accountDTO.getBalance(), userBO);
           
           NetBankingBO nbBO = netBankingConverter.dtoToBo(nbDTO.getId(), nbDTO.getPaymentId(), nbDTO.getPassword(), accountBO);
           
           return nbBO;
			
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}

	@Override
	public NetBankingBO findActiveNetBankingByAccount(String accountNumber) throws MetroSystemServiceException {
		
		try{
			BankAccountDTO accountDTO = accountDao.queryBankAccountByNumber(accountNumber);
			if(accountDTO == null){
				throw new ServiceValidationException("No bank account exists with given number: " + accountNumber);
			}
			
			NetBankingDTO nbDTO = netBankingDao.queryByAccountNumber(accountNumber);
			if(nbDTO == null){
				return null;
			}
			MetroUserDTO userDTO = accountDTO.getUser();
			
			MetroUserBO userBO = userBoDtoConverter.dtoToBo(userDTO.getUserId(), 
                                                            userDTO.getUniqueIdentifier(), 
                                                            userDTO.getName());
			
           BankAccountBO accountBO = accountBoDtoConverter.
                                       dtoToBoConverter(accountDTO.getAccountId(), 
 		                                                accountDTO.getAccountNumber(), 
 		                                                accountDTO.getBalance(), userBO);
           
           NetBankingBO nbBO = netBankingConverter.dtoToBo(nbDTO.getId(), nbDTO.getPaymentId(), nbDTO.getPassword(), accountBO);
           
           return nbBO;
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}

	@Override
	public List<NetBankingBO> findActiveNetBankingByUser(String userIdentifier) throws MetroSystemServiceException {
		
		try{
			MetroUserDTO userDTO = userDao.queryUserByIdentifier(userIdentifier);
			if(userDTO == null){
				throw new ServiceValidationException("No user exists with given identifier: " + userIdentifier);
			}
			
			List<NetBankingDTO> nbDTOs = netBankingDao.queryByUser(userIdentifier);
			List<NetBankingBO> nbBOs = new ArrayList<NetBankingBO>();
			
			if(nbDTOs == null || nbDTOs.size() == 0){
				return nbBOs;
			}
			
			MetroUserBO userBO = userBoDtoConverter.dtoToBo(userDTO.getUserId(), 
                                                            userDTO.getUniqueIdentifier(), 
                                                            userDTO.getName());
			
			for(NetBankingDTO nbDTO : nbDTOs){
				BankAccountDTO accountDTO = nbDTO.getAccount();
				BankAccountBO accountBO = accountBoDtoConverter.
                                               dtoToBoConverter(accountDTO.getAccountId(), 
                                                                accountDTO.getAccountNumber(), 
                                                                accountDTO.getBalance(), userBO);
				
				NetBankingBO nbBO = netBankingConverter.dtoToBo(nbDTO.getId(), nbDTO.getPaymentId(), nbDTO.getPassword(), accountBO);
				
				nbBOs.add(nbBO);
			}
			
			return nbBOs;
			
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}
	
	
}
