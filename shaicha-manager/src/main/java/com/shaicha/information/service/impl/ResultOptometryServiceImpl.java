package com.shaicha.information.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shaicha.information.dao.ResultOptometryDao;
import com.shaicha.information.domain.ResultOptometryDO;
import com.shaicha.information.service.ResultOptometryService;

import java.util.List;
import java.util.Map;



@Service
public class ResultOptometryServiceImpl implements ResultOptometryService {
	@Autowired
	private ResultOptometryDao resultOptometryDao;
	
	@Override
	public ResultOptometryDO get(Integer tOptometryId){
		return resultOptometryDao.get(tOptometryId);
	}
	
	@Override
	public List<ResultOptometryDO> list(Map<String, Object> map){
		return resultOptometryDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return resultOptometryDao.count(map);
	}
	
	@Override
	public int save(ResultOptometryDO resultOptometry){
		return resultOptometryDao.save(resultOptometry);
	}
	
	@Override
	public int update(ResultOptometryDO resultOptometry){
		return resultOptometryDao.update(resultOptometry);
	}
	
	@Override
	public int remove(Integer tOptometryId){
		return resultOptometryDao.remove(tOptometryId);
	}
	
	@Override
	public int batchRemove(Integer[] tOptometryIds){
		return resultOptometryDao.batchRemove(tOptometryIds);
	}
	
}
