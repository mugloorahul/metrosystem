package com.metrosystem.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.dao.IRouteDao;
import com.metrosystem.dao.beans.RouteDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

@Repository("routeDao")
@Transactional(readOnly=true,rollbackFor={Exception.class})
public class RouteDaoImpl extends MetroSystemDaoImpl<Integer, RouteDTO> implements IRouteDao{

	public RouteDaoImpl(){
		super(RouteDTO.class);
	}

	@Override
	public RouteDTO queryRouteByName(String name) throws MetroSystemDaoException {
		
		String query = "from RouteDTO where name =?";
		List<RouteDTO> routes = this.queryListOfEntities(query, name);
		if(routes == null || routes.size() == 0){
			return null;
		}
		
		return routes.get(0);
	}
	
	
}
