package com.shaicha.informationNEW.dao;

import com.shaicha.informationNEW.domain.ChanpinDetailsDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-06-17 18:16:52
 */
@Mapper
public interface ChanpinDetailsDao {

	ChanpinDetailsDO get(Integer id);
	
	List<ChanpinDetailsDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ChanpinDetailsDO chanpinDetails);
	
	int update(ChanpinDetailsDO chanpinDetails);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
