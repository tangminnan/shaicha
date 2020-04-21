package com.shaicha.informationNEW.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shaicha.informationNEW.dao.ResultEyepressureNewDao;
import com.shaicha.informationNEW.domain.ResultEyepressureNewDO;
import com.shaicha.informationNEW.service.ResultEyepressureNewService;

import java.util.List;
import java.util.Map;



@Service
public class ResultEyepressureNewServiceImpl implements ResultEyepressureNewService {
	@Autowired
	private ResultEyepressureNewDao resultEyepressureNewDao;
	
	@Override
	public ResultEyepressureNewDO get(Integer tEyepressureId){
		return resultEyepressureNewDao.get(tEyepressureId);
	}
	
	@Override
	public List<ResultEyepressureNewDO> list(Map<String, Object> map){
		return resultEyepressureNewDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return resultEyepressureNewDao.count(map);
	}
	
	@Override
	public int save(ResultEyepressureNewDO resultEyepressure){
		return resultEyepressureNewDao.save(resultEyepressure);
	}
	
	@Override
	public int update(ResultEyepressureNewDO resultEyepressure){
		return resultEyepressureNewDao.update(resultEyepressure);
	}
	
	@Override
	public int remove(Integer tEyepressureId){
		return resultEyepressureNewDao.remove(tEyepressureId);
	}
	
	@Override
	public int batchRemove(Integer[] tEyepressureIds){
		return resultEyepressureNewDao.batchRemove(tEyepressureIds);
	}
	
}
