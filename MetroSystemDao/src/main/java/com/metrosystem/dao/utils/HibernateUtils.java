package com.metrosystem.dao.utils;

import java.lang.reflect.Method;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.stereotype.Component;

@Component("hibernateUtils")
public final class HibernateUtils {

	
	public boolean isIdentifierGenStrategy(Class<?> clazz){
				
		Method methods[] = clazz.getDeclaredMethods();
		
		for(Method method: methods){
			if(method.isAnnotationPresent(GeneratedValue.class)){
				GeneratedValue annotation = method.getAnnotation(GeneratedValue.class);
				GenerationType strategy= annotation.strategy();
				if(strategy.equals(GenerationType.AUTO) || 
				   strategy.equals(GenerationType.IDENTITY)){
					return true;
				}
			}
		}
		
		return false;
	}
	
}
