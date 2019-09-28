package com.shaicha.information.service;

import java.util.List;
import java.util.Map;

import com.shaicha.information.domain.ResultEyepressureDO;

/**
 * 眼内压
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
public interface ResultEyepressureService {
	
	ResultEyepressureDO get(Integer tEyepressureId);
	
	List<ResultEyepressureDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ResultEyepressureDO resultEyepressure);
	
	int update(ResultEyepressureDO resultEyepressure);
	
	int remove(Integer tEyepressureId);
	
	int batchRemove(Integer[] tEyepressureIds);
}
