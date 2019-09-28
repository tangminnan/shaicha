package com.shaicha.information.service;
import java.util.List;
import java.util.Map;

import com.shaicha.information.domain.ChectorDO;

/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-14 16:19:31
 */
public interface ChectorService {
	
	ChectorDO get(Long chectorId);
	
	List<ChectorDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ChectorDO chector);
	
	int update(ChectorDO chector);
	
	int remove(Long chectorId);
	
	int batchRemove(Long[] chectorIds);
}
