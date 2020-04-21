package com.shaicha.informationNEW.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.shaicha.informationNEW.dao.ActivityListNewDao;
import com.shaicha.informationNEW.domain.ActivityListNewDO;
import com.shaicha.informationNEW.service.ActivityListNewService;



@Service
public class ActivityListNewServiceImpl implements ActivityListNewService {
	@Autowired
	private ActivityListNewDao activityListNewDao;
	
	@Override
	public ActivityListNewDO get(Integer id){
		return activityListNewDao.get(id);
	}
	
	@Override
	public List<ActivityListNewDO> list(Map<String, Object> map){
		return activityListNewDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return activityListNewDao.count(map);
	}
	
	@Override
	public int save(ActivityListNewDO activityListNew){
		return activityListNewDao.save(activityListNew);
	}
	
	@Override
	public int update(ActivityListNewDO activityListNew){
		return activityListNewDao.update(activityListNew);
	}
	
	@Override
	public int remove(Integer id){
		return activityListNewDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return activityListNewDao.batchRemove(ids);
	}
	
}
