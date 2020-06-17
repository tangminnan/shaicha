package com.shaicha.information.dao;

import com.shaicha.information.domain.ActivityListDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 活动表---新
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-04-17 10:29:09
 */
@Mapper
public interface ActivityListDao {

	ActivityListDO get(Integer id);
	
	List<ActivityListDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ActivityListDO activityList);
	
	int update(ActivityListDO activityList);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
