package com.shaicha.informationNEW.service;

import com.shaicha.informationNEW.domain.ChanpinListDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-06-17 18:16:53
 */
public interface ChanpinListService {
	
	ChanpinListDO get(Integer id);
	
	List<ChanpinListDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ChanpinListDO chanpinList);
	
	int update(ChanpinListDO chanpinList);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
