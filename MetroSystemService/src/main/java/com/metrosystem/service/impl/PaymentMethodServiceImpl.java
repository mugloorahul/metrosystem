package com.metrosystem.service.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.service.IPaymentMethodService;

@Component("paymentMethodService")
@Transactional(readOnly=true,rollbackFor={Exception.class})
public class PaymentMethodServiceImpl implements IPaymentMethodService{

	
}
