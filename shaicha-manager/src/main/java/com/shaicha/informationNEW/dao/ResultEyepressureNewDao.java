package com.shaicha.informationNEW.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shaicha.informationNEW.domain.ResultEyepressureNewDO;

/**
 * 眼内压
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
@Mapper
public interface ResultEyepressureNewDao {

	ResultEyepressureNewDO get(Integer tEyepressureId);
	
	List<ResultEyepressureNewDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ResultEyepressureNewDO resultEyepressure);
	
	int update(ResultEyepressureNewDO resultEyepressure);
	
	int remove(Integer t_eyepressure_id);
	
	int batchRemove(Integer[] tEyepressureIds);
	
	List<ResultEyepressureNewDO> getEyepressure(@Param("identityCard")String identityCard, @Param("start") Date start,@Param("end") Date end);
}
