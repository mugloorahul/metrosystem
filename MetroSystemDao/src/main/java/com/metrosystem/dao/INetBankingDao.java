package com.metrosystem.dao;

import java.util.List;

import com.metrosystem.dao.beans.NetBankingDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

public interface INetBankingDao extends IMetroSystemDao<Integer, NetBankingDTO> {

	public NetBankingDTO queryActiveByCustomerId(String customerId) throws MetroSystemDaoException;
	
	public List<NetBankingDTO> queryActiveByUser(String userIdentifier) throws MetroSystemDaoException;
	
	public NetBankingDTO queryActiveByAccountNumber(String accountNumber) throws MetroSystemDaoException;
	
    public NetBankingDTO queryByCustomerId(String customerId) throws MetroSystemDaoException;
	
	public List<NetBankingDTO> queryByUser(String userIdentifier) throws MetroSystemDaoException;
	
	public NetBankingDTO queryByAccountNumber(String accountNumber) throws MetroSystemDaoException;
}
