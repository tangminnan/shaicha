package com.shaicha.informationNEW.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shaicha.informationNEW.dao.ResultVisibilityNewDao;
import com.shaicha.informationNEW.domain.ResultVisibilityNewDO;
import com.shaicha.informationNEW.service.ResultVisibilityNewService;

import java.util.List;
import java.util.Map;



@Service
public class ResultVisibilityNewServiceImpl implements ResultVisibilityNewService {
	@Autowired
	private ResultVisibilityNewDao resultVisibilityNewDao;
	
	@Override
	public ResultVisibilityNewDO get(Integer tVisibilityId){
		return resultVisibilityNewDao.get(tVisibilityId);
	}
	
	@Override
	public List<ResultVisibilityNewDO> list(Map<String, Object> map){
		return resultVisibilityNewDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return resultVisibilityNewDao.count(map);
	}
	
	@Override
	public int save(ResultVisibilityNewDO resultVisibility){
		return resultVisibilityNewDao.save(resultVisibility);
	}
	
	@Override
	public int update(ResultVisibilityNewDO resultVisibility){
		return resultVisibilityNewDao.update(resultVisibility);
	}
	
	@Override
	public int remove(Integer tVisibilityId){
		return resultVisibilityNewDao.remove(tVisibilityId);
	}
	
	@Override
	public int batchRemove(Integer[] tVisibilityIds){
		return resultVisibilityNewDao.batchRemove(tVisibilityIds);
	}
	
}
