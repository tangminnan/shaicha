package com.shaicha.information.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shaicha.information.dao.ChectorDao;
import com.shaicha.information.domain.ChectorDO;
import com.shaicha.information.service.ChectorService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChectorServiceImpl implements ChectorService {
	@Autowired
	private ChectorDao chectorDao;
	
	@Override
	public ChectorDO get(Long chectorId){
		return chectorDao.get(chectorId);
	}
	
	@Override
	public List<ChectorDO> list(Map<String, Object> map){
		return chectorDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return chectorDao.count(map);
	}
	
	@Override
	public int save(ChectorDO chector){
		return chectorDao.save(chector);
	}
	
	@Override
	public int update(ChectorDO chector){
		return chectorDao.update(chector);
	}
	
	@Override
	public int remove(Long chectorId){
		return chectorDao.remove(chectorId);
	}
	
	@Override
	public int batchRemove(Long[] chectorIds){
		return chectorDao.batchRemove(chectorIds);
	}
	
}
