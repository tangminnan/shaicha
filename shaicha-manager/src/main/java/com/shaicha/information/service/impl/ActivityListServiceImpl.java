package com.shaicha.information.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.shaicha.information.dao.ActivityListDao;
import com.shaicha.information.domain.ActivityListDO;
import com.shaicha.information.service.ActivityListService;



@Service
public class ActivityListServiceImpl implements ActivityListService {
	@Autowired
	private ActivityListDao activityListDao;
	
	@Override
	public ActivityListDO get(Integer id){
		return activityListDao.get(id);
	}
	
	@Override
	public List<ActivityListDO> list(Map<String, Object> map){
		return activityListDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return activityListDao.count(map);
	}
	
	@Override
	public int save(ActivityListDO activityList){
		return activityListDao.save(activityList);
	}
	
	@Override
	public int update(ActivityListDO activityList){
		return activityListDao.update(activityList);
	}
	
	@Override
	public int remove(Integer id){
		return activityListDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return activityListDao.batchRemove(ids);
	}
	
}
