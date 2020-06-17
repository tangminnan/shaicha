package com.shaicha.informationNEW.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shaicha.informationNEW.dao.ResultDiopterNewDao;
import com.shaicha.informationNEW.domain.ResultDiopterNewDO;
import com.shaicha.informationNEW.service.ResultDiopterNewService;

import java.util.Date;
import java.util.List;
import java.util.Map;



@Service
public class ResultDiopterNewServiceImpl implements ResultDiopterNewService {
	@Autowired
	private ResultDiopterNewDao resultDiopterNewDao;
	
	@Override
	public ResultDiopterNewDO get(Integer tDiopterId){
		return resultDiopterNewDao.get(tDiopterId);
	}
	
	@Override
	public List<ResultDiopterNewDO> list(Map<String, Object> map){
		return resultDiopterNewDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return resultDiopterNewDao.count(map);
	}
	
	@Override
	public int save(ResultDiopterNewDO resultDiopter){
		return resultDiopterNewDao.save(resultDiopter);
	}
	
	@Override
	public int update(ResultDiopterNewDO resultDiopter){
		return resultDiopterNewDao.update(resultDiopter);
	}
	
	@Override
	public int remove(Integer tDiopterId){
		return resultDiopterNewDao.remove(tDiopterId);
	}
	
	@Override
	public int batchRemove(Integer[] tDiopterIds){
		return resultDiopterNewDao.batchRemove(tDiopterIds);
	}

	@Override
	public List<ResultDiopterNewDO> getByToptometryId(Integer tOptometryId) {
		return resultDiopterNewDao.getByToptometryId(tOptometryId);
	}

	@Override
	public List<ResultDiopterNewDO> jianchashijian() {
		// TODO Auto-generated method stub
		return resultDiopterNewDao.jianchashijian();
	}

	@Override
	public List<ResultDiopterNewDO> queryTimeBetween(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return resultDiopterNewDao.queryTimeBetween(startDate, endDate);
	}

	
}
