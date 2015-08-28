package com.metrosystem.dao;

import java.util.List;

import com.metrosystem.dao.beans.MetroCardDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

public interface IMetroCardDao extends IMetroSystemDao<Integer, MetroCardDTO> {

	public List<MetroCardDTO> queryCardsForUser(String userIdentifier) throws MetroSystemDaoException;

    public MetroCardDTO queryCardByNumber(String cardNumber) throws MetroSystemDaoException;
}
