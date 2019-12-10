package com.shaicha.information.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shaicha.information.dao.ResultDiopterDao;
import com.shaicha.information.domain.ResultDiopterDO;
import com.shaicha.information.service.ResultDiopterService;

import java.util.List;
import java.util.Map;



@Service
public class ResultDiopterServiceImpl implements ResultDiopterService {
	@Autowired
	private ResultDiopterDao resultDiopterDao;
	
	@Override
	public ResultDiopterDO get(Integer tDiopterId){
		return resultDiopterDao.get(tDiopterId);
	}
	
	@Override
	public List<ResultDiopterDO> list(Map<String, Object> map){
		return resultDiopterDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return resultDiopterDao.count(map);
	}
	
	@Override
	public int save(ResultDiopterDO resultDiopter){
		return resultDiopterDao.save(resultDiopter);
	}
	
	@Override
	public int update(ResultDiopterDO resultDiopter){
		return resultDiopterDao.update(resultDiopter);
	}
	
	@Override
	public int remove(Integer tDiopterId){
		return resultDiopterDao.remove(tDiopterId);
	}
	
	@Override
	public int batchRemove(Integer[] tDiopterIds){
		return resultDiopterDao.batchRemove(tDiopterIds);
	}

	@Override
	public List<ResultDiopterDO> getByToptometryId(Integer tOptometryId) {
		return resultDiopterDao.getByToptometryId(tOptometryId);
	}

	@Override
	public List<ResultDiopterDO> jianchashijian() {
		// TODO Auto-generated method stub
		return resultDiopterDao.jianchashijian();
	}
	
}
