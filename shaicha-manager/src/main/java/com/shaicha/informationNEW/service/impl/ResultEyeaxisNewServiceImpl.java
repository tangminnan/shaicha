package com.shaicha.informationNEW.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shaicha.informationNEW.dao.ResultEyeaxisNewDao;
import com.shaicha.informationNEW.domain.ResultEyeaxisNewDO;
import com.shaicha.informationNEW.service.ResultEyeaxisNewService;

import java.util.List;
import java.util.Map;



@Service
public class ResultEyeaxisNewServiceImpl implements ResultEyeaxisNewService {
	@Autowired
	private ResultEyeaxisNewDao resultEyeaxisNewDao;
	
	@Override
	public ResultEyeaxisNewDO get(Integer tEyeaxisId){
		return resultEyeaxisNewDao.get(tEyeaxisId);
	}
	
	@Override
	public List<ResultEyeaxisNewDO> list(Map<String, Object> map){
		return resultEyeaxisNewDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return resultEyeaxisNewDao.count(map);
	}
	
	@Override
	public int save(ResultEyeaxisNewDO resultEyeaxis){
		return resultEyeaxisNewDao.save(resultEyeaxis);
	}
	
	@Override
	public int update(ResultEyeaxisNewDO resultEyeaxis){
		return resultEyeaxisNewDao.update(resultEyeaxis);
	}
	
	@Override
	public int remove(Integer tEyeaxisId){
		return resultEyeaxisNewDao.remove(tEyeaxisId);
	}
	
	@Override
	public int batchRemove(Integer[] tEyeaxisIds){
		return resultEyeaxisNewDao.batchRemove(tEyeaxisIds);
	}
	
}
