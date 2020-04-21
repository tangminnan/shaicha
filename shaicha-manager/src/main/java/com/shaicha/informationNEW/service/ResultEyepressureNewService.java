package com.shaicha.informationNEW.service;

import java.util.List;
import java.util.Map;

import com.shaicha.informationNEW.domain.ResultEyepressureNewDO;

/**
 * 眼内压
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
public interface ResultEyepressureNewService {
	
	ResultEyepressureNewDO get(Integer tEyepressureId);
	
	List<ResultEyepressureNewDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ResultEyepressureNewDO resultEyepressure);
	
	int update(ResultEyepressureNewDO resultEyepressure);
	
	int remove(Integer tEyepressureId);
	
	int batchRemove(Integer[] tEyepressureIds);
}
