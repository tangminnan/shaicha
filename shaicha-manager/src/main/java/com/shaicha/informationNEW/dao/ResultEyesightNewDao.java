package com.shaicha.informationNEW.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shaicha.informationNEW.domain.ResultEyesightNewDO;

/**
 * 视力检查
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
@Mapper
public interface ResultEyesightNewDao {

	ResultEyesightNewDO get(Integer tEyesightId);
	
	List<ResultEyesightNewDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ResultEyesightNewDO resultEyesight);
	
	int update(ResultEyesightNewDO resultEyesight);
	
	int remove(Integer t_eyesight_id);
	
	int batchRemove(Integer[] tEyesightIds);
	
	List<ResultEyesightNewDO> queryLuoYanShiLi(Map<String,Object> map);
	
	List<ResultEyesightNewDO> liNian(Map<String,Object> map);
	
	List<ResultEyesightNewDO> jinShi(Map<String,Object> map);
	
	List<ResultEyesightNewDO> getLifeShili(@Param("identityCard") String identityCard,@Param("start") Date start,@Param("end") Date end);

	ResultEyesightNewDO queryMinMaxDate(@Param("start") Date start,@Param("end") Date end);
	
	List<ResultEyesightNewDO> queryDushu(@Param("identityCard") String identityCard,@Param("checkDate") Date checkDate);
	
	int queryDaijingNum(@Param("start") Date start,@Param("end") Date end);
	
	List<ResultEyesightNewDO> getcheckDate(@Param("start") Date start,@Param("end") Date end);
	
	List<ResultEyesightNewDO> getgenianlingjinshi(@Param("checkDate") Date checkDate,@Param("xueBu") String xueBu);
	
	List<ResultEyesightNewDO> getnannvjinshi(@Param("checkDate") Date checkDate,@Param("studentSex") Integer studentSex);

}
