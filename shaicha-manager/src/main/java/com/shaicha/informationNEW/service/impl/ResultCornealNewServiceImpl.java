package com.shaicha.informationNEW.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shaicha.informationNEW.dao.ResultCornealNewDao;
import com.shaicha.informationNEW.domain.ResultCornealNewDO;
import com.shaicha.informationNEW.service.ResultCornealNewService;

import java.util.List;
import java.util.Map;



@Service
public class ResultCornealNewServiceImpl implements ResultCornealNewService {
	@Autowired
	private ResultCornealNewDao resultCornealNewDao;
	
	@Override
	public ResultCornealNewDO get(Integer tCornealId){
		return resultCornealNewDao.get(tCornealId);
	}
	
	@Override
	public List<ResultCornealNewDO> list(Map<String, Object> map){
		return resultCornealNewDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return resultCornealNewDao.count(map);
	}
	
	@Override
	public int save(ResultCornealNewDO resultCorneal){
		return resultCornealNewDao.save(resultCorneal);
	}
	
	@Override
	public int update(ResultCornealNewDO resultCorneal){
		return resultCornealNewDao.update(resultCorneal);
	}
	
	@Override
	public int remove(Integer tCornealId){
		return resultCornealNewDao.remove(tCornealId);
	}
	
	@Override
	public int batchRemove(Integer[] tCornealIds){
		return resultCornealNewDao.batchRemove(tCornealIds);
	}

	@Override
	public List<ResultCornealNewDO> queryByToptometryTd(Integer tOptometryId) {
		return resultCornealNewDao.queryByToptometryTd(tOptometryId);
	}
	
}
