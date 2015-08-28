package com.metrosystem.service;

import java.util.List;

import com.metrosystem.service.beans.MetroCardBO;
import com.metrosystem.service.exception.MetroSystemServiceException;

public interface IMetroCardService {

	public Integer createMetroCard(String cardNumber,double balance,String userIdentifier) throws MetroSystemServiceException;

    public List<MetroCardBO> findCardsForUser(String userIdentifier) throws MetroSystemServiceException;

    public MetroCardBO findCardByNumber(String cardNumber) throws MetroSystemServiceException;
    
    public void deleteCard(String cardNumber) throws MetroSystemServiceException;
    
}
