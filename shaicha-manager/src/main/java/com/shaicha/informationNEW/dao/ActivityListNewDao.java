package com.shaicha.informationNEW.dao;

import com.shaicha.informationNEW.domain.ActivityListNewDO;

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
public interface ActivityListNewDao {

	ActivityListNewDO get(Integer id);

	//List<ActivityListNewDO> getlistall;
	
	List<ActivityListNewDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ActivityListNewDO activityListNew);
	
	int update(ActivityListNewDO activityListNew);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);

}
