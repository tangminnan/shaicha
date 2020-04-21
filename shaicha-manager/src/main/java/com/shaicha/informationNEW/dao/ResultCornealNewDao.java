package com.shaicha.informationNEW.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shaicha.informationNEW.domain.ResultCornealNewDO;

/**
 * 角膜曲率详情
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
@Mapper
public interface ResultCornealNewDao {

	ResultCornealNewDO get(Integer tCornealId);
	
	List<ResultCornealNewDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ResultCornealNewDO resultCorneal);
	
	int update(ResultCornealNewDO resultCorneal);
	
	int remove(Integer t_corneal_id);
	
	int batchRemove(Integer[] tCornealIds);
	
	List<ResultCornealNewDO> queryByToptometryTd(Integer tOptometryId);
	
	List<ResultCornealNewDO> getCornealMm(@Param("ifRL") String ifRL,@Param("identityCard") String identityCard
			,@Param("type") String type,@Param("start") Date start,@Param("end") Date end);
}
