package com.shaicha.informationNEW.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.shaicha.informationNEW.domain.ResultVisibilityNewDO;

/**
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
@Mapper
public interface ResultVisibilityNewDao {

	ResultVisibilityNewDO get(Integer tVisibilityId);
	
	List<ResultVisibilityNewDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ResultVisibilityNewDO resultVisibility);
	
	int update(ResultVisibilityNewDO resultVisibility);
	
	int remove(Integer t_visibility_id);
	
	int batchRemove(Integer[] tVisibilityIds);
}
