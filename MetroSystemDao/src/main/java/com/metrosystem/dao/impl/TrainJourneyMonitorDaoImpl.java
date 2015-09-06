package com.metrosystem.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.dao.ITrainJourneyMonitorDao;
import com.metrosystem.dao.beans.TrainJourneyMonitorDTO;

@Repository("trainJourneyMonitorDao")
@Transactional(readOnly = false,rollbackFor={Exception.class})
public class TrainJourneyMonitorDaoImpl extends MetroSystemDaoImpl<Integer, TrainJourneyMonitorDTO> 
implements ITrainJourneyMonitorDao {

	public TrainJourneyMonitorDaoImpl(){
		super(TrainJourneyMonitorDTO.class);
	}


}
