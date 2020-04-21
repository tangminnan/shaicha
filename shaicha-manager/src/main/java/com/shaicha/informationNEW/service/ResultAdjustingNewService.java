package com.shaicha.informationNEW.service;

import java.util.List;
import java.util.Map;

import com.shaicha.informationNEW.domain.ResultAdjustingNewDO;

/**
 * 调节灵敏度
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
public interface ResultAdjustingNewService {
	
	ResultAdjustingNewDO get(Integer tAdjustingId);
	
	List<ResultAdjustingNewDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ResultAdjustingNewDO resultAdjusting);
	
	int update(ResultAdjustingNewDO resultAdjusting);
	
	int remove(Integer tAdjustingId);
	
	int batchRemove(Integer[] tAdjustingIds);
}
