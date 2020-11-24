package com.shaicha.informationNEW.dao;

import com.shaicha.informationNEW.domain.ResultMainDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 用于首页展示数据保存
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-11-23 16:13:32
 */
@Mapper
public interface ResultMainDao {

	ResultMainDO get(Integer id);
	
	List<ResultMainDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ResultMainDO resultMain);
	
	int update(ResultMainDO resultMain);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
