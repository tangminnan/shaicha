package com.shaicha.information.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shaicha.information.domain.ResultEyesightDO;

/**
 * 视力检查
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
@Mapper
public interface ResultEyesightDao {

	ResultEyesightDO get(Integer tEyesightId);
	
	List<ResultEyesightDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ResultEyesightDO resultEyesight);
	
	int update(ResultEyesightDO resultEyesight);
	
	int remove(Integer t_eyesight_id);
	
	int batchRemove(Integer[] tEyesightIds);
	
	List<ResultEyesightDO> queryLuoYanShiLi(Map<String,Object> map);
	
	List<ResultEyesightDO> liNian(Map<String,Object> map);
	
	List<ResultEyesightDO> jinShi(Map<String,Object> map);
	
	List<ResultEyesightDO> getLifeShili(@Param("identityCard") String identityCard,@Param("start") Date start,@Param("end") Date end);

	ResultEyesightDO queryMinMaxDate(@Param("start") Date start,@Param("end") Date end);
	
	List<ResultEyesightDO> queryDushu(@Param("identityCard") String identityCard,@Param("checkDate") Date checkDate);
	
	int queryDaijingNum(@Param("start") Date start,@Param("end") Date end);
	
	List<ResultEyesightDO> getcheckDate(@Param("start") Date start,@Param("end") Date end);
	
	List<ResultEyesightDO> getgenianlingjinshi(@Param("checkDate") Date checkDate,@Param("xueBu") String xueBu);
	
	List<ResultEyesightDO> getnannvjinshi(@Param("checkDate") Date checkDate,@Param("studentSex") Integer studentSex);

}
