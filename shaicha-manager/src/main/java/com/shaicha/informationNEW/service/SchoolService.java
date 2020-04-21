package com.shaicha.informationNEW.service;

import com.shaicha.informationNEW.domain.SchoolDO;
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
public interface SchoolService {
	
	SchoolDO get(Integer id);
	
	List<SchoolDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(SchoolDO school);
	
	int update(SchoolDO school);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);

}
