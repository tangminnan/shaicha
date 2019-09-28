package com.shaicha.information.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.shaicha.information.domain.ChectorDO;

/**
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-14 16:19:31
 */
@Mapper
public interface ChectorDao {

	ChectorDO get(Long chectorId);
	
	List<ChectorDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ChectorDO chector);
	
	int update(ChectorDO chector);
	
	int remove(Long chector_id);
	
	int batchRemove(Long[] chectorIds);
}
