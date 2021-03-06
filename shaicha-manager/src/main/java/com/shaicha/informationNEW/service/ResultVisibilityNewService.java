package com.shaicha.informationNEW.service;

import java.util.List;
import java.util.Map;

import com.shaicha.informationNEW.domain.ResultVisibilityNewDO;

/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
public interface ResultVisibilityNewService {
	
	ResultVisibilityNewDO get(Integer tVisibilityId);
	
	List<ResultVisibilityNewDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ResultVisibilityNewDO resultVisibility);
	
	int update(ResultVisibilityNewDO resultVisibility);
	
	int remove(Integer tVisibilityId);
	
	int batchRemove(Integer[] tVisibilityIds);
}
