package com.metrosystem.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.dao.IMetroSystemDao;
import com.metrosystem.dao.exception.MetroSystemDaoException;
import com.metrosystem.dao.utils.Constants;
import com.metrosystem.dao.utils.HibernateUtils;


public abstract class MetroSystemDaoImpl<K extends Serializable,T extends Serializable>  implements IMetroSystemDao<K,T> {

	protected Class<? extends T> entityClazz;
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory factory;
	
	@Autowired
	@Qualifier("hibernateUtils")
	private HibernateUtils hibernateUtils;
	
	public MetroSystemDaoImpl(Class<? extends T> entityClass){
		this.entityClazz=entityClass;
	}

	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public K save(T entity) throws MetroSystemDaoException {
		
		try{
			K id = (K)factory.getCurrentSession().save(entity);
			return id;
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}
	}

	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public void delete(T entity) throws MetroSystemDaoException {
		
		try{
			factory.getCurrentSession().delete(entity);
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}
	}
	
	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public void deleteById(K identifier) throws MetroSystemDaoException {
		
		try{
			T entity = (T) factory.getCurrentSession().load(entityClazz, identifier);
			factory.getCurrentSession().delete(entity);
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}
	}

	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public void update(T entity) throws MetroSystemDaoException {
		
		try{
			factory.getCurrentSession().update(entity);
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}
	}

	@Override
	public T queryById(K identifier) throws MetroSystemDaoException {
		try{
			return (T) factory.getCurrentSession().load(entityClazz, identifier);
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}
	}
	
	
	protected List<T> queryListOfEntities(String queryString, Object ... params) throws MetroSystemDaoException
	{
		try{
			Query query = factory.getCurrentSession().createQuery(queryString);
			int paramCount = 0;
            for(Object param: params){
            	query.setParameter(paramCount, param);
            	paramCount++;
            }
			
			return (List<T>)query.list();
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}
	}
	
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	protected void batchInsertEntities(List<T> entities) throws MetroSystemDaoException{
		try{
			if(hibernateUtils.isIdentifierGenStrategy(entityClazz)){
				throw new  IllegalArgumentException("Batch insert is not supported " +
						"for entities with identity generation strategy");
			}
			
			int count = 0;
			Session session = factory.getCurrentSession();
			for(T entity: entities){
				count++;
			    session.save(entity);
			    if(count%Constants.DEFAULT_BATCH_INSERT_SIZE == 0){
			    	session.flush();
			    	session.clear();
			    }
			}
			session.flush();
	    	session.clear();
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}
	}
	
	protected final SessionFactory getSessionFactory(){
		return this.factory;
	}
}
