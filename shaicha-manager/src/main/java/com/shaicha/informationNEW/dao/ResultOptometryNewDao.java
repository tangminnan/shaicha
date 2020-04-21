package com.shaicha.informationNEW.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.shaicha.informationNEW.domain.ResultOptometryNewDO;

/**
 * 验光数据表
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
@Mapper
public interface ResultOptometryNewDao {

	ResultOptometryNewDO get(Integer tOptometryId);
	
	List<ResultOptometryNewDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ResultOptometryNewDO resultOptometry);
	
	int update(ResultOptometryNewDO resultOptometry);
	
	int remove(Integer t_optometry_id);
	
	int batchRemove(Integer[] tOptometryIds);
}
