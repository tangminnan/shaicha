package com.shaicha.information.service;

import com.shaicha.information.domain.ActivityListDO;

import java.util.List;
import java.util.Map;

/**
 * 活动表---新
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-04-17 10:29:09
 */
public interface ActivityListService {
	
	ActivityListDO get(Integer id);
	
	List<ActivityListDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ActivityListDO activityList);
	
	int update(ActivityListDO activityList);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
