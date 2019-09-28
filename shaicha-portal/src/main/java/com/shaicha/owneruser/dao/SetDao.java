package com.shaicha.owneruser.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.shaicha.owneruser.domain.SetDO;

/**
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-03-18 14:11:10
 */
@Mapper
public interface SetDao {

	
	
	List<SetDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(SetDO set);
	
	int update(SetDO set);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);

	SetDO getHouseHolderSet(Long userId);

	int addsetUser(SetDO setDO);

	SetDO getProfessionSet(Long userId);

	int updatesetUser(SetDO setDO);

	SetDO getFlag(Long userId);

	void updateFlag(SetDO setDO);
}
