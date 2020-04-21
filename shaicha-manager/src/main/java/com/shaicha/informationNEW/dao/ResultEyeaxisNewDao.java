package com.shaicha.informationNEW.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shaicha.informationNEW.domain.ResultEyeaxisNewDO;

/**
 * 眼轴长度
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
@Mapper
public interface ResultEyeaxisNewDao {

	ResultEyeaxisNewDO get(Integer tEyeaxisId);
	
	List<ResultEyeaxisNewDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ResultEyeaxisNewDO resultEyeaxis);
	
	int update(ResultEyeaxisNewDO resultEyeaxis);
	
	int remove(Integer t_eyeaxis_id);
	
	int batchRemove(Integer[] tEyeaxisIds);
	
	List<ResultEyeaxisNewDO> getEyeaxis(@Param("identityCard")String identityCard,@Param("start") Date start,@Param("end") Date end);
}
