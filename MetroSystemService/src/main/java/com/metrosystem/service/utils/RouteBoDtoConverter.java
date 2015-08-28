package com.metrosystem.service.utils;

import org.springframework.stereotype.Component;

import com.metrosystem.dao.beans.RouteDTO;
import com.metrosystem.service.beans.RouteBO;

@Component("routeBoDtoConverter")
public class RouteBoDtoConverter {


	public RouteDTO boToDto(Integer routeId,String name){
		RouteDTO dto = new RouteDTO(name);
		dto.setRouteId(routeId == null?0:routeId);
		
		return dto;
	}
	
	public RouteBO dtoToBo(Integer routeId,String name){
		RouteBO bo = new RouteBO(name);
		bo.setRouteId(routeId == null?0:routeId);
		
		return bo;
	}
}
