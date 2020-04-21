package com.shaicha.informationNEW.service;

import java.util.List;
import java.util.Map;

import com.shaicha.informationNEW.domain.ResultCornealNewDO;

/**
 * 角膜曲率详情
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
public interface ResultCornealNewService {
	
	ResultCornealNewDO get(Integer tCornealId);
	
	List<ResultCornealNewDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ResultCornealNewDO resultCorneal);
	
	int update(ResultCornealNewDO resultCorneal);
	
	int remove(Integer tCornealId);
	
	int batchRemove(Integer[] tCornealIds);
	
	List<ResultCornealNewDO> queryByToptometryTd(Integer tOptometryId);
}
