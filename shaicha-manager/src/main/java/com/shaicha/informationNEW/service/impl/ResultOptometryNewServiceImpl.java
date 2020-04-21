package com.shaicha.informationNEW.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shaicha.informationNEW.dao.ResultOptometryNewDao;
import com.shaicha.informationNEW.domain.ResultOptometryNewDO;
import com.shaicha.informationNEW.service.ResultOptometryNewService;

import java.util.List;
import java.util.Map;



@Service
public class ResultOptometryNewServiceImpl implements ResultOptometryNewService {
	@Autowired
	private ResultOptometryNewDao resultOptometryNewDao;
	
	@Override
	public ResultOptometryNewDO get(Integer tOptometryId){
		return resultOptometryNewDao.get(tOptometryId);
	}
	
	@Override
	public List<ResultOptometryNewDO> list(Map<String, Object> map){
		return resultOptometryNewDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return resultOptometryNewDao.count(map);
	}
	
	@Override
	public int save(ResultOptometryNewDO resultOptometry){
		return resultOptometryNewDao.save(resultOptometry);
	}
	
	@Override
	public int update(ResultOptometryNewDO resultOptometry){
		return resultOptometryNewDao.update(resultOptometry);
	}
	
	@Override
	public int remove(Integer tOptometryId){
		return resultOptometryNewDao.remove(tOptometryId);
	}
	
	@Override
	public int batchRemove(Integer[] tOptometryIds){
		return resultOptometryNewDao.batchRemove(tOptometryIds);
	}
	
}
