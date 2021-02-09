package com.shaicha.informationNEW.service;

import java.util.List;
import java.util.Map;

import com.shaicha.informationNEW.domain.ResultOptometryNewDO;

/**
 * 验光数据表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
public interface ResultOptometryNewService {
	
	ResultOptometryNewDO get(Integer tOptometryId);
	
	List<ResultOptometryNewDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ResultOptometryNewDO resultOptometry);
	
	int update(ResultOptometryNewDO resultOptometry);
	
	int remove(Integer tOptometryId);
	
	int batchRemove(Integer[] tOptometryIds);

	Integer findOptIdByStuId(Integer id);
}
