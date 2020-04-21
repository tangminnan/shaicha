package com.shaicha.informationNEW.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shaicha.informationNEW.dao.ResultEyesightNewDao;
import com.shaicha.informationNEW.domain.ResultEyesightNewDO;
import com.shaicha.informationNEW.service.ResultEyesightNewService;

import java.util.List;
import java.util.Map;



@Service
public class ResultEyesightNewServiceImpl implements ResultEyesightNewService {
	@Autowired
	private ResultEyesightNewDao resultEyesightNewDao;
	
	@Override
	public ResultEyesightNewDO get(Integer tEyesightId){
		return resultEyesightNewDao.get(tEyesightId);
	}
	
	@Override
	public List<ResultEyesightNewDO> list(Map<String, Object> map){
		return resultEyesightNewDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return resultEyesightNewDao.count(map);
	}
	
	@Override
	public int save(ResultEyesightNewDO resultEyesight){
		return resultEyesightNewDao.save(resultEyesight);
	}
	
	@Override
	public int update(ResultEyesightNewDO resultEyesight){
		return resultEyesightNewDao.update(resultEyesight);
	}
	
	@Override
	public int remove(Integer tEyesightId){
		return resultEyesightNewDao.remove(tEyesightId);
	}
	
	@Override
	public int batchRemove(Integer[] tEyesightIds){
		return resultEyesightNewDao.batchRemove(tEyesightIds);
	}
	
}
