package com.shaicha.informationNEW.service;

import com.shaicha.informationNEW.domain.SchoolNewDO;
import com.shaicha.informationNEW.domain.TArea;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-04-17 11:44:53
 */
public interface SchoolNewService {
	
	SchoolNewDO get(Integer id);
	
	List<SchoolNewDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(SchoolNewDO school);
	
	int update(SchoolNewDO school);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);

}
