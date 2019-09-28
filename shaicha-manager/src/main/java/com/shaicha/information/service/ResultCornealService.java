package com.shaicha.information.service;

import java.util.List;
import java.util.Map;

import com.shaicha.information.domain.ResultCornealDO;

/**
 * 角膜曲率详情
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
public interface ResultCornealService {
	
	ResultCornealDO get(Integer tCornealId);
	
	List<ResultCornealDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ResultCornealDO resultCorneal);
	
	int update(ResultCornealDO resultCorneal);
	
	int remove(Integer tCornealId);
	
	int batchRemove(Integer[] tCornealIds);
	
	ResultCornealDO queryByToptometryTd(Integer tOptometryId);
}
