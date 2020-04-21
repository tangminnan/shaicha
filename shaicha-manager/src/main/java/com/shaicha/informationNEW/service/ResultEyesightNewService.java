package com.shaicha.informationNEW.service;

import java.util.List;
import java.util.Map;

import com.shaicha.informationNEW.domain.ResultEyesightNewDO;

/**
 * 视力检查
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
public interface ResultEyesightNewService {
	
	ResultEyesightNewDO get(Integer tEyesightId);
	
	List<ResultEyesightNewDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ResultEyesightNewDO resultEyesight);
	
	int update(ResultEyesightNewDO resultEyesight);
	
	int remove(Integer tEyesightId);
	
	int batchRemove(Integer[] tEyesightIds);
}
