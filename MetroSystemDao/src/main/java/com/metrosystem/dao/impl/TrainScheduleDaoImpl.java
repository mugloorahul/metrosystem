package com.metrosystem.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.dao.ITrainScheduleDao;
import com.metrosystem.dao.beans.TrainScheduleDTO;

@Repository("trainScheduleDao")
@Transactional(readOnly=true,rollbackFor={Exception.class})
public class TrainScheduleDaoImpl extends MetroSystemDaoImpl<Integer, TrainScheduleDTO> implements
   ITrainScheduleDao
{

	public TrainScheduleDaoImpl(){
		super(TrainScheduleDTO.class);
	}
}
