package com.shaicha.informationNEW.dao;

import com.shaicha.informationNEW.domain.ChanpinListDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-06-17 18:16:53
 */
@Mapper
public interface ChanpinListDao {

	ChanpinListDO get(Integer id);
	
	List<ChanpinListDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ChanpinListDO chanpinList);
	
	int update(ChanpinListDO chanpinList);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
