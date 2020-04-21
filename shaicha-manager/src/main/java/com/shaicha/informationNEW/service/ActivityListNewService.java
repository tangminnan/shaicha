package com.shaicha.informationNEW.service;

import com.shaicha.informationNEW.domain.ActivityListNewDO;

import java.util.List;
import java.util.Map;

/**
 * 活动表---新
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-04-17 10:29:09
 */
public interface ActivityListNewService {
	
	ActivityListNewDO get(Integer id);
	
	List<ActivityListNewDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ActivityListNewDO activityListNew);
	
	int update(ActivityListNewDO activityListNew);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
