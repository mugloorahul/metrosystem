package com.metrosystem.service.utils;

import org.springframework.stereotype.Component;

import com.metrosystem.dao.beans.BankAccountDTO;
import com.metrosystem.dao.beans.NetBankingDTO;
import com.metrosystem.service.beans.BankAccountBO;
import com.metrosystem.service.beans.NetBankingBO;

@Component("netBankingBoDtoConverter")
public class NetBankingBoDtoConverter {

	public NetBankingDTO boToDto(Integer id,String customerId,
			                    String password,BankAccountDTO account){
		
		NetBankingDTO dto = new NetBankingDTO(customerId, password, account);
		dto.setId(id!=null?id:0);
		
		return dto;
	}
	
	public NetBankingBO dtoToBo(Integer id,String customerId,
            String password,BankAccountBO account){
		
		NetBankingBO bo = new NetBankingBO(customerId, password, account);
		bo.setId(id!=null?id:0);
		
		return bo;
	}
}
