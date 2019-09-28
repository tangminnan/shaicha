package com.shaicha.information.service;

import java.util.List;
import java.util.Map;

import com.shaicha.information.domain.ResultAdjustingDO;

/**
 * 调节灵敏度
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
public interface ResultAdjustingService {
	
	ResultAdjustingDO get(Integer tAdjustingId);
	
	List<ResultAdjustingDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ResultAdjustingDO resultAdjusting);
	
	int update(ResultAdjustingDO resultAdjusting);
	
	int remove(Integer tAdjustingId);
	
	int batchRemove(Integer[] tAdjustingIds);
}
