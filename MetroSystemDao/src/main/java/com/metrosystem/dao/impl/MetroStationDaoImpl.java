package com.metrosystem.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.dao.IMetroStationDao;
import com.metrosystem.dao.beans.MetroStationDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

@Repository("stationDao")
@Transactional(readOnly=true,rollbackFor={Exception.class})
public class MetroStationDaoImpl extends MetroSystemDaoImpl<Integer, MetroStationDTO> implements IMetroStationDao {

	
	public MetroStationDaoImpl(){
		super(MetroStationDTO.class);
	}

	@Override
	public MetroStationDTO queryStationByName(String name) throws MetroSystemDaoException {
		String query = "from MetroStationDTO where name =?";
		List<MetroStationDTO> stations = this.queryListOfEntities(query, name);
		if(stations == null || stations.size() == 0){
			return null;
		}
		
		return stations.get(0);
	}

	@Override
	public void insertMultipleStations(List<MetroStationDTO> stations) throws MetroSystemDaoException {
		
		this.batchInsertEntities(stations);
		
	}
	
	
}