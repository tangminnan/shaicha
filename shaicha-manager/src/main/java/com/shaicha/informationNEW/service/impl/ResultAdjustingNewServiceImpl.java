package com.shaicha.informationNEW.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shaicha.informationNEW.dao.ResultAdjustingNewDao;
import com.shaicha.informationNEW.domain.ResultAdjustingNewDO;
import com.shaicha.informationNEW.service.ResultAdjustingNewService;

import java.util.List;
import java.util.Map;



@Service
public class ResultAdjustingNewServiceImpl implements ResultAdjustingNewService {
	@Autowired
	private ResultAdjustingNewDao resultAdjustingNewDao;
	
	@Override
	public ResultAdjustingNewDO get(Integer tAdjustingId){
		return resultAdjustingNewDao.get(tAdjustingId);
	}
	
	@Override
	public List<ResultAdjustingNewDO> list(Map<String, Object> map){
		return resultAdjustingNewDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return resultAdjustingNewDao.count(map);
	}
	
	@Override
	public int save(ResultAdjustingNewDO resultAdjusting){
		return resultAdjustingNewDao.save(resultAdjusting);
	}
	
	@Override
	public int update(ResultAdjustingNewDO resultAdjusting){
		return resultAdjustingNewDao.update(resultAdjusting);
	}
	
	@Override
	public int remove(Integer tAdjustingId){
		return resultAdjustingNewDao.remove(tAdjustingId);
	}
	
	@Override
	public int batchRemove(Integer[] tAdjustingIds){
		return resultAdjustingNewDao.batchRemove(tAdjustingIds);
	}
	
}
