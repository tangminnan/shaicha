package com.shaicha.owneruser.service;


import java.util.List;
import java.util.Map;

import com.shaicha.owneruser.domain.SetDO;

/**
 * 
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-03-18 14:11:10
 */
public interface SetService {
	
	
	
	List<SetDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(SetDO set);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);

	SetDO getHouseHolderSet(Long userId);

	SetDO getProfessionSet(Long userId);
	int addsetUser(SetDO setDO);

	int updatesetUser(SetDO setDO);

	SetDO getFlag(Long userId);

	void updateFlag(SetDO setDO);
}
