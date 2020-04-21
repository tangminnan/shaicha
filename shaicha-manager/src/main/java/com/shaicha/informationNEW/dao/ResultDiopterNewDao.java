package com.shaicha.informationNEW.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shaicha.informationNEW.domain.ResultDiopterNewDO;

/**
 * 曲光度详情
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-08-16 14:08:23
 */
@Mapper
public interface ResultDiopterNewDao {

	ResultDiopterNewDO get(Integer tDiopterId);
	
	List<ResultDiopterNewDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(ResultDiopterNewDO resultDiopter);
	
	int update(ResultDiopterNewDO resultDiopter);
	
	int remove(Integer t_diopter_id);
	
	int batchRemove(Integer[] tDiopterIds);
	
	List<ResultDiopterNewDO> ifExistData(@Param("school") String school,@Param("start") Date start,@Param("end") Date end);
	
	List<ResultDiopterNewDO> getByToptometryId(Integer tOptometryId);
				
	ResultDiopterNewDO maxCheckDate();
	
	List<ResultDiopterNewDO> getStudentMyopia(Map<String,Object> map);
	
	List<ResultDiopterNewDO> getYanGuang(@Param("ifRL") String ifRL,@Param("identityCard") String identityCard,@Param("start") Date start,@Param("end") Date end);
	
	List<ResultDiopterNewDO> jianchashijian();
	
	List<ResultDiopterNewDO> queryTimeBetween(@Param("startDate") Date startDate,@Param("endDate") Date endDate);

	List<ResultDiopterNewDO> queryQiujing(@Param("identityCard") String identityCard,@Param("checkDate") Date checkDate);
	
	List<ResultDiopterNewDO> queryMyopia(@Param("identityCard") String identityCard,@Param("start") Date start,@Param("end") Date end);
}
