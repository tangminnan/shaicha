package com.shaicha.information.dao;

import com.shaicha.information.domain.SchoolNewDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-04-17 11:44:53
 */
@Mapper
public interface SchoolNewDao {

	SchoolNewDO get(Integer id);
	
	List<SchoolNewDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(SchoolNewDO school);
	
	int update(SchoolNewDO school);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
	
}
