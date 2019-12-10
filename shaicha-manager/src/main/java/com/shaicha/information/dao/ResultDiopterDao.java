package com.shaicha.information.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shaicha.information.domain.ResultDiopterDO;

/**
 * 曲光度详情
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
@Mapper
public interface ResultDiopterDao {

	ResultDiopterDO get(Integer tDiopterId);
	
	List<ResultDiopterDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ResultDiopterDO resultDiopter);
	
	int update(ResultDiopterDO resultDiopter);
	
	int remove(Integer t_diopter_id);
	
	int batchRemove(Integer[] tDiopterIds);
	
	List<ResultDiopterDO> getByToptometryId(Integer tOptometryId);
	
//	List<ResultDiopterDO> overYearMyopia(@Param("checkDate") Date checkDate,@Param("identityCard") String identityCard);
	List<ResultDiopterDO> overYearMyopia(@Param("checkDate") String checkDate,@Param("school") String school);
	
	List<ResultDiopterDO> gradeMyopia(Map<String,Object> map);
	
	List<ResultDiopterDO> studentSexMyopia(Map<String,Object> map);
	
	ResultDiopterDO maxCheckDate();
	
	List<ResultDiopterDO> getStudentMyopia(Map<String,Object> map);
	
	List<ResultDiopterDO> getYanGuang(@Param("ifRL") String ifRL,@Param("identityCard") String identityCard,@Param("checkDate") String checkDate);
	
	List<ResultDiopterDO> jianchashijian();
	
	List<ResultDiopterDO> queryTimeBetween(@Param("startDate") Date startDate,@Param("endDate") Date endDate);

}
