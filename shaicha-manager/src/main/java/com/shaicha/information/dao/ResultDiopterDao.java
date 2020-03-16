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
	
	List<ResultDiopterDO> ifExistData(@Param("school") String school,@Param("start") Date start,@Param("end") Date end);
	
	List<ResultDiopterDO> getByToptometryId(Integer tOptometryId);
				
	ResultDiopterDO maxCheckDate();
	
	List<ResultDiopterDO> getStudentMyopia(Map<String,Object> map);
	
	List<ResultDiopterDO> getYanGuang(@Param("ifRL") String ifRL,@Param("identityCard") String identityCard,@Param("start") Date start,@Param("end") Date end);
	
	List<ResultDiopterDO> jianchashijian();
	
	List<ResultDiopterDO> queryTimeBetween(@Param("startDate") Date startDate,@Param("endDate") Date endDate);

	List<ResultDiopterDO> queryQiujing(@Param("identityCard") String identityCard,@Param("checkDate") Date checkDate);
	
	List<ResultDiopterDO> queryMyopia(@Param("identityCard") String identityCard,@Param("start") Date start,@Param("end") Date end);
}
