package com.shaicha.informationNEW.service;

import java.util.List;
import java.util.Map;

import com.shaicha.informationNEW.domain.ResultEyeaxisNewDO;

/**
 * 眼轴长度
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
public interface ResultEyeaxisNewService {
	
	ResultEyeaxisNewDO get(Integer tEyeaxisId);
	
	List<ResultEyeaxisNewDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ResultEyeaxisNewDO resultEyeaxis);
	
	int update(ResultEyeaxisNewDO resultEyeaxis);
	
	int remove(Integer tEyeaxisId);
	
	int batchRemove(Integer[] tEyeaxisIds);
}
