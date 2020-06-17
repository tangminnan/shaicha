package com.shaicha.information.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shaicha.information.domain.ResultCornealDO;
import com.shaicha.informationNEW.domain.ResultCornealNewDO;

/**
 * 角膜曲率详情
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
@Mapper
public interface ResultCornealDao {

	ResultCornealDO get(Integer tCornealId);
	
	List<ResultCornealDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ResultCornealDO resultCorneal);
	
	int update(ResultCornealDO resultCorneal);
	
	int remove(Integer t_corneal_id);
	
	int batchRemove(Integer[] tCornealIds);
	
	List<ResultCornealDO> queryByToptometryTd(Integer tOptometryId);
	
	List<ResultCornealDO> getCornealMm(@Param("ifRL") String ifRL,@Param("identityCard") String identityCard
			,@Param("type") String type,@Param("activityId") Integer activityId);
	
	//int insertForeach(List<ResultCornealNewDO> list);
}
