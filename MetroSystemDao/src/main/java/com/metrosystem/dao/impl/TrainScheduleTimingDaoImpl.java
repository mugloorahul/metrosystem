package com.metrosystem.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.dao.ITrainScheduleTimingDao;
import com.metrosystem.dao.beans.TrainScheduleTimingDTO;

@Repository("trainScheduleTimingDao")
@Transactional(readOnly=true,rollbackFor={Exception.class})
public class TrainScheduleTimingDaoImpl extends MetroSystemDaoImpl<Integer, TrainScheduleTimingDTO> 
implements ITrainScheduleTimingDao 
{
   public TrainScheduleTimingDaoImpl(){
	  super(TrainScheduleTimingDTO.class);   
   }
}
