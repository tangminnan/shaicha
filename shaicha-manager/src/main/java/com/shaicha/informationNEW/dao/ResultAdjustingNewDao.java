package com.shaicha.informationNEW.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.shaicha.informationNEW.domain.ResultAdjustingNewDO;

/**
 * 调节灵敏度
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
@Mapper
public interface ResultAdjustingNewDao {

	ResultAdjustingNewDO get(Integer tAdjustingId);
	
	List<ResultAdjustingNewDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ResultAdjustingNewDO resultAdjusting);
	
	int update(ResultAdjustingNewDO resultAdjusting);
	
	int remove(Integer t_adjusting__id);
	
	int batchRemove(Integer[] tAdjustingIds);
}
